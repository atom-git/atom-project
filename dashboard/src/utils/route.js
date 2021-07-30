import Default from '@/config/default'
/**
 * 路由相关工具类
 */
export default class RouteUtils {
  /**
   * 修改网页当前的title
   * @param title 标题
   */
  static setDomTitle (title) {
    // 拼接系统名称
    document.title = title.concat('-', Default.title)
    const userAgent = navigator.userAgent
    // 在MicroMessenger[微信浏览器] ip(hone|od|ad)指IOS平台设备
    // eslint-disable-next-line
    const wechatRegex = /\bMicroMessenger\/([\d.]+)/
    const iosRegex = /ip(hone|od|ad)/i
    if (wechatRegex.test(userAgent) && iosRegex.test(userAgent)) {
      const iframe = document.createElement('iframe')
      iframe.src = '/favicon-solid.ico'
      iframe.style.display = 'none'
      iframe.onload = () => {
        setTimeout(function () {
          iframe.remove()
        }, 10)
      }
      document.body.appendChild(iframe)
    }
  }
  /**
   * 生成路由访问白名单
   * @param router 切入的路由
   */
  static generateWhite (router) {
    let whiteList = []
    router.forEach(r => {
      whiteList.push(r.name)
      if (r.children && r.children.length > 0) {
        whiteList = whiteList.concat(this.generateWhite(r.children))
      }
    })
    return whiteList
  }
}
