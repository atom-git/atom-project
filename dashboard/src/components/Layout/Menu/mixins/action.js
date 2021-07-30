/**
 * 菜单的操作整体混入
 */
const defaultKeys = { title: 'title', key: 'id', icon: 'icon', children: 'children', disabled: 'disabled', hidden: 'hidden' }
const SubMenu = {
  name: 'SubMenu',
  props: {
    // 子菜单
    subMenu: {
      type: Object,
      default: () => ({}),
    },
    // submenu替代的key
    replaceFields: {
      type: Object,
      required: true
    }
  },
  template: `
    <a-sub-menu :key="subMenu[replaceFields.key]">
      <template #title>
        <IconFont :type="subMenu[replaceFields.icon]" />
        <span>{{ subMenu[replaceFields.title] }}</span>
      </template>
      <template v-for="(menu, index) in subMenu[replaceFields.children]">
        <template v-if="!$utils.isValid(menu[replaceFields.children])">
          <a-menu-item :key="menu[replaceFields.key]">
            <IconFont :type="menu[replaceFields.icon]" />
            <span>{{ menu[replaceFields.title] }}</span>
          </a-menu-item>
        </template>
        <template v-else>
          <SubMenu :subMenu="menu" :key="index" :replaceFields="replaceFields"/>
        </template>
      </template>
    </a-sub-menu>
  `
}
export default {
  name: 'SideMenu',
  props: {
    // 主题色
    theme: {
      type: String,
      default: 'dark'
    },
    // 菜单列表
    menus: {
      type: Array,
      required: true
    },
    // menu替代的key
    replaceKeys: {
      type: Object,
      default: () => (defaultKeys)
    }
  },
  components: {
    SubMenu
  },
  data () {
    return {
      // 展开的菜单
      openKeys: [],
      // 当前选中的菜单
      selectedKeys: [this.$route.name]
    }
  },
  computed: {
    // 实际替代字段
    replaceFields () {
      return Object.assign(defaultKeys, this.replaceKeys)
    }
  },
  methods: {
    // 响应菜单点击
    handleClick (menu) {
      // 点击非当前路由时跳转
      if (this.selectedKeys.indexOf(menu.key) < 0) {
        this.$router.push({ name: menu.key})
      }
      this.$emit('on-menu-click')
    }
  },
  watch: {
    // 对$route进行监听，发生变化时，设置新的选中菜单
    '$route' (newRoute) {
      this.selectedKeys = [newRoute.name]
    }
  }
}
