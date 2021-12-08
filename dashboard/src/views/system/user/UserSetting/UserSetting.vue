<template>
  <SideLayout sideTitle="个人设置">
    <template #sider>
      <a-anchor :offsetTop="92">
        <a-menu mode="vertical" :theme="contentTheme" class="atom-user-setting-sider">
          <a-anchor-link href="#basic">
            <template #title>
              <a-menu-item key="basic">
                <template #icon><IconFont type="IdcardOutlined"/></template>基本信息
              </a-menu-item>
            </template>
          </a-anchor-link>
          <a-anchor-link href="#security">
            <template #title>
              <a-menu-item key="security">
                <template #icon><IconFont type="LockOutlined"/></template>安全设置
              </a-menu-item>
            </template>
          </a-anchor-link>
          <a-anchor-link href="#bind">
            <template #title>
              <a-menu-item key="bind">
                <template #icon><IconFont type="GroupOutlined"/></template>帐号绑定
              </a-menu-item>
            </template>
          </a-anchor-link>
          <a-anchor-link href="#destory">
            <template #title>
              <a-menu-item key="destory">
                <template #icon><IconFont type="UserDeleteOutlined"/></template>帐号销毁
              </a-menu-item>
            </template>
          </a-anchor-link>
        </a-menu>
      </a-anchor>
    </template>
    <template #content>
      <div class="atom-user-setting-content">
        <BasicForm :userInfo="userInfo"></BasicForm>
        <SecurityForm :userInfo="userInfo"></SecurityForm>
        <a-card id="bind" title="帐号绑定">

        </a-card>
        <a-card id="destory" title="帐号销毁">

        </a-card>
      </div>
    </template>
  </SideLayout>
</template>

<script>
/**
 * 个人设置
 */
import { SideLayout } from '@/layouts'
import BasicForm from './BasicForm'
import SecurityForm from './SecurityForm'
import config from '@/config/mixins/config'
import user from '@/config/mixins/user'
export default {
  name: 'UserSetting',
  components: { SideLayout, BasicForm, SecurityForm },
  mixins: [config, user]
}
</script>

<style lang="less">
.atom-user-setting-sider {

}
.atom-user-setting-content {
  .ant-card {
    &:not(:last-child) {
      margin-bottom: 16px;
    }
    .atom-form >.ant-card-body {
      padding: 0;
    }
  }
}
</style>
