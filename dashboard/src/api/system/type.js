import axios from '@/config/lib/axios'
import Utils from '@/utils'
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
      method: Default.HTTP_METHOD.GET,
      params: filter
    })
  }

  /**
   * 根据filter获取系统维值 { typeName, parentId, meanId }
   * @param filter 过滤器
   */
  static codeTree (filter) {
    return axios({
      url: '/system/type/code/tree',
      method: Default.HTTP_METHOD.GET,
      params: filter
    })
  }

  /**
   * 删除数据字典维值
   * @param codeId 数据字典维值id
   */
  static deleteCode (codeId) {
    return axios({
      url: Utils.formatStr('/system/type/code/delete/{s}', codeId),
      method: Default.HTTP_METHOD.DELETE
    })
  }

  /**
   * 新增或者编辑数据字典
   * @param sysType 字典
   * @param sysTypeCode 字典数据
   */
  static updateCode (sysType, sysTypeCode) {
    return axios({
      url: Utils.formatStr('/system/type/{s}/code/update', sysType.id),
      method: Default.HTTP_METHOD.PUT,
      data: sysTypeCode
    })
  }

  /**
   * 数据字典维值的上移或者下移
   * @param move 动作
   * @param sysTypeCode 字典维值
   */
  static exchange (move, sysTypeCode) {
    return axios({
      url: Utils.formatStr('/system/type/exchange/{s}', move),
      method: Default.HTTP_METHOD.POST,
      data: sysTypeCode
    })
  }
}
