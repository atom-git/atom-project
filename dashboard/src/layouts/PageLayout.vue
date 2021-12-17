<template>
  <div class="atom-page-layout" v-water-mark="waterMark">
    <router-view/>
  </div>
</template>

<script>
import user from '@/config/mixins/user'
export default {
  name: 'PageLayout',
  mixins: [user]
}
</script>

<style lang="less">
.atom-page-layout {
  height: 100%;
  &>.ant-card {
    &:not(:first-child) {
      margin-top: 16px;
    }
  }
}
</style>
