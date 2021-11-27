<template>
  <div v-if="funcZone" class="atom-func-zone">
    <!-- 带文本的大按钮展示区域 -->
    <span v-if="checkable" class="atom-func-btn">
      <span class="atom-checked-count">
        已选择<a-tooltip title="点击清空"><span @click="handleClearCheck">{{ checkedCount }}</span></a-tooltip>项
      </span>
      <!-- 全选按钮 -->
      <a-checkbox v-if="checkallShow" v-model:checked="checkall" @change="handleCheckAll">{{ checkall ? '清空' : '全选' }}</a-checkbox>
    </span>
    <!-- 批量删除，在打开选择功能后，根据外部配置决定是否要出现此按钮 -->
    <span v-if="funcZone.delete && checkable"
          class="atom-func-btn"
          v-permission="funcZone.delete && funcZone.delete.permission">
       <TipButton :type="funcZone.delete.type || 'primary'"
                  :danger="true"
                  :icon="funcZone.delete.icon || Default.ACTION.DELETE.icon"
                  @click="handleClick(funcZone.delete, Default.ACTION.DELETE, funcZone.delete.extend || false)">
        {{ funcZone.delete.title || Default.ACTION.DELETE.title }}
      </TipButton>
    </span>
    <!-- 选择/取消选择 自动切换，选择按钮出现时，前面追加全选功能按钮 -->
    <span v-if="funcZone.check"
          class="atom-func-btn">
      <TipButton :type="funcZone.check.type || 'primary'"
                 :icon="funcZone.check.icon || Default.ACTION.CHECK.icon"
                 @click="handleClick(funcZone.check, Default.ACTION.CHECK, false)">
        {{ checkable ? '取消' : funcZone.check.title || Default.ACTION.CHECK.title }}
      </TipButton>
    </span>
    <a-divider v-if="checkable && funcZone.delete" type="vertical"/>

    <!-- 新增功能按钮 -->
    <span v-if="funcZone.add"
          class="atom-func-btn"
          v-permission="funcZone.add && funcZone.add.permission">
      <TipButton :type="funcZone.add.type || 'primary'"
                 :icon="funcZone.add.icon || Default.ACTION.ADD.icon"
                 @click="handleClick(funcZone.add, Default.ACTION.ADD, funcZone.add.extend || false)">
        {{ funcZone.add.title || Default.ACTION.ADD.title }}
      </TipButton>
    </span>

    <!-- 编辑功能按钮，主要用于FuncDesc组件中 -->
    <span v-if="funcZone.edit"
           class="atom-func-btn"
          v-permission="funcZone.edit && funcZone.edit.permission">
      <TipButton :type="funcZone.edit.type || 'primary'"
                 :icon="funcZone.edit.icon || Default.ACTION.EDIT.icon"
                 @click="handleClick(funcZone.edit, Default.ACTION.EDIT, funcZone.edit.extend || false)">
        {{ funcZone.edit.title || Default.ACTION.EDIT.title }}
      </TipButton>
    </span>

    <!-- 其他附加功能按钮，table中action定义不能是【新增:add,编辑:edit,下载:download,导入:upload,删除:delete,详情:detail】中的一个，且其中没有定义extend属性为true -->
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

    <!-- 图标按钮展示区域 -->
    <!-- download 下载 -->
    <a-tooltip v-if="funcZone.download"
               :title="funcZone.download.title || Default.ACTION.DOWNLOAD.title">
      <IconFont :type="funcZone.download.icon || Default.ACTION.DOWNLOAD.icon"
                @click="handleClick(funcZone.download, Default.ACTION.DOWNLOAD, funcZone.download.extend || false)"/>
    </a-tooltip>
    <!-- upload 导入 -->
    <a-tooltip v-if="funcZone.upload"
               :title="funcZone.upload.title || Default.ACTION.UPLOAD.title">
      <IconFont :type="funcZone.upload.icon || Default.ACTION.UPLOAD.icon"
                @click="handleClick(funcZone.upload, Default.ACTION.UPLOAD, funcZone.upload.extend || false)"/>
    </a-tooltip>
    <!-- refresh 刷新 -->
    <a-tooltip v-if="funcZone.refresh"
               :title="funcZone.refresh.title || Default.ACTION.REFRESH.title">
      <IconFont :type="funcZone.refresh.icon || Default.ACTION.REFRESH.icon"
                @click="handleClick(funcZone.refresh, Default.ACTION.REFRESH, funcZone.refresh.extend || false)"/>
    </a-tooltip>
    <!-- setting 列配置 -->
    <a-popover v-if="funcZone.setting"
               placement="bottomRight"
               :trigger="['click']">
      <a-tooltip :title="funcZone.setting.title || Default.ACTION.SETTING.title">
        <IconFont :type="funcZone.setting.icon || Default.ACTION.SETTING.icon"/>
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
 * funcZone: [Object{ TipButton属性 }] 新增，下载等功能按钮区
 *           具备默认实现逻辑 [add, download, upload, refresh, setting, checked, delete]
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
    // 是否显示全选
    checkallShow: {
      type: Boolean,
      default: true
    },
    // 选中的记录数
    checkedCount: {
      type: [Number, String],
      default: 0
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
      indeterminate: false,
      // 是否是可选择状态
      checkable: false,
      // 是否全选
      checkall: false
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
  emits: ['column-change', 'func-zone-action', 'func-zone-checkall', 'func-zone-clear-check'],
  watch: {
    checkedColumns: {
      deep: true,
      handler (newValue) {
        // 设置是否全部选中
        this.checkedAll = this.checkedColumns.length === this.columnsSize
        this.indeterminate = this.checkedColumns.length !== 0 && this.checkedColumns.length < this.columnsSize
        this.$emit('column-change', newValue)
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
        if (this.$utils.isValid(column.children) && column.show) {
          checkedColumns.push(...this.initCheckedColumns(column.children))
        }
      })
      return checkedColumns
    },
    // 响应按钮点击
    handleClick (action, defaultAction, extend) {
      if (defaultAction.name === this.$default.ACTION.CHECK.name) {
        this.checkable = !this.checkable
      }
      // 用默认的action保障事件action的完整性
      this.$emit('func-zone-action', Object.assign(defaultAction, action), extend, this.checkable)
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
    },
    // 响应全选功能的选中与否
    handleCheckAll () {
      this.$emit('func-zone-checkall', this.checkall)
    },
    // 清空选择
    handleClearCheck () {
      this.$emit('func-zone-clear-check')
    }
  }
}
</script>

<style lang="less">
.atom-func-zone {
  .atom-func-btn {
    margin: 0 4px;
    &:first-child {
      margin-left: 0;
    }
    .atom-checked-count {
      margin-right: 8px;
      span {
        color: #1890FF;
        font-weight: bold;
        padding: 4px;
        cursor: pointer;
      }
    }
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
