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
          <a-list-item-meta title="开启页面水印"></a-list-item-meta>
          <template #actions>
            <a-switch size="small" v-model:checked="waterMarkEnable" @change="handleWaterMarkEnable"/>
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
      drawerVisible: false,
      themeOptions: [
        { icon: 'atom-theme-light', title: '纯白世界', value: 'light' },
        { icon: 'atom-theme-mix', title: '黑白无极', value: 'mix' },
        { icon: 'atom-theme-dark', title: '暗黑世界', value: 'dark' }
      ],
      navOptions: [
        { icon: 'atom-layout-side', title: '左右布局', value: 'sider' },
        { icon: 'atom-layout-top', title: '上下布局', value: 'top' },
        { icon: 'atom-layout-mix', title: '混合布局', value: 'mix' },
        { icon: 'atom-layout-drawer', title: '移动抽屉', value: 'drawer' }
      ],
      theme: this.$store.getters.theme,
      primaryColor: this.$store.getters.primaryColor,
      layout: this.$store.getters.layout,
      fixHeader: this.$store.getters.fixHeader,
      multiTab: this.$store.getters.multiTab,
      multiTabDraggable: this.$store.getters.multiTabDraggable,
      waterMarkEnable: this.$store.getters.waterMarkEnable,
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
    },
    // 响应Header是否固定
    handleFixToggle (fixHeader) {
      this.$store.dispatch('setFixHeader', fixHeader)
    },
    // 响应是否打开多标签
    handleMultiTabToggle (multiTab) {
      this.$store.dispatch('setMultiTab', multiTab)
    },
    // 响应多标签是否可拖拽
    handleMultiTabDraggable (multiTabDraggable) {
      this.$store.dispatch('setMultiTabDraggable', multiTabDraggable)
    },
    // 响应页面水印是否打开
    handleWaterMarkEnable (waterMarkEnable) {
      this.$store.dispatch('setWaterMarkEnable', waterMarkEnable)
    },
    // 响应动画效果的切换
    handleAnimateToggle () {
      // 调整默认方向
      this.transition.direction = this.directionOptions[0].value
      this.$store.dispatch('setTransition', this.transition)
    },
    // 响应动画方案的切换
    handleTransitionToggle () {
      this.$store.dispatch('setTransition', this.transition)
    }
  }
}
</script>
