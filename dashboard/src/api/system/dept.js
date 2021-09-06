import axios from '@/config/lib/axios'
import Default from '@/config/default'
import Utils from '@/utils'

/*
 * 系统组织api
 */
export default class dept {
  // dept的替代字段
  static replaceFields = { title: 'deptName', key: 'id', value: 'id' }
  /**
   * 查询组织树
   */
  static tree () {
    return axios({
      url: '/system/dept/tree',
      method: Default.HTTP_METHOD.GET
    })
  }

  /**
   * 更新组织数据
   * @param sysDept 组织机构信息
   */
  static update (sysDept) {
    return axios({
      url: '/system/dept/update/',
      method: Default.HTTP_METHOD.PUT,
      data: sysDept
    })
  }

  /**
   * 根据id删除组织
   * @param deptId 组织id
   */
  static delete (deptId) {
    return axios({
      url: Utils.formatStr('/system/dept/delete/{s}', deptId),
      method: Default.HTTP_METHOD.DELETE
    })
  }
}
