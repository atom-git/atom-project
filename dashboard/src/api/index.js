import system from './system'
import user from './system/user'
import dept from './system/dept'
import role from './system/role'
system.user = user
system.dept = dept
system.role = role

/**
 * api管理
 */
export default {
  system
}
