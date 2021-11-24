import axios from '@/config/lib/axios'
import Default from '@/config/default'
import Utils from '@/utils'

/**
 * 系统自定义表单api
 */
export default class form {

  /**
   * 新增或者编辑自定义表单
   * @param sysForm 自定义表单
   */
  static update (sysForm) {
    return axios({
      url: '/system/form/update',
      method: Default.HTTP_METHOD.PUT,
      data: sysForm
    })
  }

  /**
   * 系统自定义表单禁用/启用
   * @param formId 系统自定义表单id
   */
  static toggleValid (formId) {
    return axios({
      url: Utils.formatStr('/system/form/toggle/valid/{s}', formId),
      method: Default.HTTP_METHOD.GET
    })
  }
}
