<template>
  <a-card class="atom-form" v-if="fields" v-bind="$attrs" :bordered="false">
    <!-- 表单头 -->
    <template v-if="title" #title>
      <FuncTitle :title="title"></FuncTitle>
    </template>
    <!-- 表单头右侧扩展按钮 -->
    <template v-if="$slots.extra" #extra>
      <slot name="extra"></slot>
    </template>
    <!-- 表单区域 -->
    <a-form ref="funcForm"
            :model="model"
            :layout="layout"
            :labelAlign="labelAlign"
            :labelCol="layout !== 'vertical' ? labelCol : null"
            :wrapperCol="layout !== 'vertical' ? wrapperCol : null">
      <!-- FormItem渲染 -->
      <a-form-item v-for="field in renderFields"
                   :key="field.name"
                   :name="field.name"
                   :label="field.label"
                   :rules="initRules(field)"
                   :colon="field.colon"
                   :extra="field.extra"
                   :help="field.help">
        <!-- 根据field的slot名称设置挂载点，field采用slot方式挂载时，优先级最高 -->
        <slot v-if="field.slot" :name="field.slot" :field="field" :model="model"></slot>
        <!-- inputGroup字段 -->
        <FieldRender v-else-if="field.type === 'inputGroup'"
                     :field="field"
                     :size="field.size || size">
          <template #group>
            <!-- 如果里面带slot时，则优先挂载slot -->
            <template v-for="groupField in field.group">
              <slot v-if="groupField.slot" :name="groupField.slot" :field="groupField" :model="model"></slot>
              <FieldRender v-else
                           :key="groupField.name"
                           :field="groupField"
                           :size="groupField.size || size"
                           v-model="model[groupField.name]"></FieldRender>
            </template>
          </template>
        </FieldRender>
        <!-- 非inputGroup字段 -->
        <FieldRender v-else :field="field" :size="field.size || size" v-model="model[field.name]"></FieldRender>
      </a-form-item>
      <!-- 操作按钮区域 -->
      <a-form-item v-if="!hiddenFooter"
                   :wrapperCol="{ span: 24 }"
                   class="atom-form-footer" :style="{ textAlign: footerAlign }">
        <!-- 通过slot挂载操作按钮-优先级最高 -->
        <slot v-if="$slots.footer" name="footer" :handleClick="handleAction"></slot>
        <!-- 通过footerActions传入操作按钮-优先级中等 -->
        <template v-else-if="footerActions">
          <a-button v-for="action in footerActions"
                    :key="action.name"
                    v-bind="action"
                    @click="handleAction(action.name)">{{ action.title }}</a-button>
        </template>
        <!-- 默认操作按钮-优先级最低 -->
        <template v-else>
          <a-button key="cancel"
                    @click="handleAction('cancel')">{{ $t('global.cancel') }}</a-button>
          <a-button key="submit"
                    type="primary"
                    @click="handleAction('submit')">{{ $t('global.submit') }}</a-button>
        </template>
      </a-form-item>
    </a-form>
  </a-card>
</template>

<script>
/**
 * 列表展示类型的表单
 */
import FuncTitle from '@/components/Common/FuncTitle'
import FieldRender from './Render/FieldRender'
import form from './mixins/form'
export default {
  name: 'FormList',
  components: {
    FuncTitle,
    FieldRender
  },
  mixins: [form],
  props: {
    // 表单的头名称
    title: {
      type: String
    },
    /**
     * formList仅支持'horizontal'|'vertical'两种形式
     * horizontal: label和content同一行
     * vertical: label和content不同行
     */
    layout: {
      type: String,
      default: 'horizontal'
    },
    // 是否隐藏footer
    hiddenFooter: {
      type: Boolean,
      default: false
    },
    /**
     * footer自定义按钮，优先级slot最高，actions其次，最后是默认
     * 默认为确认和取消两个按钮
     * actions: [{ }] 具体结构如下
     * name: action的名称
     * title: 按钮的title
     * 以及其他tooltip和button的属性值
     */
    footerActions: {
      type: Array
    },
    /**
     * 按钮的对齐方式
     * center | right | left
     */
    footerAlign: {
      type: String,
      default: 'center'
    }
  },
  computed: {
    // 用于渲染的fields，深度克隆防止互相影响
    renderFields () {
      return this.$utils.deepClone(this.fields)
    }
  },
  emits: ['form-action'],
  methods: {
    // 响应footer action
    handleAction (name) {
      if (name === 'cancel') {
        // 取消响应
        this.resetForm()
      } else if (name === 'submit') {
        // 提交响应
        this.submitForm()
      }
      this.$emit('form-action', name)
    }
  }
}
</script>

<style lang="less">
@import "Form";
</style>
