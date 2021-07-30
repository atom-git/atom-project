import { store } from '@/store'
/**
 * 按钮级权限: permission 权限指令
 * 指令用法：
 *  - 在需要控制 元素 级别权限的组件上使用 v-permission="['/system/user/add']" , 如下：
 *    <a-button v-permission="['/system/user/add']" >添加用户</a-button>
 *    <a-button v-permission="['/system/user/delete']">删除用户</a-button>
 *    <a v-permission="['/system/user/update']" @click="edit(record)">修改</a>
 *
 *  - 当前用户没有权限时，组件上使用了该指令则会被隐藏
 *  @see https://github.com/sendya/ant-design-pro-vue/pull/53
 *  @see https://v3.cn.vuejs.org/guide/migration/custom-directives.html#_2-x-%E8%AF%AD%E6%B3%95
 */
export default {
  mounted: function (el, binding) {
    const { value } = binding // 获取自定义指令传入的鉴权信息
    const actions = store.getters.actions
    if (value && value instanceof Array && value.length > 0) {
      const permissionResources = value

      // 判断用户是否包含该元素所需权限
      const hasPermission = actions && actions.some(resource => {
        return permissionResources.includes(resource)
      })
      // 权限不足
      if (!hasPermission) {
        // 移除该dom元素
        el.parentNode && el.parentNode.removeChild(el)
      }
    }
  }
}
