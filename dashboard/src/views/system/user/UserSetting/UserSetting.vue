<template>
  <SideLayout sideTitle="个人设置">
    <template #sider>
      <a-anchor :offsetTop="92" :affix="appConfig.layout !== 'drawer'" class="atom-user-setting-sider">
        <a-menu mode="vertical" :theme="contentTheme">
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
          <a-anchor-link href="#login">
            <template #title>
              <a-menu-item key="login">
                <template #icon><IconFont type="GroupOutlined"/></template>登录信息
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
        <a-card id="login" title="登录信息">
          <a-list-item>
            <a-list-item-meta title="第三方登录" :description="thirdSignIn.name">
              <template #avatar>
                <a-avatar :style="{ backgroundColor: thirdSignIn.color }">
                  <template #icon>
                    <IconFont :type="thirdSignIn.icon"/>
                  </template>
                </a-avatar>
              </template>
            </a-list-item-meta>
            <template #actions>
              <a-button type="link">解绑</a-button>
            </template>
          </a-list-item>
          <a-list-item>
            <a-list-item-meta title="最后登录时间" :description="userInfo.lastLogin">
              <template #avatar>
                <a-avatar :style="{ backgroundColor: '#1890FF' }">
                  <template #icon>
                    <IconFont type="ClockCircleOutlined"/>
                  </template>
                </a-avatar>
              </template>
            </a-list-item-meta>
          </a-list-item>
        </a-card>
        <a-card id="destory" title="帐号销毁">
          <a-alert type="warning" showIcon message="帐号一旦销毁，将无法恢复，请三思而行！" />
          <div class="atom-user-destory">
            <span class="atom-user-destory-warn">
              <a-switch v-model:checked="destoryConfirm"/>本人确认要删除帐号
            </span>
            <a-button type="primary"
                      danger
                      :disabled="!destoryConfirm"
                      @click="handleDestory">销毁帐号</a-button>
          </div>
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
  mixins: [config, user],
  data () {
    return {
      // 删除是否确认
      destoryConfirm: false,
      // 第三方绑定列表
      keyInfos: [
        { title: '第三方登录', avatar: '', }
      ]
    }
  },
  computed: {
    // 第三方登录相关信息
    thirdSignIn () {
      return this.initThird(this.userInfo['thirdType'])
    }
  },
  methods: {
    // 初始化第三方信息
    initThird (thirdType) {
      if (thirdType === 'wechat') {
        return { icon: 'WechatOutlined', name: '微信', color: '#52C41A' }
      } else if (thirdType === 'qq') {
        return { icon: 'QqOutlined', name: 'QQ', color: '#000000D9' }
      } else if (thirdType === 'alipay') {
        return { icon: 'AlipayOutlined', name: '支付宝', color: '#1890FF' }
      } else {
        return { icon: 'UserAddOutlined', name: '未绑定', color: '#00000073' }
      }
    },
    // 响应帐号销毁
    handleDestory () {
      console.log('destory')
    }
  }
}
</script>

<style lang="less">
.atom-user-setting-sider {
  user-select: none;
}
.atom-user-setting-content {
  .ant-card {
    &:not(:last-child) {
      margin-bottom: 16px;
    }
    .atom-form >.ant-card-body {
      padding: 0;
    }
    .atom-user-destory {
      margin-top: 16px;
      display: flex;
      justify-content: space-between;
      align-items: center;
      .atom-user-destory-warn {
        vertical-align: middle;
        line-height: 32px;
        color: #F5222D;
        font-weight: 600;
        .ant-switch {
          margin-right: 12px;
        }
      }
      .ant-btn-dangerous:disabled {
        border-color: #B33B3D;
        color: #B33B3D;
      }
    }
  }
}
</style>
