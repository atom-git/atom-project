<template>
  <template v-for="(action, index) in actions" v-permission="action.permission">
    <a-dropdown v-if="action.children" :key="index">
      <TipButton :size="size"
                 :icon="action.icon"
                 @click="$emit('click', action)">
        {{ action.title }}<IconFont type="DownOutlined"/>
      </TipButton>
      <template #overlay>
        <a-menu>
          <a-menu-item v-for="child in action.children"
                       :key="child.name"
                       @click="$emit('click', child)">
            <template #icon><IconFont :type="child.icon"/></template>{{ child.title }}
          </a-menu-item>
        </a-menu>
      </template>
    </a-dropdown>
    <template v-else>
      <TipButton :key="action.name"
                 :size="size"
                 :icon="action.icon"
                 @click="$emit('click', action)">
        <template v-if="type === 'text' || type === 'both'">{{ action.title }}</template>
      </TipButton>
    </template>
    <a-divider :key="'divider_' + index" v-if="divider && (index < (actions.length - 1))" type="vertical"/>
  </template>
</template>

<script>
/**
 * 具有tooltip的Button组，slot:[title, default]
 * actions为[{title, icon, name, apiUrl}]的一组操作按钮，是否需要展示icon由icon属性是否配置来决定
 */
import TipButton from './TipButton'
export default {
  name: 'TipButtonGroup',
  components: {
    TipButton
  },
  props: {
    // 按钮的类型 text | icon | both
    type: {
      type: String,
      default: 'text'
    },
    // TipButtonGroup的操作按钮绑定
    actions: {
      type: Array,
      required: false
    },
    // 按钮大小 [small default large]
    size: {
      type: String,
      default: 'small'
    },
    // 是否显示分隔线
    divider: {
      type: Boolean,
      default: true
    }
  },
  emits: ['click']
}
</script>

<style lang="less">
.ant-divider, .ant-divider-vertical {
  margin: 0 4px !important;
}
</style>
