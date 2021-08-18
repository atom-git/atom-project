 <template>
  <a-tooltip placement="bottom" title="主题设置">
    <span class="atom-header-button">
      <IconFont type="BgColorsOutlined" @click="drawerVisible = !drawerVisible"/>
    </span>
    <!-- 主题设置drawer -->
    <a-drawer v-model:visible="drawerVisible" width="300" wrapClassName="atom-theme-container">
      <a-divider class="atom-theme-divider">主题设置</a-divider>
      <div class="atom-theme-setting-item">
        <IconRadio name="theme"
                   :options="themeOptions"
                   v-model="theme"
                   @change="handleThemeToggle"></IconRadio>
      </div>
      <a-divider class="atom-theme-divider">主题色</a-divider>
      <div class="atom-theme-setting-item">
        <ColorPicker v-model="primaryColor" @change="handleThemeToggle"></ColorPicker>
      </div>
      <a-divider class="atom-theme-divider">布局模式</a-divider>
      <div class="atom-theme-setting-item">
        <IconRadio name="layout"
                   :options="navOptions"
                   v-model="layout"
                   size="small"
                   @change="handleLayoutSwitch"></IconRadio>
      </div>
      <a-divider class="atom-theme-divider">页面设置</a-divider>
      <a-list class="atom-theme-page-setting" :split="false">
        <a-list-item>
          <a-list-item-meta title="固定顶栏"></a-list-item-meta>
          <template #actions>
            <a-switch :disabled="layout === 'mix'" size="small" v-model:checked="fixHeader" @change="handleFixToggle"/>
          </template>
        </a-list-item>
        <a-list-item>
          <a-list-item-meta title="开启多页签"></a-list-item-meta>
          <template #actions>
            <a-switch size="small" v-model:checked="multiTab" @change="handleMultiTabToggle"/>
          </template>
        </a-list-item>
        <a-list-item>
          <a-list-item-meta title="开启页签拖拽"></a-list-item-meta>
          <template #actions>
            <a-switch size="small" v-model:checked="multiTabDraggable" @change="handleMultiTabDraggable"/>
          </template>
        </a-list-item>
        <a-list-item>
          <a-list-item-meta title="页面弹窗样式"></a-list-item-meta>
          <template #actions>
            <a-select size="small" v-model:value="dialog.type" :options="dialogOptions" @change="handleDialogChange"/>
          </template>
        </a-list-item>
        <a-list-item>
          <a-list-item-meta title="页面弹窗大小"></a-list-item-meta>
          <template #actions>
            <a-input-number size="small" step="10" v-model:value="dialog.size" @change="handleDialogChange"/>
          </template>
        </a-list-item>
        <a-list-item>
          <a-list-item-meta title="开启页面水印"></a-list-item-meta>
          <template #actions>
            <a-select size="small" v-model:value="transition.name" :options="animateOptions" @change="handleAnimateToggle"></a-select>
          </template>
        </a-list-item>
      </a-list>
      <a-divider class="atom-theme-divider">动画效果</a-divider>
      <a-list class="atom-theme-page-setting" :split="false">
        <a-list-item>
          <a-list-item-meta title="禁用动画"></a-list-item-meta>
          <template #actions>
            <a-switch size="small" v-model:checked="transition.disabled" @change="handleTransitionToggle"/>
          </template>
        </a-list-item>
        <a-list-item>
          <a-list-item-meta title="动画效果"></a-list-item-meta>
          <template #actions>
            <a-select size="small" v-model:value="transition.name" :options="animateOptions" @change="handleAnimateToggle"></a-select>
          </template>
        </a-list-item>
        <a-list-item>
          <a-list-item-meta title="动画方向"></a-list-item-meta>
          <template #actions>
            <a-select size="small" v-model:value="transition.direction" :options="directionOptions" @change="handleTransitionToggle"></a-select>
          </template>
        </a-list-item>
      </a-list>
    </a-drawer>
  </a-tooltip>
</template>

<script>
/**
 * 全局主题设置
 */
import { IconRadio } from '@/components/Common/FuncForm/Item'
import ColorPicker from '@/components/Common/ColorPicker'
import { toggleTheme } from '@/config/theme'
import { animateSet } from '@/components/Common/Animate/config'
export default {
  name: 'ThemeSetup',
  components: {
    IconRadio,
    ColorPicker
  },
  data () {
    return {
      // 主题设置drawer是否展开
      drawerVisible: false,
      // 主题选项
      themeOptions: [
        { icon: 'atom-theme-light', title: '纯白世界', value: 'light' },
        { icon: 'atom-theme-mix', title: '黑白无极', value: 'mix' },
        { icon: 'atom-theme-dark', title: '暗黑世界', value: 'dark' }
      ],
      // 布局选项
      navOptions: [
        { icon: 'atom-layout-side', title: '左右布局', value: 'sider' },
        { icon: 'atom-layout-top', title: '上下布局', value: 'top' },
        { icon: 'atom-layout-mix', title: '混合布局', value: 'mix' },
        { icon: 'atom-layout-drawer', title: '移动抽屉', value: 'drawer' }
      ],
      // 主题
      theme: this.$store.getters.theme,
      // 主题色
      primaryColor: this.$store.getters.primaryColor,
      // 布局
      layout: this.$store.getters.layout,
      // 是否固定头
      fixHeader: this.$store.getters.fixHeader,
      // 是否开启多标签
      multiTab: this.$store.getters.multiTab,
      // 多标签是否可移动
      multiTabDraggable: this.$store.getters.multiTabDraggable,
      // 弹窗
      dialog: this.$store.getters.dialog,
      // 弹窗样式
      dialogOptions: [
        { title: 'drawer', value: 'drawer' },
        { title: 'modal', value: 'modal' },
      ],
      // 水印
      waterMarkEnable: this.$store.getters.waterMarkEnable,
      // 切换动画
      transition: this.$store.getters.transition
    }
  },
  computed: {
    // 动画效果选项
    animateOptions () {
      return Object.values(animateSet).map(animate => { return { value: animate.name, label: animate.title } })
    },
    // 动画方向选项
    directionOptions () {
      return animateSet[this.transition.name].directionIns.map( direction => { return { value: direction } })
    }
  },
  methods: {
    // 响应主题切换
    handleThemeToggle ({ theme = this.theme, primaryColor = this.primaryColor }) {
      const loadding = this.$message.loading('正在切换主题！', 1)
      toggleTheme(theme, primaryColor).then(() => {
        loadding.then(
            () => { this.$message.success('主题切换成功！', 1) },
            () => { this.$message.error('主题切换失败！', 2) })
        // 保存主题到缓存
        this.$store.dispatch('setTheme', theme)
        this.$store.dispatch('setPrimaryColor', primaryColor)
      })
    },
    // 响应布局切换
    handleLayoutSwitch (layout) {
      // 保存布局到缓存
      this.$store.dispatch('setLayout', layout)
      this.$nextTick(() => {
        this.$message.success('布局切换成功！', 1)
      })
    },
    // 响应Header是否固定
    handleFixToggle (fixHeader) {
      this.$store.dispatch('setFixHeader', fixHeader)
      this.$nextTick(() => {
        this.$message.success(`页面头部已${fixHeader ? '锁定' : '解锁'}`, 1)
      })
    },
    // 响应弹窗改变
    handleDialogChange () {
      this.$store.dispatch('setDialog', this.dialog)
      this.$nextTick(() => {
        this.$message.success(`弹窗样式已设置为${this.dialog.type}宽度[${this.dialog.size}]`, 1)
      })
    },
    // 响应是否打开多标签
    handleMultiTabToggle (multiTab) {
      this.$store.dispatch('setMultiTab', multiTab)
      this.$nextTick(() => {
        this.$message.success(`多标签已${multiTab ? '开启' : '关闭'}`, 1)
      })
    },
    // 响应多标签是否可拖拽
    handleMultiTabDraggable (multiTabDraggable) {
      this.$store.dispatch('setMultiTabDraggable', multiTabDraggable)
      this.$nextTick(() => {
        this.$message.success(`多标签拖拽已${multiTabDraggable ? '开启' : '关闭'}`, 1)
      })
    },
    // 响应页面水印是否打开
    handleWaterMarkEnable (waterMarkEnable) {
      this.$store.dispatch('setWaterMarkEnable', waterMarkEnable)
      this.$nextTick(() => {
        this.$message.success('已经开启水印！', 1)
      })
    },
    // 响应动画效果的切换
    handleAnimateToggle () {
      // 调整默认方向
      this.transition.direction = this.directionOptions[0].value
      this.$store.dispatch('setTransition', this.transition)
      this.$nextTick(() => {
        this.$message.success('动画效果设置成功！', 1)
      })
    },
    // 响应动画方案的切换
    handleTransitionToggle () {
      this.$store.dispatch('setTransition', this.transition)
      this.$nextTick(() => {
        this.$message.success('动画效果设置成功！', 1)
      })
    }
  }
}
</script>
