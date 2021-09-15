/**
 * sockJS连接，STOMP信息发送模式
 */

import SockJS from 'sockjs-client'
import Stomp from 'stompjs'
import Default from '@/config/default'
import { message } from 'ant-design-vue'
import { store } from '@/store'

export default {
  // 状态 0 未连接，100连接中，200连接成功，400连接失败
  status: 0,
  // 心跳及断线重连时长
  heartbeat: 10000,
  // sockJS对象
  socket: null,
  // Stomp客户端
  client: null,
  // 错误信息
  error: null,
  // 订阅列表，用于保存订阅信息
  subscribeMap: {},
  // 默认连接的headers
  headers: {
    'Platform': Default.platform,
    'Platform-Type': Default.platformType
  },
  // 重连记时器, timer重连定时器，msgCloser:重连消息提示器
  reconnect: null,
  // websocket连接
  connect: function (url = Default.WEB_SOCKET_ENDPOINT, headers) {
    // 订阅及发送信息均发生于登录后，因此未登录状态下不创建连接
    if (store.getters.token) {
      // 创建websocket创建
      this.socket = new SockJS(url)
      // stomp客户端创建
      this.client = Stomp.over(this.socket)
      // 设置客户端与服务端的存活心跳
      this.client.heartbeat = { incoming: this.heartbeat, outgoing: this.heartbeat }
      // 生产环境关闭debug
      if (process.env.NODE_ENV !== 'production') {
        this.client.debug = () => {}
      }
      // 外部传入参数，内部增加token值
      headers = Object.assign(headers || {}, this.headers, { 'Access-Token': store.getters.token })
      // 设置等待连接状态
      this.status = 100
      // 连接并设置监听,设置headers,connectCallback,errorCallback
      this.client.connect(headers, () => {
        // connectCallback连接成功后的回调
        // 重连后删除重连且删除消息提示
        this.clear()
        // 设置连接成功
        this.status = 200
        message.success('消息服务已连接成功！')
        // 该位置一般用于连接成功后，订阅事件
      }, (frame) => {
        console.log(this.status, this.subscribeMap)
        // errorCallback连接失败后的回调
        // 记录错误信息
        this.error = frame
        // 设置连接失败
        this.status = 400
        // 创建websocket创建，先看是否已经连接，已连接则先关闭
        if (this.socket != null) {
          this.socket.close()
          this.socket = null
        }
        // 清空订阅列表
        this.subscribeMap = {}
        // 该位置一般用于清理或者发送提示,以及断线重连
        if (this.reconnect === null) {
          this.reconnect = {
            msgCloser: message.error('消息服务连接失败，正在断线重连！', 0),
            timer: setInterval(((_this) => {
              return () => { _this.connect(url, headers) }
            })(this), this.heartbeat)
          }
        }
      })
    }
  },
  // websocket断开连接
  disconnect: function (headers) {
    if (this.client !== null) {
      // 外部传入参数，内部增加token值
      headers = Object.assign(headers || {}, this.headers, { 'Access-Token': store.getters.token })
      this.client.disconnect(() => {
        if (this.socket !== null) {
          this.socket.close()
        }
        this.clear()
        this.reset()
      }, headers)
    } else {
      this.clear()
      this.reset()
    }
  },
  // 订阅服务，headers用于自定义传入头部参数
  subscribe: function (destination, msgCallback, headers) {
    return new Promise(resolve => {
      if (this.status === 200) {
        console.log('=====subscribe', this.status)
        // 外部传入参数，内部增加token值
        headers = Object.assign(headers || {}, this.headers, { 'Access-Token': store.getters.token })
        const subscribe = this.client.subscribe(destination, frame => {
          msgCallback((JSON.parse(frame.body) || {}).data)
        }, headers)
        // 记录订阅了哪些信息
        this.subscribeMap[subscribe.id] = Object.assign({ destination }, subscribe)
        resolve(subscribe)
      } else {
        console.log('=====wait', this.status)
        // 等待连接后再次发起订阅
        setTimeout(() => { this.subscribe(destination, msgCallback, headers).then(subscribe => resolve(subscribe)) }, 500)
      }
    })
  },
  // 取消订阅
  unsubscribe: function (subscribe) {
    if (this.status === 200) {
      this.client.unsubscribe(subscribe.id)
      // 删除订阅信息
      delete this.subscribeMap[subscribe.id]
    } else {
      // 等待连接后再次发起订阅
      setTimeout(() => { this.unsubscribe(subscribe) }, 500)
    }
  },
  // 发送消息，headers用于自定义传入头部参数
  send: function (destination, msg, headers) {
    return new Promise(resolve => {
      // 连接成功后发送信息
      if (this.status === 200) {
        console.log('=====send', this.status)
        // 外部传入参数，内部增加token值
        headers = Object.assign(headers || {}, this.headers, { 'Access-Token': store.getters.token })
        resolve(this.client.send(destination, headers, JSON.stringify(msg)))
      } else {
        console.log('=====wait', this.status)
        // 等待连接后再次发送
        setTimeout(() => { return this.send(destination, msg, headers).then(frame => resolve(frame)) }, 500)
      }
    })
  },
  // 清除信息
  clear () {
    if (this.reconnect !== null) {
      this.reconnect.msgCloser()
      clearInterval(this.reconnect.timer)
      this.reconnect = null
    }
  },
  // 重置为初始状态
  reset () {
    this.socket = this.client = this.error = this.reconnect = null
    this.status = 0
    this.subscribeMap = {}
  },
  // 获取状态
  getStatus: function () {
    return this.status
  },
  // 获取错误信息
  getError: function () {
    return this.error
  }
}
