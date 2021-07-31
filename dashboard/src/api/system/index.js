import axios from '@/config/lib/axios'
import Default from '@/config/default'
import Utils from '@/utils'

/**
 * 系统管理api
 */
export default class system {
  /*
   * 登录请求
   */
  static signIn (signUser) {
    return axios({
      url: '/login',
      method: Default.HTTP_METHOD_POST,
      data: signUser
    })
  }

  /**
   * 注册用户
   */
  static signUp (signUser) {
    return axios({
      url: '/system/user/sign/up',
      method: Default.HTTP_METHOD_POST,
      data: signUser
    })
  }

  /**
   * 获取验证码
   */
  static captcha () {
    return axios({
      url: '/system/captcha',
      method: Default.HTTP_METHOD_GET
    })
  }

  /**
   * 校验图片验证码
   * @param captcha: 图片验证信息
   */
  static judgeCaptcha (captcha) {
    return axios({
      url: '/system/judge/captcha',
      method: Default.HTTP_METHOD_POST,
      data: captcha
    })
  }

  /**
   * 忘记密码，仅支持portal的密码修改，dashboard需联系管理员重置，然后登录后进行修改
   * @param forget: 忘记密码对象
   */
  static forgetPassword (forget) {
    return axios({
      url: '/system/forget/password',
      method: Default.HTTP_METHOD_POST,
      data: forget
    })
  }

  /*
   * 根据token登出用户
   */
  static signOut () {
    return axios({
      url: '/system/logout',
      method: Default.HTTP_METHOD_POST
    })
  }

  /**
   * 发送验证码
   * @param phone: 用户手机号
   */
  static sendVerifyCode (phone) {
    return axios({
      url: Utils.formatStr('/system/send/{s}/verifyCode', phone),
      method: Default.HTTP_METHOD_POST
    })
  }

  /**
   * 生成第三方登录随机串
   */
  static thirdState () {
    return axios({
      url: '/system/third/state',
      method: Default.HTTP_METHOD_GET
    })
  }

  /*
   * 第三方登录回调方法
   * @param signUser 第三方登录授权临时票据,crf校验串
   */
  static thirdSignIn (signUser) {
    return axios({
      url: '/system/third/sign/in',
      method: Default.HTTP_METHOD_POST,
      data: signUser
    })
  }
}
