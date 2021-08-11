import system from './system'
import user from './system/user'
import dept from './system/dept'
import role from './system/role'
import menu from './system/menu'
import action from './system/action'
import type from './system/type'
system.user = user
system.dept = dept
system.role = role
system.menu = menu
system.action = action
system.type = type

/**
 * api管理
 */
export default {
  system
}
