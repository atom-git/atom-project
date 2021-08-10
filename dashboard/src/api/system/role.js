import axios from '@/config/lib/axios'
import Default from '@/config/default'
import Utils from '@/utils'

/*
 * 系统角色api
 */
export default class role {
  // role的替代字段
  static replaceFields = { title: 'roleName', label: 'roleName', key: 'id', value: 'id', status: 'ifDefault' }

  /**
   * 查询角色列表
   */
  static list () {
    return axios({
      url: '/system/role/list',
      method: Default.HTTP_METHOD_GET
    })
  }

  /**
   * 更新角色信息
   * @param sysRole 角色信息
   */
  static update (sysRole) {
    return axios({
      url: '/system/role/update',
      method: Default.HTTP_METHOD_PUT,
      data: sysRole
    })
  }

  /**
   * 根据id删除角色
   * @param roleId 角色id
   */
  static delete (roleId) {
    return axios({
      url: Utils.formatStr('/system/role/delete/{s}', roleId),
      method: Default.HTTP_METHOD_DELETE
    })
  }

  /**
   * 获取角色权限
   * @param roleId 角色id
   */
  static permission (roleId) {
    return axios({
      url: Utils.formatStr('/system/role/{s}/permission', roleId),
      method: Default.HTTP_METHOD_GET
    })
  }

  /**
   * 修改角色权限
   * @param roleId 角色id
   * @param permission 权限信息
   */
  static updatePermission (roleId, permission) {
    return axios({
      url: Utils.formatStr('/system/role/{s}/update/permission', roleId),
      method: Default.HTTP_METHOD_POST,
      data: permission
    })
  }
}
