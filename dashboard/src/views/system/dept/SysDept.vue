<template>
  <SideLayout sideTitle="组织架构">
    <template #sider>
      <MenuTree :search="true"
                :options="sysDeptList"
                :loading="loading"
                :tree="sysDeptTree"
                :actions="actions"
                :replaceKeys="replaceFields"
                @tree-select="handleTreeSelect"
                @tree-node-action="handleAction"></MenuTree>
    </template>
    <template #content>
      <FormList ref="deptForm"
                :title="formTitle"
                :fields="fields"
                v-model="sysDept"
                @submit="handleFormSubmit">
        <template #extra>
          <a-button type="primary" @click="handleAdd">
            <IconFont type="FileAddOutlined"/>新增
          </a-button>
        </template>
        <template #footer="{ handleClick }">
          <a-button @click="handleClick('submit')" type="primary" block :loading="loading">更新组织信息</a-button>
        </template>
      </FormList>
    </template>
  </SideLayout>
</template>

<script>
/**
 * 组织管理
 */
import { SideLayout } from '@/layouts'
import MenuTree from '@/components/Common/MenuTree'
import { FormList } from '@/components/Common/FuncForm'
import { createVNode } from 'vue'
import { ExclamationCircleOutlined } from '@ant-design/icons-vue'
// sysDept默认值
const defaultSysDept = { ifValid: 1 }
export default {
  name: 'SysDept',
  components: { SideLayout, MenuTree, FormList },
  data () {
    return {
      // 替代字段
      replaceFields: this.$api.system.dept.replaceFields,
      // 组织架构列表
      sysDeptList: [],
      // 组织架构树
      sysDeptTree: [],
      // 当前组织对象
      sysDept: defaultSysDept,
      // 表单的动作
      formAction: this.$default.ACTION_ADD,
      // 组织树的按钮
      actions: [
        this.$default.ACTION_ADD,
        this.$default.ACTION_DELETE
      ],
      // 请求loading
      loading: false
    }
  },
  computed: {
    // 表单的fields
    fields () {
      return [
        { type: 'text', label: '部门名称', name: 'deptName', rules: [{ required: true }] },
        { type: 'text', label: '部门描述', name: 'deptDesc' },
        { type: 'text', label: '负责人', name: 'leaderName' },
        { type: 'text', label: '负责人电话', name: 'leaderPhone', rules: [{ required: true, type: 'phone' }] },
        { type: 'treeSelect', label: '上级部门', name: 'deptParent', treeData: this.sysDeptTree, replaceFields: this.replaceFields },
        { type: 'radio', label: '状态', name: 'ifValid', default: 1, options: [{ title: '禁用', value: 0 }, { title: '启用', value: 1 }], rules: [{ type: 'integer', required: true }] }
      ]
    },
    // 表单的标题
    formTitle () {
      return `【${this.formAction.title}】系统组织信息`
    }
  },
  methods: {
    // 加载组织机构树
    loadSysDeptTree () {
      this.loading = true
      this.$api.system.dept.tree().then(sysDeptList => {
        this.sysDeptList = sysDeptList
        // 转成树结构
        this.sysDeptTree = this.$utils.buildTree(sysDeptList, 'id', 'deptParent')
      }).finally(() => {
        this.loading = false
      })
    },
    // 响应树节点的选择
    handleTreeSelect (nodeKey, treeNode) {
      this.formAction = this.$default.ACTION_EDIT
      this.sysDept = treeNode
    },
    // 响应角色新增
    handleAdd () {
      this.formAction = this.$default.ACTION_ADD
      this.sysDept = defaultSysDept
    },
    // 响应菜单扩展操作
    handleAction (action, treeNode) {
      if (action === this.$default.ACTION_ADD.name) {
        // 新增时，重置右侧表单
        this.formAction = this.$default.ACTION_ADD
        this.sysDept = { deptParent: treeNode.id, ifValid: 1 }
      } else {
        this.sysDept = treeNode
        // 删除时弹出删除框
        const self = this
        this.$modal.$confirm({
          icon: createVNode(ExclamationCircleOutlined),
          okType: 'danger',
          content: `确定要删除组织【${treeNode.deptName}】吗？`,
          onOk () {
            self.$api.system.dept.delete(self.sysDept.id).then(() => {
              self.sysDept = defaultSysDept
              // 提示删除成功
              self.$message.success('系统组织删除成功！')
              // 刷新数据
              self.loadSysDeptTree()
            })
          }
        })
      }
    },
    // 响应数据编辑提交
    handleFormSubmit (sysDept) {
      this.loading = true
      this.$refs.deptForm.validate().then(() => {
        // 新增或者编辑的数据提交
        this.$api.system.dept.update(sysDept).then(() => {
          this.$message.success('系统组织信息更新成功！')
          this.loadSysDeptTree()
        }).finally(() => { this.loading = false })
      }).catch(() => { this.loading = false })
    }
  },
  created () {
    // 加载组织树数据
    this.loadSysDeptTree()
  }
}
</script>
