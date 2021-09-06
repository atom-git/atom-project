import axios from '@/config/lib/axios'
import Default from '@/config/default'
import Utils from '@/utils'

/*
 * 系统菜单api
 */
export default class menu {
  // menu的替代字段
  static replaceFields = { title: 'name', label: 'name', key: 'id', value: 'id', children: 'children' }
  /**
   * 查询菜单树
   */
  static tree () {
    return axios({
      url: '/system/menu/tree',
      method: Default.HTTP_METHOD.GET
    })
  }

  /**
   * 更新菜单信息
   * @param sysMenu 菜单信息
   */
  static update (sysMenu) {
    return axios({
      url: '/system/menu/update',
      method: Default.HTTP_METHOD.PUT,
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
      method: Default.HTTP_METHOD.DELETE
    })
  }

  /**
   * 菜单的上移或者下移
   * @param move 动作
   * @param sysMenu 菜单
   */
  static exchange (move, sysMenu) {
    return axios({
      url: Utils.formatStr('/system/menu/exchange/{s}', move),
      method: Default.HTTP_METHOD.POST,
      data: sysMenu
    })
  }
}
