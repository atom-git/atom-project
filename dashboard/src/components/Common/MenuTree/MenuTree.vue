<template>
  <a-card :class="['atom-menu-tree', device.type]" :bordered="false">
    <div class="atom-menu-search">
      <a-select v-if="search"
                class="atom-tree-select"
                showSearch
                placeholder="--请选择--"
                :allowClear="true"
                :filterOption="true"
                optionFilterProp="title"
                @change="handleSelectChange">
        <a-select-option v-for="option in options" :key="option[optionFields.value]" :title="option[optionFields.title]">
          {{ option[optionFields.title] }}
        </a-select-option>
      </a-select>
      <a-tooltip v-if="refresh" title="刷新">
        <a-button type="primary" shape="circle" class="atom-refresh" @click="handleRefresh">
          <template #icon><IconFont type="SyncOutlined" /></template>
        </a-button>
      </a-tooltip>
    </div>
    <div :style="treeStyle">
      <a-spin :spinning="loading" v-if="loading || tree">
        <!-- 这里用v-if的原因是让第一级能够自动展开 -->
        <a-tree v-if="tree"
                :treeData="tree"
                :replaceFields="replaceFields"
                :blockNode="true"
                :checkable="checkable"
                v-model:expandedKeys="expandedKeys"
                v-model:checkedKeys="checkedKeys"
                :selectedKeys="selectedKeys"
                :showLine="true"
                @select="handleTreeSelect"
                @check="handleTreeCheck">
          <template v-for="slot in slots" #[slot.name]>
            {{ slot.node[replaceFields.title] }}
            <a-badge v-if="slot.node[replaceFields.status] !== undefined && slot.node[replaceFields.status] !== null"
                     :key="slot.node[replaceFields.key]"
                     :status="slot.node.status"/>
            <a-dropdown v-if="actions" :key="slot.name" placement="bottomCenter">
              <IconFont type="MoreOutlined" class="atom-menu-tree-action"/>
              <template #overlay>
                <a-menu @click="handleAction($event, slot.node)">
                  <a-menu-item v-for="action in actions" :key="action.name">
                    <IconFont v-if="action.icon" :type="action.icon"/>{{ action.title }}
                  </a-menu-item>
                </a-menu>
              </template>
            </a-dropdown>
          </template>
        </a-tree>
      </a-spin>
      <a-empty v-else/>
    </div>
  </a-card>
</template>

<script>
/**
 * 多功能树结构
 * 修改tree为menu样式，并且抛出与tree一样的事件，同时在节点后方追加action
 * action上抛出事件 action.name,treeNode | tree-node-action
 */
import config from '@/config/mixins/config'
export default {
  name: 'MenuTree',
  mixins: [config],
  props: {
    // 树数据
    tree: {
      type: Array,
      required: true
    },
    // key值的字段名
    replaceKeys: {
      type: Object,
      required: false
    },
    // 是否支持多选
    checkable: {
      type: Boolean,
      default: false
    },
    // tree默认选择的节点
    defaultSelectedKeys: {
      type: Array,
      required: false
    },
    // tree默认选中的节点
    defaultCheckedKeys: {
      type: Array,
      required: false
    },
    /*
     * 每一行的操作按钮 [{icon, title, name}]
     * icon是图标
     * title是hover后显示的内容
     * name是emit的事件名称
     */
    actions: {
      type: Array,
      required: false
    },
    // 是否加载中的状态
    loading: {
      type: Boolean,
      default: false
    },
    // 是否显示搜索
    search: {
      type: Boolean,
      default: false
    },
    // 搜索的下拉菜单
    options: {
      type: Array,
      required: false
    },
    // 搜索下拉选项的key，可以用replaceKeys替代
    optionKeys: {
      type: Object,
      required: false
    },
    // 是否显示刷新按钮
    refresh: {
      type: Boolean,
      default: false
    },
    // node显示状态时的默认选项
    statusOptions: {
      type: Array,
      default: () => ([{ status: 'processing', value: 1 }, { status: 'warning', value: 0 }])
    }
  },
  data () {
    return {
      // 默认展开的节点
      expandedKeys: [],
      // 设置选择的keys
      selectedKeys: [],
      // 设置选中的keys
      checkedKeys: []
    }
  },
  computed: {
    // 默认的替换字段
    replaceFields () {
      return Object.assign({ key: 'key', title: 'title', children: 'children', status: 'status' }, this.replaceKeys)
    },
    // 下拉菜单替换字段
    optionFields () {
      return Object.assign({}, this.replaceFields, this.optionKeys)
    },
    // 用于对title重新挂载的slot数组
    slots () {
      const slots = []
      this.initActionNode(this.tree, slots)
      return slots
    },
    // 树的最大高度，PC端，平板端是总高度的80%，手机端是50%
    treeStyle () {
      const maxHeight = this.device.isMobile ? this.contentHeight * 0.6 : this.contentHeight * 0.9
      return { maxHeight: maxHeight + 'px', overflow: 'auto' }
    }
  },
  watch: {
    // 响应默认值的改变，immediate防止数据不更新
    defaultSelectedKeys: {
      immediate: true,
      deep: true,
      handler (newValue) {
        this.selectedKeys = [...(newValue || [])]
      }
    },
    // 响应默认值的改变，immediate防止数据不更新
    defaultCheckedKeys: {
      immediate: true,
      deep: true,
      handler (newValue) {
        this.checkedKeys = [...(newValue || [])]
      }
    },
    // 监听tree的值变化，设置展开
    tree: {
      deep: true,
      handler (newValue) {
        if (this.$utils.isValid(newValue)) {
          this.initExtendKeys(newValue)
        }
      }
    }
  },
  emits: ['tree-node-action', 'tree-search-select', 'tree-select', 'tree-check'],
  methods: {
    // 生成默认展示的第一个节点
    initExtendKeys (tree) {
      // 重新生成展开的第一个节点
      const treeNode = tree.find(node => node[this.replaceFields.children])
      // 如果expandedKeys不是空的时候，则选择第一个作为打开项
      this.expandedKeys = this.$utils.isValid(this.expandedKeys) ? this.expandedKeys : this.$utils.isValid(treeNode) ? [treeNode[[this.replaceFields.key]]] : []
    },
    // 生成action的节点
    initActionNode (tree, slots) {
      let slotName = ''
      tree.forEach(node => {
        slotName = 'title' + node[this.replaceFields.key]
        node.slots = { title: slotName }
        // 如果有状态信息字段，则写入状态信息，用于显示状态
        if (node[this.replaceFields.status] !== undefined && node[this.replaceFields.status] !== null) {
          node.status = this.statusOptions.filter(option => option.value === node[this.replaceFields.status])[0].status || 'default'
        }
        // 把挂载点写进数组用于生成挂载信息
        slots.push({ name: slotName, node: node })
        if (this.$utils.isValid(node[this.replaceFields.children])) {
          this.initActionNode(node[this.replaceFields.children], slots)
        }
      })
    },
    // 响应扩展菜单的点击
    handleAction ({ key }, treeNode) {
      // 设置当前操作的节点为选中节点
      this.selectedKeys = [treeNode[this.replaceFields.key]]
      this.$emit('tree-node-action', key, treeNode)
    },
    // 响应搜索框选择
    handleSelectChange (selectKey) {
      this.expandedKeys = []
      this.selectedKeys = [selectKey]
      this.$emit('tree-search-select', selectKey)
    },
    // 响应节点的选中
    handleTreeSelect (selectedKeys, { node }) {
      this.selectedKeys = selectedKeys
      // 如果当前非选中，则设置node为空对象
      let selectNode = {}
      if (selectedKeys.length > 0) {
        selectNode = node.dataRef
      }
      this.$emit('tree-select', this.selectedKeys, selectNode)
    },
    // 响应节点的check
    handleTreeCheck (checkedKeys, $event) {
      this.$emit('tree-check', checkedKeys, $event)
    },
    // 响应数据刷新，数据由外部进行控制
    handleRefresh () {
      this.$emit('refresh')
    }
  }
}
</script>

<style lang="less">
@import "menuTree";
</style>
