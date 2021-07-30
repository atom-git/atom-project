import columnResize from './columnResize'
import permission from './permission'
import waterMark from './waterMark'
/**
 * 装配动态指令
 * @param app app实例
 */
export function setUpDirectives (app) {
  app.directive('column-resize', columnResize)
  app.directive('permission', permission)
  app.directive('water-mark', waterMark)
}
