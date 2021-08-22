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
        if (this.reconnect !== null) {
          this.reconnect.msgCloser()
          clearInterval(this.reconnect.timer)
          this.reconnect = null
        }
        // 设置连接成功
        this.status = 200
        message.success('消息服务已连接成功！')
        // 该位置一般用于连接成功后，订阅事件
      }, (frame) => {
        // errorCallback连接失败后的回调，等待连接状态时，才做重连动作
        if (this.status === 100) {
          // 记录错误信息
          this.error = frame
          // 设置连接失败
          this.status = 400
          // 清空订阅列表
          this.subscribeMap = {}
          // 该位置一般用于清理或者发送提示,以及断线重连
          if (this.reconnect === null) {
            this.reconnect = {
              msgCloser: message.error('消息服务连接失败，正在断线重连！', 0),
              timer: setInterval(((_this) => {
                return () => { _this.connect(headers) }
              })(this), this.heartbeat)
            }
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
        this.socket = this.client = this.error = this.reconnect = null
        this.status = 0
        this.subscribeMap = {}
      }, headers)
    }
  },
  // 订阅服务，headers用于自定义传入头部参数
  subscribe: function (destination, msgCallback, headers) {
    // 连接成功后订阅服务，返回信息中包括订阅id，以及取消订阅方法，取消订阅及订阅方自行发起，header中写入token
    if (this.status === 200) {
      // 外部传入参数，内部增加token值
      headers = Object.assign(headers || {}, this.headers, { 'Access-Token': store.getters.token })
      const subscribe = this.client.subscribe(destination, (msg) => {
        msgCallback(JSON.parse(msg) || msg)
      }, headers)
      // 记录订阅了哪些信息，同一订阅多次发生时，记录每次订阅的返回信息
      this.subscribeMap[destination] = (this.subscribeMap[destination] || []).push(subscribe)
      return subscribe
    } else {
      // 如果未连接，直接返回当前的状态及错误信息，订阅方自行判断
      return {
        status: this.status,
        error: this.error
      }
    }
  },
  // 发送消息，headers用于自定义传入头部参数
  send: function (destination, msg, headers) {
    // 连接成功后发送信息
    if (this.status === 200) {
      // 外部传入参数，内部增加token值
      headers = Object.assign(headers || {}, this.headers, { 'Access-Token': store.getters.token })
      return this.client.send(destination, headers, JSON.stringify(msg))
    } else {
      // 如果未连接，直接返回当前的状态及错误信息，发送方自行判断
      return {
        status: this.status,
        error: this.error
      }
    }
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
