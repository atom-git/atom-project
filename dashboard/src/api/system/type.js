import axios from '@/config/lib/axios'
import Default from '@/config/default'

/*
 * 系统数据字典api
 */
export default class type {
  // type的替代字段
  static replaceFields = { title: 'typeName', label: 'typeName', key: 'id', value: 'id' }

  /**
   * 根据filter获取系统维值 { typeName, parentId, meanId }
   * @param filter 过滤器
   */
  static codeList (filter) {
    return axios({
      url: '/system/type/code/list',
      method: Default.HTTP_METHOD_GET,
      params: filter
    })
  }
}
