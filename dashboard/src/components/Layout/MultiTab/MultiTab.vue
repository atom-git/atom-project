<template>
  <a-tabs v-model:activeKey="activeRoute" class="atom-multi-tabs" @change="handleTabChange">
    <a-tab-pane v-for="openRoute in openRoutes" :key="openRoute.name">
      <template #tab>
        {{ openRoute.meta.title }}
        <IconFont v-if="openRoute.name === activeRoute" :type="refreshing ? 'LoadingOutlined' : 'SyncOutlined'" @click.stop="handleReload"/>
        <IconFont v-if="openRoute.closable" type="CloseOutlined" @click.stop="handleClose(openRoute)"/>
      </template>
    </a-tab-pane>
    <!-- 多于一个时显示 -->
    <template #tabBarExtraContent v-if="tabCount > 1">
      <a-dropdown :trigger="['hover']">
        <span><IconFont type="DownOutlined"/></span>
        <template #overlay>
          <a-menu @click="handleDropdownClick">
            <a-menu-item key="left"><IconFont type="LeftCircleOutlined"/>关闭左侧</a-menu-item>
            <a-menu-item key="right"><IconFont type="RightCircleOutlined"/>关闭右侧</a-menu-item>
            <a-menu-item key="other"><IconFont type="SwapOutlined"/>关闭其他</a-menu-item>
            <a-menu-item key="all"><IconFont type="RetweetOutlined"/>关闭全部</a-menu-item>
          </a-menu>
        </template>
      </a-dropdown>
    </template>
  </a-tabs>
</template>

<script>
/**
 * 页面多标签组件
 */
// 首页路由
const homeRoute = { name: 'home', path: '/home', meta: { title: '首页' }, closable: false }
export default {
  name: 'MultiTab',
  props: {
    // 是否可拖拽
    draggable: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      // 已打开的路由，把首页永远固定在tab中
      openRoutes: this.$store.state.openRoutes && this.$store.state.openRoutes.length > 0 ? this.$store.state.openRoutes : [homeRoute],
      // 激活的路由
      activeRoute: '',
      // 是否正在刷新
      refreshing: false
    }
  },
  computed: {
    // 多个tab时显示关闭按钮
    tabCount () {
      return this.openRoutes.length
    },
    // 当前选中的在openRoutes数组中的位置
    activeIndex () {
      return this.openRoutes.findIndex(route => route.name === this.activeRoute)
    }
  },
  created () {
    // 生成已打开的路由
    this.generateOpenRoute(this.$route)
  },
  inject: ['reload'],
  methods: {
    // 响应tab的切换
    handleTabChange (activeRoute) {
      // 跳转至相应的路由
      this.$router.push({ name: activeRoute })
    },
    // 响应tab关闭按钮
    handleClose (openRoute) {
      const index = this.openRoutes.findIndex(route => route.name === openRoute.name)
      this.openRoutes.splice(index, 1)
      this.$store.dispatch('setOpenRoutes', this.openRoutes)
      // 改变路由到当前打开路由的下一个，如果没有下一个，则取当前路由的最后一个，如果没有打开的路由，则打开首页
      if (index >= this.openRoutes.length) {
        this.$router.push({ name: this.openRoutes[this.openRoutes.length - 1].name })
      } else {
        if (this.openRoutes[index].name !== this.$route.name) {
          this.$router.push({ name: this.openRoutes[index].name })
        }
      }

    },
    // 响应路由重新加载
    handleReload () {
      const self = this
      if (!self.refreshing) {
        this.refreshing = true
        setTimeout(() => {
          self.reload()
          self.refreshing = false
        }, 500)
      }
    },
    // 构建openRoute
    generateOpenRoute (newRoute) {
      this.activeRoute = newRoute.name
      if (this.openRoutes.filter(route => route.name === newRoute.name).length <= 0) {
        this.openRoutes.push({ name: newRoute.name, path: newRoute.path, meta: newRoute.meta, query: newRoute.query, params: newRoute.params, closable: true })
        this.$store.dispatch('setOpenRoutes', this.openRoutes)
      }
    },
    // 响应标签页多功能按钮
    handleDropdownClick (item) {
      switch (item.key) {
        case 'left':
          // 从0开始，删除index长度的元素
          this.openRoutes.splice(1, this.activeIndex - 1)
          break
        case 'right':
          // 从当前元素加+1开始，删除长度index+1个元素
          this.openRoutes.splice(this.activeIndex + 1, (this.openRoutes.length - 1 - this.activeIndex))
          break
        case 'other':
          // 等于当前位置的route
          this.openRoutes = [homeRoute, this.openRoutes[this.activeIndex]]
          break
        case 'all':
          // 仅保留当前的tab
          this.openRoutes = [homeRoute]
          this.generateOpenRoute(this.$route)
          break
      }
    }
  },
  watch: {
    // 对$route进行监听，发生变化时，加入新的路由
    '$route' (newRoute) {
      // 设置当前路由为新选中, 如果没有当前路由，则加入
      this.generateOpenRoute(newRoute)
    }
  }
}
</script>

<style lang="less">
@import "multiTab";
</style>
