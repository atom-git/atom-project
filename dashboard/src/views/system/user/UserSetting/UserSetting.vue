<template>
  <SideLayout sideTitle="个人设置">
    <template #sider>
      个人设置锚点
    </template>
    <template #content>
      基本信息、安全设置、帐号绑定、帐号销毁
    </template>
  </SideLayout>
</template>

<script>
/**
 * 个人设置
 */
import { SideLayout } from '@/layouts'
export default {
  name: 'UserSetting',
  components: { SideLayout }
}
</script>
