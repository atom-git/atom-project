import axios from '@/config/lib/axios'
import Default from '@/config/default'
import Utils from '@/utils'

/*
 * 系统角色api
 */
export default class role {
  // role的替代字段
  static replaceFields = { title: 'roleName', label: 'roleName', key: 'id', value: 'id' }
}
