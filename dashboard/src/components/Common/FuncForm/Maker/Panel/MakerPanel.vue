<template>
  <a-layout-content class="atom-maker-panel">
    <!-- 画布头部 -->
    <MakerHeader @maker-header-action="handleHeaderAction"
                 @maker-canvas-resize="handleCanvasResize"></MakerHeader>
    <div class="atom-maker-canvas-panel">
      <div :class="['atom-maker-canvas', panel]">
        <!-- 表单区域 -->
        <a-form v-bind="formConfig">
          <!-- 有元素时 -->
          <Draggable v-if="widgets"
                     :list="widgets"
                     v-bind="dragOptions"
                     itemKey="key"
                     tag="transition-group"
                     :component-data="{ name:'fade' }"
                     @add="handleWidgetAdd">
            <!-- FormItem渲染 -->
            <template #item="{ element }">
              <a-form-item :key="element.key"
                           :name="element.key"
                           :label="element.title">
                <!-- 布局元素 -->
                <template v-if="element.group === 'layout'">
                  <p>{{ `layout: ${element}` }}</p>
                </template>
                <!-- form组件元素 -->
                <FieldRender v-else
                             :field="element.options"></FieldRender>
              </a-form-item>
            </template>
          </Draggable>
          <!-- 没有元素时 -->
          <a-empty v-else description="从左侧拖拽或点击来添加字段"/>
        </a-form>
      </div>
    </div>
  </a-layout-content>
</template>

<script>
/**
 * 画布面板
 */
import MakerHeader from '../Widget/MakerHeader'
import Draggable from 'vuedraggable'
import FieldRender from '../../Form/Render/FieldRender'
export default {
  name: 'MakerPanel',
  components: { MakerHeader, Draggable, FieldRender },
  props: {
    // 表单配置
    makerConfig: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      // 画布的样式 mac | pad | phone
      panel: 'mac',
      // 组件列表，从组件库中拖过来的信息，需要在结束后增加一个唯一性key
      widgets: [],
      // 当前操作的组件
      curWidget: {},
      // 表单配置信息
      formConfig: {
        layout: 'horizontal'
      },
      // 拖动配置
      dragOptions: {
        animation: 300,
        group: { name: 'widgets' },
        sort: true,
        ghostClass: 'atom-widget-ghost'
      }
    }
  },
  methods: {
    // 响应头部点击响应
    handleHeaderAction (action) {
      console.log(action)
    },
    // 响应画布大小调整
    handleCanvasResize (panel) {
      this.panel = panel
    },
    // 响应组件的增加
    handleWidgetAdd (event) {
      // 设置当前操作的组件
      this.curWidget = this.widgets[event.newIndex]
    }
  }
}
</script>
