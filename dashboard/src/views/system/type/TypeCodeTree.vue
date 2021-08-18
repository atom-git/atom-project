<template>
  <SideLayout sideTitle="数据字典维值" :side="diaglogSize >= 720 ? 8 : 24">
    <template #sider>
      <MenuTree :loading="loading"
                :tree="sysTypeCodeTree"
                :replaceKeys="replaceFields"
                :actions="actions"
                maxHeight="45%"
                @tree-select="handleTreeSelect"
                @tree-node-action="handleAction"></MenuTree>
    </template>
    <template #content>
      <FormList ref="typeForm"
                :title="formTitle"
                :fields="fields"
                v-model="sysTypeCode"
                @submit="handleFormSubmit">
        <template #extra>
          <a-button type="primary" @click="handleAdd">
            <IconFont type="FileAddOutlined"/>新增
          </a-button>
        </template>
        <template #footer="{ handleClick }">
          <a-button @click="handleClick('submit')" type="primary" block :loading="loading">更新数据字典</a-button>
        </template>
      </FormList>
    </template>
  </SideLayout>
</template>

<script>
import MenuTree from '@/components/Common/MenuTree'
import { FormList } from '@/components/Common/FuncForm'
import { SideLayout } from '@/layouts'
import { createVNode } from 'vue'
import { ExclamationCircleOutlined } from '@ant-design/icons-vue'
// sysTypeCode默认值
const defaultSysTypeCode = { typeName: '', typeDesc: '', editable: true }
export default {
  name: 'TypeCodeTree',
  components: { SideLayout, MenuTree, FormList },
  props: {
    // 数据字典信息
    sysType: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      // 替换字段
      replaceFields: this.$api.system.type.replaceFields,
      // 数据字典维值列表
      sysTypeCodeTree: [],
      // 数据字典维值
      sysTypeCode: defaultSysTypeCode,
      // 表单的动作
      formAction: this.$default.ACTION_ADD,
      // 菜单树的按钮
      actions: [
        this.$default.ACTION_ADD,
        this.$default.ACTION_DELETE,
        this.$default.ACTION_MOVE_UP,
        this.$default.ACTION_MOVE_DOWN
      ],
      diaglogSize: this.$default.dialog.size,
      // 请求loading
      loading: false
    }
  },
  computed: {
    // 表单的fields
    fields () {
      return [
        { type: 'text', label: '类型名称', name: 'typeName', rules: [{ required: true, max: 20 }] },
        { type: 'text', label: '类型描述', name: 'typeDesc', rules: [{ required: true, max: 50 }] },
        { type: 'treeSelect', label: '父级类型', name: 'parentId', treeData: this.sysTypeCodeTree, replaceFields: this.replaceFields },
        { type: 'number', label: '优先级', name: 'typeOrder', rules: [{ required: true }] }
      ]
    },
    // 表单的标题
    formTitle () {
      return `【${this.formAction.title}】系统数据字典维值信息`
    }
  },
  watch: {
    // 监听sysType的变化
    sysType: {
      immediate: true,
      deep: true,
      handler (newValue) {
        // 重新加载数据字典维值
        this.loadSysTypeCodeTree(newValue)
      }
    }
  },
  methods: {
    // 加载数据字典维值树
    loadSysTypeCodeTree (sysType) {
      if (this.$utils.isValid(sysType)) {
        this.loading = true
        this.$api.system.type.codeTree({ meanId: sysType.id }).then(sysTypeCodeTree => {
          this.sysTypeCodeTree = sysTypeCodeTree
        }).finally(() => { this.loading = false })
      }
    },
    // 响应树节点的选择
    handleTreeSelect (nodeKey, treeNode) {
      this.formAction = this.$default.ACTION_EDIT
      this.sysTypeCode = treeNode
    },
    // 响应角色新增
    handleAdd () {
      this.formAction = this.$default.ACTION_ADD
      this.sysTypeCode = this.$utils.deepClone(defaultSysTypeCode)
    },
    // 响应字典扩展操作
    handleAction (action, treeNode) {
      if (action === this.$default.ACTION_ADD.name) {
        // 新增时，重置右侧表单
        this.formAction = this.$default.ACTION_ADD
        // 根据当前菜单的子集的order最大值，设置order默认值
        const children = treeNode.children
        let typeOrder = 1
        if (this.$utils.isValid(children)) {
          const orderArray = this.$utils.simpleList(children, 'typeOrder')
          orderArray.sort(this.$utils.sortNumDesc)
          typeOrder = orderArray[0] + 1
        }
        this.sysTypeCode = { parentId: treeNode.id, typeOrder: typeOrder }
      } else if (action === this.$default.ACTION_DELETE.name) {
        this.sysTypeCode = treeNode
        // 删除时弹出删除框
        const self = this
        this.$modal.$confirm({
          icon: createVNode(ExclamationCircleOutlined),
          okType: 'danger',
          content: `确定要删除数据字典维值【${treeNode.typeName}】吗？`,
          onOk () {
            self.$api.system.type.deleteCode(self.sysTypeCode.id).then(() => {
              self.sysTypeCode = self.$utils.deepClone(defaultSysTypeCode)
              // 提示删除成功
              self.$message.success('系统数据字典维值删除成功！')
              // 刷新数据
              self.loadSysTypeCodeTree(self.sysType)
            })
          }
        })
      } else if (action === this.$default.ACTION_MOVE_UP.name || action === this.$default.ACTION_MOVE_DOWN.name) {
        this.loading = true
        this.$api.system.type.exchange(action, treeNode).then(sysTypeCode => {
          this.sysTypeCode = sysTypeCode
          this.$message.success('系统数据字典维值顺序更新成功！')
          this.loadSysTypeCodeTree(this.sysType)
        }).finally(() => { this.loading = false })
      }
    },
    // 响应数据编辑提交
    handleFormSubmit (sysTypeCode) {
      this.loading = true
      this.$refs.typeForm.validate().then(() => {
        // 新增或者编辑的数据提交
        this.$api.system.type.updateCode(this.sysType, sysTypeCode).then(() => {
          this.sysTypeCode = this.$utils.deepClone(defaultSysTypeCode)
          this.$message.success('系统数据字典维值信息更新成功！')
          this.loadSysTypeCodeTree(this.sysType)
        }).finally(() => { this.loading = false })
      }).catch(() => { this.loading = false })
    }
  }
}
</script>
