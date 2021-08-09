<template>
  <SideLayout v-if="sysRole && sysRole.id" sideTitle="菜单列表" :side="{}">
    <template #sider>
      <MenuTree :loading="loading"
                :tree="permissionTree"
                :checkable="true"
                :replaceKeys="replaceFields"
                :defaultCheckedKeys="checkedMenus"
                @tree-select="handleTreeSelect"
                @tree-check="handleTreeCheck"></MenuTree>
    </template>
    <template #content>
      <a-collapse v-if="activeKey.length > 0" v-model:activeKey="activeKey" :bordered="false">
        <a-collapse-panel v-for="(sysActionTopic, index) in sysActionTopicList"
                          :key="index"
                          :header="sysActionTopic.name">
          <FormatTable :columns="actionColumns"
                       :dataSource="sysActionTopic.sysActionList"
                       :rowSelection="actionRowSelection"></FormatTable>
        </a-collapse-panel>
      </a-collapse>
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
    // 赋权的角色
    sysRole: {
      type: Object,
      required: true
    },
    // 角色权限
    rolePermission: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      // 替换字段列表
      replaceFields: this.$api.system.menu.replaceFields,
      // 当前选中的菜单对应的动作主题列表
      sysActionTopicList: [],
      // action表单
      actionColumns: [
        { dataIndex: 'name', title: '资源名称' },
        { dataIndex: 'type', title: '请求类型', format: 'formatStatus', options: [{ value: 1, title: '查询', status: 'success' }, { value: 2, title: '新增', status: 'processing' }, { value: 3, title: '编辑', status: 'warning' }, { value: 4, title: '删除', status: 'error' }] },
        { dataIndex: 'grantType', title: '授权类型', format: 'formatStatus', options: [{ value: 0, title: '手动', status: 'warning' }, { value: 1, title: '自动', status: 'processing' }] }
      ],
      // 折叠面板的激活key，默认激活所有
      activeKey: [],
      // 请求loading
      loading: false
    }
  },
  computed: {
    // 选中的菜单列表
    checkedMenus () {
      return this.rolePermission.checkedMenus
    },
    // 系统菜单资源列表
    sysMenuActionList () {
      return this.rolePermission.sysMenuActionList
    },
    // 权限树
    permissionTree () {
      return this.$utils.buildTree(this.sysMenuActionList, 'id', 'parentId')
    },
    // 当前选中的是哪个资源卡
    activeActionList () {
      return []
    },
    // action表单选中操作
    actionRowSelection () {
      const self = this
      return {
        selectedRowKeys: this.activeActionList.filter(action => action.checked).map(action => action.id),
        onChange (selectedRowKeys) {
          self.activeActionList.forEach(action => {
            // 改变原有对象的值
            action.checked = selectedRowKeys && selectedRowKeys.length > 0 && selectedRowKeys.indexOf(action.id) > -1
          })
        }
      }
    }
  },
  methods: {
    // 响应树选中
    handleTreeSelect (nodeKey, treeNode) {
      // 在右侧分主题展示该菜单下的能够选择的动作资源权限
      this.sysActionTopicList = treeNode.sysActionTopicVOList || []
      this.activeKey = this.sysActionTopicList.map((item, index) => { return index })
    },
    // 响应树节点check
    handleTreeCheck (checkedKeys, $event) {
      console.log(checkedKeys, $event)
    }
  }
}
</script>
