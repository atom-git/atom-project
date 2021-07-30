<template>
  <div v-if="funcZone" class="atom-table-func">
    <!-- 新增功能按钮 -->
    <span class="atom-func-btn" v-permission="funcZone.add.permission">
      <TipButton v-if="funcZone.add"
                 :type="funcZone.add.type || 'primary'"
                 :icon="funcZone.add.icon || Default.ACTION_ADD.icon"
                 @click="handleClick(funcZone.add, Default.ACTION_ADD, funcZone.add.extend || false)">
        {{ funcZone.add.title || Default.ACTION_ADD.title }}
      </TipButton>
    </span>

    <!-- 其他附加功能按钮，action定义不能是【新增:add,编辑:edit,下载:download,导入:upload,删除:delete,详情:detail】中的一个 -->
    <template v-if="funcZone.extend">
      <span class="atom-func-btn"
            v-for="tipButton in funcZone.extend"
            :key="tipButton.action"
            v-permission="tipButton.permission">
        <TipButton :type="tipButton.type || 'default'"
                   :icon="tipButton.icon || ''"
                   @click="handleClick(tipButton, tipButton, true)">{{ tipButton.title || '' }}</TipButton>
      </span>
    </template>
    <a-divider v-if="funcZone.add || funcZone.extend" type="vertical"/>

    <!-- 默认功能按钮 -->
    <!-- download 下载 -->
    <a-tooltip v-if="funcZone.download"
               :title="funcZone.download.title || Default.ACTION_DOWNLOAD.title">
      <IconFont :type="funcZone.download.icon || Default.ACTION_DOWNLOAD.icon"
                @click="handleClick(funcZone.download, Default.ACTION_DOWNLOAD, funcZone.download.extend || false)"/>
    </a-tooltip>
    <!-- upload 导入 -->
    <a-tooltip v-if="funcZone.upload"
               :title="funcZone.upload.title || Default.ACTION_UPLOAD.title">
      <IconFont :type="funcZone.upload.icon || Default.ACTION_UPLOAD.icon"
                @click="handleClick(funcZone.upload, Default.ACTION_UPLOAD, funcZone.upload.extend || false)"/>
    </a-tooltip>
    <!-- refresh 刷新 -->
    <a-tooltip v-if="funcZone.refresh"
               :title="funcZone.refresh.title || Default.ACTION_REFRESH.title">
      <IconFont :type="funcZone.refresh.icon || Default.ACTION_REFRESH.icon"
                @click="handleClick(funcZone.refresh, Default.ACTION_REFRESH, funcZone.refresh.extend || false)"/>
    </a-tooltip>
    <!-- setting 列配置 -->
    <a-popover v-if="funcZone.setting"
               placement="bottomRight"
               :trigger="['click']">
      <a-tooltip :title="funcZone.setting.title || Default.ACTION_SETTING.title">
        <IconFont :type="funcZone.setting.icon || Default.ACTION_SETTING.icon"/>
      </a-tooltip>
      <template #title>
        <a-row>
          <a-col span="12" :style="{ lineHeight: '32px' }">
            <a-checkbox v-model:checked="checkedAll" @change="toogleChecked" :indeterminate="indeterminate">列设置</a-checkbox>
          </a-col>
          <a-col span="12" :style="{ textAlign: 'right' }">
            <a-button type="link" @click="handleReset">重置</a-button>
          </a-col>
        </a-row>
      </template>
      <template #content>
        <div :style="{ width: '168px', maxHeight: '260px', overflow: 'auto' }">
          <a-tree class="atom-table-column-tree"
                  v-model:checkedKeys="checkedColumns"
                  checkable
                  showLine
                  :selectable="false"
                  :treeData="columns">
            <!-- 外部$slots传入的自定义挂载点 -->
            <template v-for="slotName in titleSlots" #[slotName]>
              <slot :name="slotName"></slot>
            </template>
          </a-tree>
        </div>
      </template>
    </a-popover>
  </div>
</template>
<script>
/**
 * 多功能按钮区域
 * funcZone: [Object{ TipButton属性 }] 新增，下载等功能按钮区，具备默认实现逻辑 [add, download, upload, refresh, setting]
 *           如果需要自定义，action事件名称不能与默认一致，默认事件包括[新增:add,编辑:edit,下载:download,导入:upload,删除:delete,详情:detail]
 *           示例:
 *           {
 *              [add: { ...TipButton属性, permission }], // 可选配置 | Boolean, extend是true，表示需要外部扩展响应，取消默认响应
 *              [edit: { ...TipButton属性, permission }], // 可选配置 | Boolean, extend是true，表示需要外部扩展响应，取消默认响应
 *              ...
 *           }
 *           upload: 导入按钮点击后，如查有提供模板地址，则生成导入模板的下载提示
 *           默认功能按钮包括，refresh 刷新 可选[定时刷新],setting 列设置
 */
import { TipButton } from '@/components/Common/FuncButton'
export default {
  name: 'FuncZone',
  components: {
    TipButton
  },
  props: {
    // 功能区域按钮配置
    funcZone: {
      type: Object,
      required: false
    },
    // 列设置列表
    columns: {
      type: Array,
      default: () => [{}]
    },
    // 树结构标题相关挂载点
    titleSlots: {
      type: Array,
      required: false
    }
  },
  data () {
    return {
      // 刷新间隔，默认1分钟
      delay: 1,
      // 全选按钮的值
      checkedAll: false,
      // 选中的列
      checkedColumns: [],
      // 用于重置的选中列
      resetCheckedColumns: [],
      // 常量
      Default: this.$default,
      // 列设置全选与非全选
      indeterminate: false
    }
  },
  computed: {
    // 刷新选项
    refreshOptions () {
      if (this.funcZone.refresh && this.funcZone.refresh.option) {
        return this.funcZone.refresh.option
      } else {
        return [{ delay: 1, label: '定时一分钟' }, { delay: 3, label: '定时三分钟' }, { delay: 5, label: '定时五分钟' }]
      }
    },
    // 字段列表的长度
    columnsSize () {
      return this.columns.length
    }
  },
  emits: ['table-column-change', 'table-func-action'],
  watch: {
    checkedColumns: {
      deep: true,
      handler (newValue) {
        // 设置是否全部选中
        this.checkedAll = this.checkedColumns.length === this.columnsSize
        this.indeterminate = this.checkedColumns.length !== 0 && this.checkedColumns.length < this.columnsSize
        this.$emit('table-column-change', newValue)
      }
    }
  },
  created () {
    // 初始化选中状态
    this.checkedColumns = this.initCheckedColumns(this.columns)
    // 设置重置状态
    this.resetCheckedColumns = this.checkedColumns
    // 设置是否全部选中
    this.checkedAll = this.checkedColumns.length === this.columnsSize
    this.indeterminate = this.checkedColumns.length !== 0 && this.checkedColumns.length < this.columnsSize
  },
  methods: {
    // 初始化选中的字段
    initCheckedColumns (columns) {
      const checkedColumns = []
      columns.map(column => {
        // 格式化column防止出现没有key值的情况
        column.key = column.key || column.dataIndex || column.title
        // 配置show或者hidden或者有children且show的字段大于0时该字段show=true
        column.show = column.show || !column.hidden || (this.$utils.isValid(column.children) && column.children.filter(child => child.show).length > 0)
        if (column.show) {
          // 存入keys
          checkedColumns.push(column.key)
        }
        // 写入子集的选中结果
        if (this.$utils.isValid(column.children)) {
          checkedColumns.push(...this.initCheckedColumns(column.children))
        }
      })
      return checkedColumns
    },
    // 响应按钮点击
    handleClick (action, defaultAction, extend) {
      // 用默认的action保障事件action的完整性
      this.$emit('table-func-action', Object.assign(defaultAction, action), extend)
    },
    // 响应列设置的全选与非全选
    toogleChecked ($event) {
      // 重新生成选中未选中
      this.generateCheckedColumns(this.columns, $event.target.checked)
    },
    /**
     * 生成选中的列
     * @param columns 原始列
     * @param checkedAll true | false | undefined
     */
    generateCheckedColumns (columns, checkedAll) {
      if (checkedAll) {
        columns.map(column => {
          // 不包括时才写入
          if (!this.checkedColumns.includes(column.key)) {
            this.checkedColumns.push(column.key)
          }
          if (this.$utils.isValid(column.children)) {
            this.generateCheckedColumns(column.children, checkedAll)
          }
        })
      } else {
        this.checkedColumns = []
      }
    },
    // 响应选中重置
    handleReset () {
      this.checkedColumns = this.resetCheckedColumns
    }
  }
}
</script>

<style lang="less">
.atom-table-func {
  .atom-func-btn {
    margin-right: 8px;
  }
  >.anticon {
    font-size: 16px;
    padding: 8px;
    &:last-child {
      margin-right: -8px;
    }
  }
}
</style>
