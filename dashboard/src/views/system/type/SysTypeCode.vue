<template>
  <Dialog v-model="visible"
          :maskClosable="false"
          :closable="false"
          :footer="null">
    <!-- 单层级 -->
    <FormatTable title="数据字典配置"
                 :columns="columns"
                 :dataSource="sysTypeCodeList"
                 :pagination="false"
                 :funcZone="funcZone"
                 :loading="loading"
                 @table-func-action="handleFuncAction">
      <template #typeName="{ text, row }">
        <a-input v-if="row.editable" v-model="row.typeName" :maxLength="20" allowClear>
          <template #prefix><a-tooltip title="最大长度20"><IconFont type="InfoCircleOutlined"/></a-tooltip></template>
        </a-input>
        <template v-else>{{ text }}</template>
      </template>
      <template #typeDesc="{ text, row }">
        <a-input v-if="row.editable" v-model="row.typeName" :maxLength="50" allowClear>
          <template #prefix><a-tooltip title="最大长度50"><IconFont type="InfoCircleOutlined"/></a-tooltip></template>
        </a-input>
        <template v-else>{{ text }}</template>
      </template>
      <template #action="{ row }">
        <TipButtonGroup v-if="row.editable" :actions="saveActions" @click="handleRowAction($event, row)"></TipButtonGroup>
        <TipButtonGroup v-else :actions="editActions" @click="handleRowAction($event, row)"></TipButtonGroup>
      </template>
    </FormatTable>
    <!-- 多层级 -->
    <div></div>
    <template #footer>
      <a-button @click="() => { this.visible = false; $emit('cancel') }">关闭</a-button>
    </template>
  </Dialog>
</template>

<script>
/**
 * 数据字典维值管理
 */
import Dialog from '@/components/Common/Dialog'
import { FormatTable } from '@/components/Common/FuncTable'
import { TipButtonGroup } from '@/components/Common/FuncButton'
// sysTypeCode默认值
const defaultSysTypeCode = { typeName: '', typeDesc: '', editable: true }
export default {
  name: 'SysTypeCode',
  components: { Dialog, FormatTable, TipButtonGroup },
  props: {
    // 操作窗口显隐
    modelValue: {
      type: Boolean,
      default: false
    },
    // 数据字典信息
    sysType: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      // 维值字段列表
      columns: [
        { dataIndex: 'typeName', title: '类型名称', width: 160, slots: { customRender: 'typeName' } },
        { dataIndex: 'typeDesc', title: '类型描述', width: 160, slots: { customRender: 'typeDesc' } },
        { dataIndex: 'action', title: '操作', slots: { customRender: 'action' } }
      ],
      // 数据字典维值列表
      sysTypeCodeList: [],
      // 功能按钮
      funcZone: {
        add: { extend: true },
        refresh: { extend: true }
      },
      // 编辑按钮
      editActions: [{ title: '编辑', name: 'edit' }, { title: '删除', name: 'delete' }],
      // 保存按钮
      saveActions: [{ title: '保存', name: 'save' }, { title: '取消', name: 'cancel' }],
      // 请求loading
      loading: false,
      // dialog是否显示
      visible: false
    }
  },
  watch: {
    // 监听窗口显隐
    modelValue (newValue) {
      this.visible = newValue
    },
    // 监听内部窗口显隐
    visible (newValue) {
      this.$emit('update:modelValue', newValue)
    }
  },
  methods: {
    // 加载数据字典维值
    loadSysTypeCodeList () {
      this.loading = true
      this.$api.system.type.codeList({ meanId: this.sysType.id }).then(sysTypeCodeList => {
        this.sysTypeCodeList = sysTypeCodeList
      }).finally(() => { this.loading = false })
    },
    // 响应表格功能按钮
    handleFuncAction (action) {
      if (action.name === this.$default.ACTION_ADD.name) {
        // 新增
        this.sysTypeCodeList.push(defaultSysTypeCode)
      } else if (action.name === this.$default.ACTION_REFRESH.name) {
        // 刷新
        this.loadSysTypeCodeList()
      }
    },
    // 响应行操作
    handleRowAction (action, row) {
      console.log(action, row)
    }
  },
  mounted () {
    // 加载数据字典维值
    this.loadSysTypeCodeList()
  }
}
</script>
