<template>
  <SideLayout sideTitle="角色列表">
    <template #sider>
      <MenuTree :search="true"
                :options="sysRoleList"
                :loading="loading"
                :tree="sysRoleList"
                :actions="actions"
                :replaceKeys="replaceFields"
                @tree-select="handleTreeSelect"
                @tree-node-action="handleAction"></MenuTree>
    </template>
    <template #content>
      <a-tabs v-model:activeKey="activeKey" @change="handleTabChange">
        <a-tab-pane key="edit" tab="角色信息">
          <FormList ref="roleForm"
                    title="角色信息编辑"
                    :fields="fields"
                    v-model="sysRole"
                    @submit="handleFormSubmit">
            <template #footer="{ handleClick }">
              <a-button @click="handleClick('submit')" type="primary" block :loading="loading">更新角色信息</a-button>
            </template>
          </FormList>
        </a-tab-pane>
        <a-tab-pane key="permission" tab="权限信息">
          <a-spin :spinning="loading" tip="加载中...">
            <RolePermission :sysRole="sysRole" :permission="permission"></RolePermission>
          </a-spin>
        </a-tab-pane>
        <!-- 扩展的tab元素 -->
        <template #tabBarExtraContent>
          <a-button type="primary" @click="handleAdd">
            <IconFont type="FileAddOutlined"/>新增
          </a-button>
        </template>
      </a-tabs>
    </template>
  </SideLayout>
</template>

<script>
/**
 * 角色管理
 */
import { SideLayout } from '@/layouts'
import MenuTree from '@/components/Common/MenuTree'
import { FormList } from '@/components/Common/FuncForm'
import { createVNode } from 'vue'
import { ExclamationCircleOutlined } from '@ant-design/icons-vue'
import RolePermission from './RolePermission'
// sysRole默认值
const defaultSysRole = { ifDefault: 0 }
export default {
  name: 'SysRole',
  components: { SideLayout, MenuTree, FormList, RolePermission },
  data () {
    return {
      // 角色替换字段
      replaceFields: this.$api.system.role.replaceFields,
      // 角色列表
      sysRoleList: [],
      // 当前操作的角色
      sysRole: defaultSysRole,
      // 表单的动作
      formAction: this.$default.ACTION.ADD,
      // 组织树的按钮
      actions: [
        { icon: 'ContactsOutlined', title: '赋权', name: 'permission' },
        this.$default.ACTION.DELETE
      ],
      // 当前激活的信息窗
      activeKey: 'edit',
      // 字段列表
      fields: [
        { type: 'text', label: '角色名称', name: 'roleName', rules: [{ required: true }] },
        { type: 'text', label: '角色描述', name: 'roleDesc' },
        { type: 'switch', label: '是否默认', name: 'ifDefault', default: 0, options: [{ value: 1, label: '是' }, { value: 0, label: '否' }] }
      ],
      // 角色权限
      permission: {},
      // 是否加载中
      loading: true
    }
  },
  computed: {
    // 表单的标题
    formTitle () {
      return `【${this.formAction.title}】系统组织信息`
    }
  },
  methods: {
    // 加载组织机构树
    loadSysRoleList () {
      this.loading = true
      this.$api.system.role.list().then(sysRoleList => {
        this.sysRoleList = sysRoleList
      }).finally(() => {
        this.loading = false
      })
    },
    // 加载角色权限信息
    loadRolePermission () {
      if (this.activeKey === 'permission' && this.$utils.isValid(this.sysRole.id)) {
        this.loading = true
        this.$api.system.role.permission(this.sysRole.id).then(permission => {
          this.permission = permission
        }).finally(() => { this.loading = false })
      } else {
        this.permission = {}
      }
    },
    // 响应树选中
    handleTreeSelect (nodeKey, treeNode) {
      this.sysRole = treeNode
      // 取消选中时，将permission重置为空
      if (!this.$utils.isValid(this.sysRole)) {
        this.permission = {}
      }
      // 如果当前切至赋权tab，选中角色，则加载角色权限信息
      this.loadRolePermission()
    },
    // 响应tabs切换
    handleTabChange () {
      // 如果当前切至赋权tab，选中角色，则加载角色权限信息
      this.loadRolePermission()
    },
    // 响应角色新增
    handleAdd () {
      this.formAction = this.$default.ACTION.ADD
      this.sysRole = defaultSysRole
      this.activeKey = 'edit'
    },
    // 响应菜单扩展操作
    handleAction (action, treeNode) {
      if (action === 'permission') {
        // 赋权
        this.formAction = this.$default.ACTION.EDIT
        this.sysRole = treeNode
        // 切换至赋权tab
        this.activeKey = 'permission'
        this.loadRolePermission()
      } else {
        this.sysRole = treeNode
        // 删除时弹出删除框
        const self = this
        this.$modal.$confirm({
          icon: createVNode(ExclamationCircleOutlined),
          okType: 'danger',
          content: `确定要删除角色【${treeNode.roleName}】吗？`,
          onOk () {
            self.$api.system.role.delete(self.sysRole.id).then(() => {
              self.sysRole = defaultSysRole
              // 提示删除成功
              self.$message.success('系统角色删除成功！')
              // 刷新数据
              self.loadSysRoleList()
            })
          }
        })
      }
    },
    // 响应数据编辑提交
    handleFormSubmit (sysRole) {
      this.loading = true
      this.$refs.roleForm.validate().then(() => {
        // 新增或者编辑的数据提交
        this.$api.system.role.update(sysRole).then(() => {
          this.sysRole = defaultSysRole
          this.$message.success('系统角色信息更新成功！')
          this.loadSysRoleList()
        }).finally(() => { this.loading = false })
      }).catch(() => { this.loading = false })
    }
  },
  created () {
    // 加载组织树数据
    this.loadSysRoleList()
  }
}
</script>
