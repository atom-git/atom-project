<template>
  <SideLayout v-if="permissionGenerated" sideTitle="菜单列表">
    <template #sider>
      <a-spin :spinning="loading" tip="加载中...">
        <MenuTree :tree="menuTree"
                  :checkable="true"
                  :replaceKeys="replaceFields"
                  :defaultCheckedKeys="checkedMenus"
                  @tree-check="handleTreeCheck"></MenuTree>
      </a-spin>
    </template>
    <template #content>
      <a-spin v-if="sysActionTopicList.length > 0" :spinning="loading" tip="加载中...">
        <a-collapse v-model:activeKey="activeKey"
                    :accordion="true"
                    :bordered="false">
          <a-collapse-panel v-for="(sysActionTopic, index) in sysActionTopicList"
                            :key="index"
                            :header="sysActionTopic.name">
            <FormatTable :columns="actionColumns"
                         :dataSource="sysActionTopic['sysActionList']"
                         :pagination="false"
                         :rowSelection="initRowSelection(index)"></FormatTable>
          </a-collapse-panel>
        </a-collapse>
      </a-spin>
      <a-empty v-else description="无可选资源，需要菜单中配置"/>
    </template>
  </SideLayout>
  <a-empty v-else description="请选择角色"/>
</template>

<script>
import { SideLayout } from '@/layouts'
import MenuTree from '@/components/Common/MenuTree'
import { FormatTable } from '@/components/Common/FuncTable'
export default {
  name: 'RolePermission',
  components: { SideLayout, MenuTree, FormatTable },
  props: {
    // 当前选中的角色
    sysRole: {
      type: Object,
      required: true
    },
    // 角色权限
    permission: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      // 替换字段列表
      replaceFields: this.$api.system.menu.replaceFields,
      // action表单
      actionColumns: [
        { dataIndex: 'name', title: '资源名称' },
        { dataIndex: 'type', title: '请求类型', format: 'formatStatus', options: [{ value: 1, title: '查询', status: 'success' }, { value: 2, title: '新增', status: 'processing' }, { value: 3, title: '编辑', status: 'warning' }, { value: 4, title: '删除', status: 'error' }] },
        { dataIndex: 'grantType', title: '授权类型', format: 'formatStatus', options: [{ value: 0, title: '手动', status: 'warning' }, { value: 1, title: '自动', status: 'processing' }] }
      ],
      // 折叠面板的激活key，默认激活所有
      activeKey: [0],
      // 选中的权限
      sysPermission: {
        menusSet: [],
        actionSet: [],
      },
      // 请求loading
      loading: false
    }
  },
  watch: {
    // 监听外部传入变化，初始化值
    permission: {
      deep: true,
      handler (newValue) {
        this.initPermission(newValue)
      }
    }
  },
  computed: {
    // 系统菜单树
    menuTree () {
      return this.$utils.buildTree(this.permission.sysMenuList, 'id', 'parentId')
    },
    // 选中的菜单列表
    checkedMenus () {
      return this.permission.checkedMenus || []
    },
    // 角色对应的资源主题列表
    sysActionTopicList () {
      return this.permission.sysActionTopicList || []
    },
    // 信息已生成，用于控制器
    permissionGenerated () {
      return this.sysRole && this.sysRole.id && this.$utils.isValid(this.permission)
    }
  },
  methods: {
    // 初始化权限信息
    initPermission (permission) {
      // 初始化菜单选中结果
      this.sysPermission.menusSet = permission['checkedMenus']
      // 初始化资源选中结果
      this.sysPermission.actionSet = permission['checkedActions']
    },
    // 初始化表格的行选中效果
    initRowSelection (index) {
      const self = this
      return {
        selectedRowKeys: this.sysActionTopicList[index]['sysActionList'].filter(action => action.checked).map(action => action.id),
        onChange (selectedRowKeys) {
          self.sysActionTopicList[index]['sysActionList'].forEach(action => {
            // 原选中状态
            const originChecked = action.checked || false
            // 改变原有对象的值
            action.checked = selectedRowKeys && selectedRowKeys.length > 0 && selectedRowKeys.includes(action.id)
            if (originChecked && !action.checked) {
              // 原来选中，现在没选中，则删除
              self.$utils.arrayRemove(self.sysPermission.actionSet, self.sysPermission.actionSet.indexOf(action.id))
            } else if (!originChecked && action.checked) {
              // 原来没选中，现有选中，则加进来
              self.sysPermission.actionSet.push(action.id)
            }
          })
          // 更新数据
          self.handlePermissionUpdate()
        }
      }
    },
    // 响应树节点check
    handleTreeCheck (checkedKeys, $event) {
      this.sysPermission.menusSet = checkedKeys.concat($event.halfCheckedKeys)
      this.handlePermissionUpdate()
    },
    // 响应权限更新
    handlePermissionUpdate () {
      this.loading = true
      this.$api.system.role.updatePermission(this.sysRole.id, this.sysPermission).then(() => {
        this.$message.success('系统角色权限更新成功！')
      }).finally(() => { this.loading = false })
    }
  }
}
</script>
