import axios from '@/config/lib/axios'
import Default from '@/config/default'

/**
 * 网络管理api
 */
export default class network {

  /**
   * 更新IP-MAC绑定信息
   * @param bind
   */
  static updateBind (bind) {
    return axios({
      url: '/business/network/bind/update',
      method: Default.HTTP_METHOD.PUT,
      data: bind
    })
  }
}
