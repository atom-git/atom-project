import system from './system'
import user from './system/user'
import dept from './system/dept'
import role from './system/role'
import menu from './system/menu'
system.user = user
system.dept = dept
system.role = role
system.menu = menu

/**
 * api管理
 */
export default {
  system
}
