<template>
  <SideLayout sideTitle="系统菜单">
    <template #sider>
      <MenuTree :search="true"
                :options="sysMenuList"
                :loading="loading"
                :tree="sysMenuTree"
                :actions="actions"
                :replaceKeys="replaceFields"
                @tree-select="handleTreeSelect"
                @tree-node-action="handleAction"></MenuTree>
    </template>
    <template #content>
      <FormList ref="menuForm"
                :title="formTitle"
                :fields="fields"
                v-model="sysMenu"
                @submit="handleFormSubmit">
        <template #extra>
          <a-button type="primary" @click="handleAdd">
            <IconFont type="FileAddOutlined"/>新增
          </a-button>
        </template>
        <template #footer="{ handleClick }">
          <a-button @click="handleClick('submit')" type="primary" block :loading="loading">更新菜单信息</a-button>
        </template>
      </FormList>
    </template>
  </SideLayout>
</template>

<script>
/**
 * 菜单管理
 */
import { SideLayout } from '@/layouts'
import MenuTree from '@/components/Common/MenuTree'
import { FormList } from '@/components/Common/FuncForm'
import { createVNode } from 'vue'
import { ExclamationCircleOutlined } from '@ant-design/icons-vue'
// sysMenu默认值
const defaultSysMenu = { hidden: 0, ifValid: 1 }
export default {
  name: 'SysMenu',
  components: { SideLayout, MenuTree, FormList },
  data () {
    return {
      // 替代字段
      replaceFields: this.$api.system.menu.replaceFields,
      // 系统菜单列表
      sysMenuList: [],
      // 系统菜单树
      sysMenuTree: [],
      // 资源主题列表
      actionTopicList: [],
      // 当前菜单对象
      sysMenu: defaultSysMenu,
      // 表单的动作
      formAction: this.$default.ACTION_ADD,
      // 菜单树的按钮
      actions: [
        this.$default.ACTION_ADD,
        this.$default.ACTION_DELETE,
        this.$default.ACTION_MOVE_UP,
        this.$default.ACTION_MOVE_DOWN
      ],
      // 请求loading
      loading: false
    }
  },
  computed: {
    // 表单的fields
    fields () {
      return [
        { type: 'text', label: '菜单名称', name: 'name', rules: [{ required: true }] },
        { type: 'text', label: '菜单描述', name: 'desc' },
        { type: 'iconPicker', label: '菜单图标', name: 'icon' },
        { type: 'text', label: '路由名称', name: 'route', rules: [{ required: true }] },
        { type: 'treeSelect', label: '父级菜单', name: 'parentId', treeData: this.sysMenuTree, replaceFields: this.replaceFields },
        { type: 'number', label: '优先级', name: 'menuOrder', rules: [{ required: true, type: 'integer' }], extra: '新增时默认按照子集最大序号生成' },
        { type: 'select', label: '数据主题', name: 'topicList', rules: [{ required: false, type: 'any' }], mode: 'multiple', options: this.actionTopicList, replaceFields: this.$api.system.action.replaceFields },
        { type: 'switch', label: '是否隐藏', name: 'hidden', options: [{ title: '否', value: 0 }, { title: '是', value: 1 }], rules: [{ type: 'integer', required: true }] },
        { type: 'radio', label: '状态', name: 'ifValid', options: [{ title: '禁用', value: 0 }, { title: '启用', value: 1 }], rules: [{ type: 'integer', required: true }] }
      ]
    },
    // 表单的标题
    formTitle () {
      return `【${this.formAction.title}】系统菜单信息`
    }
  },
  methods: {
    // 加载菜单机构树
    loadSysMenuTree () {
      this.loading = true
      this.$api.system.menu.tree().then(sysMenuList => {
        this.sysMenuList = sysMenuList
        // 转成树结构
        this.sysMenuTree = this.$utils.buildTree(sysMenuList, 'id', 'parentId')
      }).finally(() => {
        this.loading = false
      })
    },
    // 加载动作主题列表
    loadActionTopicList () {
      this.$api.system.action.topicList().then(topicList => {
        this.actionTopicList = topicList
      })
    },
    // 响应树节点的选择
    handleTreeSelect (nodeKey, treeNode) {
      this.formAction = this.$default.ACTION_EDIT
      this.sysMenu = treeNode
    },
    // 响应角色新增
    handleAdd () {
      this.formAction = this.$default.ACTION_ADD
      this.sysMenu = defaultSysMenu
    },
    // 响应菜单扩展操作
    handleAction (action, treeNode) {
      if (action === this.$default.ACTION_ADD.name) {
        // 新增时，重置右侧表单
        this.formAction = this.$default.ACTION_ADD.name
        // 根据当前菜单的子集的order最大值，设置order默认值
        const children = treeNode.children
        let menuOrder = 1
        if (this.$utils.isValid(children)) {
          const orderArray = this.$utils.simpleList(children, 'menuOrder')
          orderArray.sort(this.$utils.sortNumDesc)
          menuOrder = orderArray[0] + 1
        }
        this.sysMenu = { parentId: treeNode.id, menuOrder: menuOrder, hidden: 0, ifValid: 1 }
      } else if (action === this.$default.ACTION_DELETE.name) {
        this.sysMenu = treeNode
        // 删除时弹出删除框
        const self = this
        this.$modal.$confirm({
          icon: createVNode(ExclamationCircleOutlined),
          okType: 'danger',
          content: `确定要删除菜单【${treeNode.name}】吗？`,
          onOk () {
            self.$api.system.menu.delete(self.sysMenu.id).then(() => {
              self.sysMenu = defaultSysMenu
              // 提示删除成功
              self.$message.success('系统菜单删除成功！')
              // 刷新数据
              self.loadSysDeptTree()
            })
          }
        })
      } else if (action === this.$default.ACTION_MOVE_UP.name || action === this.$default.ACTION_MOVE_DOWN.name) {
        this.loading = true
        this.$api.system.menu.exchange(action, treeNode).then(sysMenu => {
          this.sysMenu = sysMenu
          this.$message.success('系统菜单顺序更新成功！')
          this.loadSysMenuTree()
        }).finally(() => {
          this.loading = false
        })
      }
    },
    // 响应数据编辑提交
    handleFormSubmit (sysMenu) {
      this.loading = true
      this.$refs.menuForm.validate().then(() => {
        // 新增或者编辑的数据提交
        this.$api.system.menu.update(sysMenu).then(() => {
          this.$message.success('系统菜单信息更新成功！')
          this.loadSysMenuTree()
        }).finally(() => { this.loading = false })
      }).catch(() => { this.loading = false })
    }
  },
  created () {
    // 加载菜单树数据
    this.loadSysMenuTree()
    // 加载资源主题数据
    this.loadActionTopicList()
  }
}
</script>
