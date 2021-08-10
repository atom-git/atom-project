import axios from '@/config/lib/axios'
import Default from '@/config/default'
import Utils from '@/utils'

/*
 * 系统用户api
 */
export default class user {
  /*
   * 根据token获取用户，token由axios自动配置
   */
  static me () {
    return axios({
      url: '/system/user/me',
      method: Default.HTTP_METHOD_GET
    })
  }

  /**
   * 修改用户密码
   * @param userId 用户ID
   * @param sysUser 用户信息实体
   */
  static updatePassword (userId, sysUser) {
    return axios({
      url: Utils.formatStr('/system/user/update/{s}/password', userId),
      method: Default.HTTP_METHOD_POST,
      data: sysUser
    })
  }

  /**
   * 更新用户头像
   * @param userId 用户ID
   * @param sysUser 用户信息实体
   */
  static updateHead (userId, sysUser) {
    return axios({
      url: Utils.formatStr('/system/user/update/{s}/head', userId),
      method: Default.HTTP_METHOD_POST,
      data: sysUser
    })
  }

  /*
   * 获取系统用户数据表
   * @param filter 系统用户Filter对象
   * @param page 分页信息
   * @param order 排序信息
   */
  static list (filter, page, order) {
    return axios({
      url: '/system/user/list',
      method: Default.HTTP_METHOD_GET,
      params: { ...filter, ...page, ...{ orderData: order } }
    })
  }

  /**
   * 新增或者编辑用户
   * @param sysUser 用户传输对象
   */
  static update (sysUser) {
    return axios({
      url: '/system/user/update',
      method: Default.HTTP_METHOD_PUT,
      data: sysUser
    })
  }

  /**
   * 重置用户密码
   * @param userId 用户ID
   */
  static resetPassword (userId) {
    return axios({
      url: Utils.formatStr('/system/user/reset/{s}/password', userId),
      method: Default.HTTP_METHOD_POST
    })
  }

  /**
   * 重置用户密码
   * @param userId 用户ID
   */
  static toggleValid (userId) {
    return axios({
      url: Utils.formatStr('/system/user/toggle/valid/{s}', userId),
      method: Default.HTTP_METHOD_GET
    })
  }

  /**
   * 查询用户角色信息
   * @param userId 用户ID
   */
  static roleList (userId) {
    return axios({
      url: Utils.formatStr('/system/user/{s}/role', userId),
      method: Default.HTTP_METHOD_GET
    })
  }

  /**
   * 更新用户角色
   * @param userId 用户ID
   * @param userRoleList 用户角色列表
   */
  static updateRole (userId, userRoleList) {
    return axios({
      url: Utils.formatStr('/system/user/{s}/update/role', userId),
      method: Default.HTTP_METHOD_POST,
      data: userRoleList
    })
  }
}
