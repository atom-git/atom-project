import system from './system'
import user from './system/user'
import dept from './system/dept'
import role from './system/role'
import menu from './system/menu'
import action from './system/action'
import type from './system/type'
import news from './system/news'
import file from './system/file'
system.user = user
system.dept = dept
system.role = role
system.menu = menu
system.action = action
system.type = type
system.news = news
system.file = file

/**
 * api管理
 */
export default {
  system
}
