<template>
  <a-card class="atom-desc" :bordered="false">
    <!-- 标题 -->
    <template v-if="title" #title>
      <!-- 按照FuncTitle来生成 -->
      <FuncTitle :title="title"></FuncTitle>
    </template>

    <!-- 右侧功能按钮 -->
    <template v-if="funcZone" #extra>
      <FuncZone :funcZone="editable ? editActions : funcZone"
                @table-func-action="handleFuncAction">
      </FuncZone>
    </template>

    <!-- 顶部提示区域 -->
    <template v-if="$slots.alert">
      <slot name="alter"></slot>
    </template>
    <a-alert v-if="alert && alert.message"
             :message="alert.message" :type="alert.type" :closable="alert.closable || false" />

    <!-- 顶部提示区域 -->
    <a-descriptions v-if="$utils.isValid(dataSource)"
                    :bordered="bordered"
                    :colon="colon"
                    :column="span"
                    :layout="layout"
                    :size="size"
                    :labelStyle="labelStyle"
                    :contentStyle="contentStyle">
      <a-descriptions-item v-for="column in columns"
                           :key="column.dataIndex"
                           :class="column.class"
                           :label="column.title"
                           :span="column.span">
        <!-- 如果有挂载点时优先外部 -->
        <slot v-if="column.slot"
              :name="column.slot"
              :data="dataSource"
              :editable="editable"
              :column="column"></slot>
        <!-- 如果没有挂载点，则按照格式化字段写入 -->
        <template v-else>
          <!-- 是否可编辑的判断 -->
          <FieldRender v-if="editable"
                       :size="size"
                       :field="initField(column)"
                       v-model="dataSource[column.dataIndex]"
                       @change="handleFieldChange($event, column)"></FieldRender>
          <FormatColumn v-else
                        :column="column"
                        :text="dataSource[column.dataIndex]"
                        :row="dataSource"></FormatColumn>
        </template>
      </a-descriptions-item>
    </a-descriptions>
    <!-- 没有数据时显示为空 -->
    <a-empty v-else description="暂无数据"/>
  </a-card>
</template>

<script>
/**
 * 格式化描述展现
 */
import FuncTitle from '@/components/Common/FuncTitle'
import FuncZone from '@/components/Common/FuncZone'
import FormatColumn from '@/components/Advance/FuncTable/Render/FormatColumn'
import FieldRender from '@/components/Advance/FuncForm/Form/Render/FieldRender'
export default {
  name: 'FormatDesc',
  components: { FuncTitle, FuncZone, FormatColumn, FieldRender },
  props: {
    /**
     * 字段列表 { title, dataIndex, span, class, format, form }
     * format: 参考FormatColumn
     * form: { type, options, slot, 其他相应类型的FormItem所对应的属性 }
     **/
    columns: {
      type: Array,
      required: true
    },
    // 要展示的数据
    modelValue: {
      type: Object,
      required: false
    },
    // 顶部提示区域
    alert: {
      type: Object,
      required: false
    },
    // 顶部右侧功能按钮区
    funcZone: {
      type: Object,
      required: false
    },
    // 自定义提交的逻辑 返回类型为Promise
    onSubmit: {
      type: Function,
      required: false
    },
    // 是否加载中
    loading: {
      type: Boolean,
      default: false
    },
    // 是否显示边框
    bordered: {
      type: Boolean,
      default: true
    },
    // 冒号是否显示，无边框时默认显示
    colon: {
      type: Boolean,
      default: true
    },
    // 一行展示多少列，支持响应式写法
    span: {
      type: [Number, Object],
      default: () => ({ xxl: 4, xl: 3, lg: 3, md: 2, sm: 2, xs: 1 })
    },
    // 描述布局 horizontal | vertical
    layout: {
      type: String,
      default: 'horizontal'
    },
    // 设置列表的大小（只有设置 bordered={true} 生效）default | middle | small
    size: {
      type: String,
      default: 'default'
    },
    // 描述列表的标题，显示在最顶部，默认采用FuncTitle代替
    title: {
      type: String,
      required: false
    },
    // 自定义标签样式
    labelStyle: {
      type: Object,
      required: false
    },
    // 自定义内容样式
    contentStyle: {
      type: Object,
      required: false
    }
  },
  data () {
    return {
      // 内部绑定的数据
      dataSource: {},
      // 是否可编辑
      editable: false,
      // 操作按钮
      editActions: {
        extend: [
          { name: 'submit', title: '确认', type: 'primary' },
          { name: 'cancel', title: '取消' }
        ]
      }
    }
  },
  watch: {
    // 监听外部传入的值变化实现双绑
    modelValue: {
      deep: true,
      immediate: true,
      handler (newValue) {
        this.dataSource = newValue
      }
    },
    // 监听内部的值变化
    dataSource: {
      deep: true,
      handler (newValue) {
        this.$emit('update:modelValue', newValue)
        this.$emit('change', newValue)
      }
    }
  },
  emits: ['update:modelValue', 'change', 'desc-func-action'],
  methods: {
    /**
     * 响应功能区域操作按钮
     * @param action 响应事件的名称
     * @param extend 是否扩展功能，true | false，true事件直接上抛不做默认处理
     */
    handleFuncAction (action, extend) {
      if (extend) {
        if (action.name === 'submit') {
          this.$emit('update:modelValue', this.dataSource)
          // 只有在传入onSubmit时才在内部关闭
          if (this.$utils.isFunction(this.onSubmit)) {
            this.onSubmit(this.dataSource).then(() => {
              this.editable = false
            })
          }
        } else if (action.name === 'cancel') {
          this.editable = false
        }
        this.$emit('desc-func-action', action)
      } else {
        // 如果是编辑，则构建内部编辑功能
        if (action.name === this.$default.ACTION.EDIT.name) {
          this.editable = true
        }
      }
    },
    // 根据字段初始化field
    initField (column) {
      return Object.assign({}, column, column.form, {
        type: (column.form && column.form.type) || column.type || 'text',
        label: (column.form && column.form.label) || column.title,
        name: (column.form && column.form.name) || column.dataIndex,
      })
    },
    // 响应字段值改变
    handleFieldChange (value, column) {
      this.dataSource[column.dataIndex] = value
    },
    // 切换editable状态
    toggleEditable () {
      this.editable = !this.editable
    }
  }
}
</script>

<style lang="less">
.atom-desc {
  .atom-desc-footer {
    text-align: center;
    margin-top: 16px;
    .ant-btn:not(:last-child) {
      margin-right: 16px;
    }
  }
}
</style>
