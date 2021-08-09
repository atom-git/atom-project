import axios from '@/config/lib/axios'
import Default from '@/config/default'
import Utils from '@/utils'

/*
 * 系统菜单api
 */
export default class menu {
  // menu的替代字段
  static replaceFields = { title: 'name', label: 'name', key: 'id', value: 'id' }

  /**
   * 查询菜单列表
   */
  static list () {
    return axios({
      url: '/system/menu/list',
      method: Default.HTTP_METHOD_GET
    })
  }

  /**
   * 更新菜单信息
   * @param sysMenu 菜单信息
   */
  static update (sysMenu) {
    return axios({
      url: '/system/menu/update',
      method: Default.HTTP_METHOD_POST,
      data: sysMenu
    })
  }

  /**
   * 根据id删除菜单
   * @param menuId 菜单id
   */
  static delete (menuId) {
    return axios({
      url: Utils.formatStr('/system/menu/delete/{s}', menuId),
      method: Default.HTTP_METHOD_DELETE
    })
  }
}
