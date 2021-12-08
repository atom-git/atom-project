/*
 网络请求axios配置
 */
import Axios from 'axios'
import Qs from 'qs'
import { store } from '@/store'
import { router } from '@/router'
import { message } from 'ant-design-vue'
import Default from '@/config/default'

const axios = Axios.create({
  // 所有请求均使用相对地址，会在前面自动拼接/api
  baseURL: '/api',
  // 请求超时时间
  timeout: 10 * 1000,
  // 参数序列化方法
  paramsSerializer: params => {
    return Qs.stringify(params, { indices: false, allowDots: true })
  }
})

// 防止重复请求
let pendingList = []
const pendingHandler = (config, cancel) => {
  const http = Qs.stringify({ url: config.url, method: config.method, params: config.params, data: config.data })
  if (pendingList.includes(http)) {
    if (cancel) {
      // 取消重复请求
      cancel('重复请求，自动取消')
    } else {
      // 删除请求
      pendingList.splice(pendingList.indexOf(http), 1)
    }
  } else {
    if (cancel) {
      pendingList.push(http)
    }
  }
}

// request请求拦截
axios.interceptors.request.use(config => {
  // /api/login为了方便后台spring security处理，修改为/login
  if (config.url === '/login') {
    config.baseURL = ''
  }
  // 加入重复请求取消令牌
  config.cancelToken = new Axios.CancelToken(cancel => {
    pendingHandler(config, cancel)
  })
  // 配置请求头
  // config.headers.post['Content-Type'] = 'application/json'
  // 增加请求令牌
  const token = store.getters.token
  if (token) {
    config.headers['Access-Token'] = token
  }
  // 增加平台及平台类型内容
  config.headers['Platform'] = Default.platform
  config.headers['Platform-Type'] = Default.platformType
  return config
}, err)

// response响应拦截
axios.interceptors.response.use(response => {
  // 判断是否为下载请求
  if (response.headers['content-type'] === 'application/octet-stream') {
    return Promise.resolve(response)
  }
  const respMsg = response.data
  // 请求结束删除请求标记
  pendingHandler(response.config)
  if (respMsg.status === 200) {
    // 响应正常交给业务逻辑
    return Promise.resolve(respMsg.data)
  } else {
    // 响应异常交给公共处理，然后再返回给业务逻辑，可以决定是否要响应
    return err({ errorCode: respMsg.errorCode, errorMsg: respMsg.errorMsg })
  }
}, err)

// 全局请求错误处理，业务失败也在这里公共处理
const err = (error) => {
  // 判断是业务异常还是axios异常
  if (error.response) {
    // axios请求异常响应
    message.error('服务响应错误，请稍后再试！')
  } else {
    if (error.errorCode === '9004') {
      // 清除用户，清除权限
      store.dispatch('clearUser')
      store.dispatch('clearPermission')
      // 回退到登录页
      router.replace({ name: 'signIn' })
    } else {
      // 有响应时，处理业务异常
      message.error(error.errorMsg)
    }
  }
  // 清空pending列表
  pendingList = []
  return Promise.reject(error)
}

export default axios
