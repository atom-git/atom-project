<template>
  <router-view v-slot="{ Component, route }">
    <!-- 动画特效，由全局的配置决定 -->
    <Animate :animate="appConfig.transition.name"
             :direction="appConfig.transition.direction"
             :disabled="appConfig.transition.disabled">
      <!-- 是否keep-alive，由route的meta配置决定, key值必须有，否则transition-group会有异常 -->
      <keep-alive :include="generateInclude(route)" key="keepAlive">
        <component :is="Component" :key="route.name" />
      </keep-alive>
    </Animate>
  </router-view>
</template>

<script>
import Animate from '@/components/Common/Animate'
import { mapGetters } from 'vuex'
export default {
  name: 'RouteView',
  components: {
    Animate
  },
  data () {
    return {
      // 需要alive的路由，在点击过一次后增加进来
      includeRoute: []
    }
  },
  computed: {
    ...mapGetters(['appConfig'])
  },
  methods: {
    // meta中指定路由是否可缓存在第一次访问时，增加到include中
    generateInclude (route) {
      if (route.meta && route.meta.keepAlive && this.includeRoute.indexOf(route.name) < 0) {
        this.includeRoute.push(route.name)
      }
      return this.includeRoute
    }
  }
}
</script>
