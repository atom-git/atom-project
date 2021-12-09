<template>
  <SideLayout sideTitle="组织架构">
    <template #sider>
      <MenuTree :search="true"
                :options="sysDeptList"
                :loading="loading"
                :tree="sysDeptTree"
                :replaceKeys="replaceFields"
                :customizeTitle="false"
                @tree-select="handleTreeSelect"></MenuTree>
    </template>
    <template #content>
      <FuncTable :apiUrl="apiUrl"
                 :columns="columns"
                 :funcZone="funcZone"
                 :extendParams="extendParams"
                 :labelCol="{ style: 'width: 60px' }"
                 @table-row-action="handleRowAction"></FuncTable>
    </template>
  </SideLayout>
  <!-- 赋权弹窗 -->
  <Dialog v-model="visible"
          title="用户赋权角色"
          :loading="loading"
          :maskClosable="false"
          @ok="handleDialogSubmit">
    <FormList ref="roleForm"
              v-model="userRole"
              :fields="roleFields"
              :hiddenFooter="true"
              :labelCol="{ xs: 24, sm: 8, md: 6, xl: 6, xxl: 5 }"></FormList>
  </Dialog>
</template>

<script>
/**
 * 用户管理
 */
import { SideLayout } from '@/layouts'
import MenuTree from '@/components/Common/MenuTree'
import { FuncTable } from '@/components/Advance/FuncTable'
import { ExclamationCircleOutlined } from '@ant-design/icons-vue'
import { createVNode } from 'vue'
import Dialog from '@/components/Common/Dialog'
import { FormList } from '@/components/Advance/FuncForm'
export default {
  name: 'SysUser',
  components: { SideLayout, MenuTree, FuncTable, Dialog, FormList },
  data () {
    return {
      // 组织架构列表
      sysDeptList: [],
      // 组织架构树
      sysDeptTree: [],
      // 替代字段
      replaceFields: this.$api.system.dept.replaceFields,
      // 当前组织对象
      sysDept: {},
      // 用户请求url
      apiUrl: '/system/user/list',
      // 用户功能按钮区域
      funcZone: {
        add: { apiUrl: '/system/user/update' },
        download: true,
        refresh: true,
        setting: true
      },
      // 系统角色列表
      sysRoleList: [],
      // 用户角色
      userRole: {},
      // 弹窗是否显示
      visible: false,
      // 请求loading
      loading: false
    }
  },
  computed: {
    // 表格列
    columns () {
      return [
        {
          title: '基本信息',
          children: [
            {
              dataIndex: 'head',
              title: '头像',
              format: 'formatAvatar'
            },
            {
              type: 'input',
              dataIndex: 'account',
              title: '帐号',
              form: { filter: true, add: true, edit: true, rules: [{ required: true }] }
            },
            {
              type: 'input',
              dataIndex: 'phone',
              title: '手机',
              form: { filter: true, add: true, edit: true, rules: [{ required: true, type: 'mobile' }] }
            },
            {
              type: 'input',
              dataIndex: 'email',
              title: '邮箱',
              form: { filter: true, add: true, edit: true, rules: [{ required: true, type: 'email' }] }
            },
            {
              type: 'input',
              dataIndex: 'name',
              title: '名称',
              form: { filter: true, add: true, edit: true, rules: [{ required: true }] }
            }
          ]
        },
        {
          type: 'treeSelect',
          dataIndex: 'deptId',
          title: '部门',
          format: 'formatObject|sysDept.deptName',
          form: { add: true, edit: true, rules: [{ required: true, type: 'any' }], treeData: this.sysDeptTree, replaceFields: this.$api.system.dept.replaceFields }
        },
        {
          dataIndex: 'createTime',
          title: '创建日期',
          format: 'formatDate|YYYY-MM-DD HH:mm:ss',
          hidden: true
        },
        {
          type: 'radio',
          dataIndex: 'ifValid',
          title: '状态',
          format: 'formatSwitch',
          options: [{ value: 1, title: '有效', status: 'success' }, { value: 0, title: '删除', status: 'error' }],
          form: { filter: true, edit: true, rules: [{ required: true, type: 'any' }] }
        },
        {
          dataIndex: 'action',
          title: '操作',
          format: 'formatAction|text',
          actions: [
            { title: '编辑', name: 'edit', apiUrl: '/system/user/update' },
            { title: '重置', name: 'reset' },
            { title: '赋权', name: 'permission', apiUrl: '/system/user/{s}' }
          ]
        }
      ]
    },
    // 赋权动作的字段
    roleFields () {
      return [
        {
          type: 'select',
          label: '用户角色',
          name: 'userRoleList',
          rules: [{ required: true }],
          options: this.sysRoleList,
          mode: 'multiple',
          showSearch: true,
          replaceFields: this.$utils.deepClone(this.$api.system.role.replaceFields)
        }
      ]
    },
    // 用户筛选的扩展参数
    extendParams () {
      return { deptId: this.sysDept.id }
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
      this.sysDept = treeNode
    },
    // 响应行级操作按钮
    handleRowAction (action, row) {
      this.sysUser = row
      const self = this
      if (action.name === 'reset') {
        this.$modal.$confirm({
          content: `确认要重置用户【${row.name}】的密码吗？`,
          icon: createVNode(ExclamationCircleOutlined),
          confirmLoading: self.loading,
          onOk () {
            self.loading = true
            self.$api.system.user.resetPassword(row.id).then(password => {
              self.$message.success(`用户${row.name}密码重置为${password}成功！`)
            }).finally(() => { self.loading = false })
          }
        })
      } else if (action.name === 'permission') {
        // 查询角色列表
        this.$api.system.user.roleList(this.sysUser.id).then(sysUserRole => {
          this.sysRoleList = sysUserRole.sysRoleList
          this.userRole.userRoleList = sysUserRole.userRoleList
          this.visible = true
        })
      } else if (action.name === 'ifValid') {
        this.$modal.$confirm({
          content: `确认要${row.ifValid ? '禁用' : '启用'}用户【${row.name}】吗？`,
          icon: createVNode(ExclamationCircleOutlined),
          confirmLoading: self.loading,
          onOk () {
            self.loading = true
            row.ifValid = row.ifValid ? 0 : 1
            self.$api.system.user.update(row).then(() => {
              self.$message.success(`用户【${row.name}】已被${row.ifValid ? '启用' : '禁用'}！`)
            }).finally(() => { self.loading = false })
          }
        })
      }
    },
    // 响应弹窗表单的提交
    handleDialogSubmit () {
      this.loading = true
      this.$refs.roleForm.validate().then(() => {
        this.$api.system.user.updateRole(this.sysUser.id, this.userRole.userRoleList).then(() => {
          this.$message.success(`用户【${this.sysUser.name}】赋权角色成功！`)
        }).finally(() => {
          this.loading = false
          this.visible = false
        })
      }).catch(() => { this.loading = false })
    }
  },
  created () {
    // 加载组织树数据
    this.loadSysDeptTree()
  }
}
</script>
