<template>
  <SideLayout sideTitle="个人中心">
    <template #sider>
      个人简介card
    </template>
    <template #content>
      待办、通知、消息tab列表
    </template>
  </SideLayout>
</template>

<script>
/**
 * 个人中心
 */
import { SideLayout } from '@/layouts'
export default {
  name: 'UserCenter',
  components: { SideLayout }
}
</script>
