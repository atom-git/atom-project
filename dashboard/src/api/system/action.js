import axios from '@/config/lib/axios'
import Default from '@/config/default'

/*
 * 系统资源api
 */
export default class action {
  // action的替代字段
  static replaceFields = { title: 'name', label: 'name', key: 'id', value: 'id' }

  /**
   * 查询资源主题列表
   */
  static topicList () {
    return axios({
      url: '/system/action/topic/list',
      method: Default.HTTP_METHOD.GET
    })
  }

  /**
   * 查询资源列表
   */
  static list () {
    return axios({
      url: '/system/action/list',
      method: Default.HTTP_METHOD.GET
    })
  }
}
