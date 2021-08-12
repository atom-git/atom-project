<template>
  <FormatTable title="数据字典配置"
               :columns="columns"
               :dataSource="sysTypeCodeList"
               :pagination="false"
               :funcZone="funcZone"
               :loading="loading"
               @table-func-action="handleFuncAction">
    <template #typeName="{ text, row }">
      <a-input v-if="row.editable" v-model:value="row.typeName" :maxLength="20" allowClear>
        <template #prefix><a-tooltip title="最大长度20"><IconFont type="InfoCircleOutlined"/></a-tooltip></template>
      </a-input>
      <template v-else>{{ text }}</template>
    </template>
    <template #typeDesc="{ text, row }">
      <a-input v-if="row.editable" v-model:value="row.typeDesc" :maxLength="50" allowClear>
        <template #prefix><a-tooltip title="最大长度50"><IconFont type="InfoCircleOutlined"/></a-tooltip></template>
      </a-input>
      <template v-else>{{ text }}</template>
    </template>
    <template #action="{ row, index }">
      <TipButtonGroup v-if="row.editable" :actions="saveActions" @click="handleRowAction($event, row, index)"></TipButtonGroup>
      <TipButtonGroup v-else :actions="editActions" @click="handleRowAction($event, row, index)"></TipButtonGroup>
    </template>
  </FormatTable>
</template>

<script>
import { FormatTable } from '@/components/Common/FuncTable'
import { TipButtonGroup } from '@/components/Common/FuncButton'
import { createVNode } from 'vue'
import { ExclamationCircleOutlined } from '@ant-design/icons-vue'
// sysTypeCode默认值
const defaultSysTypeCode = { typeName: '', typeDesc: '', editable: true }
export default {
  name: 'TypeCodeList',
  components: { FormatTable, TipButtonGroup },
  props: {
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
    // 监听sysType的变化
    sysType: {
      immediate: true,
      deep: true,
      handler (newValue) {
        // 重新加载数据字典维值
        this.loadSysTypeCodeList(newValue)
      }
    }
  },
  methods: {
    // 加载数据字典维值列表
    loadSysTypeCodeList (sysType) {
      if (this.$utils.isValid(sysType)) {
        this.loading = true
        this.$api.system.type.codeList({ meanId: sysType.id }).then(sysTypeCodeList => {
          this.sysTypeCodeList = sysTypeCodeList
        }).finally(() => { this.loading = false })
      }
    },
    // 响应表格功能按钮
    handleFuncAction (action) {
      if (action.name === this.$default.ACTION_ADD.name) {
        // 新增
        this.sysTypeCodeList.push(this.$utils.deepClone(defaultSysTypeCode))
      } else if (action.name === this.$default.ACTION_REFRESH.name) {
        // 刷新
        this.loadSysTypeCodeList(this.sysType)
      }
    },
    // 响应行操作
    handleRowAction (action, row, index) {
      if (action.name === 'edit') {
        // 打开编辑状态
        row.editable = true
      } else if (action.name === 'delete') {
        const self = this
        this.$modal.$confirm({
          content: `确认要删除数据字典维值【${row.typeName}】吗？`,
          icon: createVNode(ExclamationCircleOutlined),
          confirmLoading: self.loading,
          onOk () {
            self.loading = true
            self.$api.system.type.deleteCode(row.id).then(() => {
              // 删除当前记录
              self.sysTypeCodeList.splice(index, 1)
              self.$message.success('系统数据字典维值删除成功！')
            }).finally(() => { self.loading = false })
          }
        })
      } else if (action.name === 'save') {
        this.loading = true
        this.$api.system.type.updateCode(this.sysType, row).then(sysTypeCode => {
          // 如果是新增，需要把id带入进去
          row.id = sysTypeCode.id
          // 关闭编辑状态
          row.editable = false
          this.$message.success('系统数据字典维值更新成功！')
        }).finally(() => { this.loading = false })
      } else if (action.name === 'cancel') {
        // 关闭编辑状态
        row.editable = false
      }
    }
  }
}
</script>
