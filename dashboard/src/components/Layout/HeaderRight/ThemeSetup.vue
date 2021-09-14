 <template>
  <a-tooltip placement="bottom" :title="$t('Layout.ThemeSetup.theme')">
    <span class="atom-header-button">
      <IconFont type="BgColorsOutlined" @click="drawerVisible = !drawerVisible"/>
    </span>
    <!-- 主题设置drawer -->
    <a-drawer v-model:visible="drawerVisible" width="300" wrapClassName="atom-theme-container">
      <a-divider class="atom-theme-divider">{{ $t('Layout.ThemeSetup.theme') }}</a-divider>
      <div class="atom-theme-setting-item">
        <IconRadio name="theme"
                   :options="themeOptions"
                   v-model="appConfig.theme"
                   @change="handleThemeToggle"></IconRadio>
      </div>
      <a-divider class="atom-theme-divider">{{ $t('Layout.ThemeSetup.primaryColor') }}</a-divider>
      <div class="atom-theme-setting-item">
        <ColorPicker v-model="appConfig.primaryColor" @change="handleThemeToggle"></ColorPicker>
      </div>
      <a-divider class="atom-theme-divider">{{ $t('Layout.ThemeSetup.layout.title') }}</a-divider>
      <div class="atom-theme-setting-item">
        <IconRadio name="layout"
                   :options="navOptions"
                   v-model="appConfig.layout"
                   size="small"
                   @change="handleLayoutSwitch"></IconRadio>
      </div>
      <a-divider class="atom-theme-divider">{{ $t('Layout.ThemeSetup.layout.page') }}</a-divider>
      <a-list class="atom-theme-page-setting" :split="false">
        <a-list-item>
          <a-list-item-meta :title="$t('Layout.ThemeSetup.layout.header')"></a-list-item-meta>
          <template #actions>
            <a-switch :disabled="appConfig.layout === 'mix'" size="small" v-model:checked="appConfig.fixHeader" @change="handleFixToggle"/>
          </template>
        </a-list-item>
        <a-list-item>
          <a-list-item-meta :title="$t('Layout.ThemeSetup.layout.multiTab')"></a-list-item-meta>
          <template #actions>
            <a-switch size="small" v-model:checked="appConfig.multiTab" @change="handleMultiTabToggle"/>
          </template>
        </a-list-item>
        <a-list-item>
          <a-list-item-meta :title="$t('Layout.ThemeSetup.layout.multiTabDraggable')"></a-list-item-meta>
          <template #actions>
            <a-switch size="small" v-model:checked="appConfig.multiTabDraggable" @change="handleMultiTabDraggable"/>
          </template>
        </a-list-item>
        <a-list-item>
          <a-list-item-meta :title="$t('Layout.ThemeSetup.layout.dialog.type')"></a-list-item-meta>
          <template #actions>
            <a-select size="small" v-model:value="appConfig.dialog.type" :options="dialogOptions" @change="handleDialogChange"/>
          </template>
        </a-list-item>
        <a-list-item>
          <a-list-item-meta :title="$t('Layout.ThemeSetup.layout.dialog.size')"></a-list-item-meta>
          <template #actions>
            <a-input-number size="small" step="10" v-model:value="appConfig.dialog.size" @blur="handleDialogChange"/>
          </template>
        </a-list-item>
        <a-list-item>
          <a-list-item-meta :title="$t('Layout.ThemeSetup.layout.waterMark')"></a-list-item-meta>
          <template #actions>
            <a-switch size="small" v-model:checked="appConfig.waterMark" @change="handleWaterMarkToggle"/>
          </template>
        </a-list-item>
      </a-list>
      <a-divider class="atom-theme-divider">{{ $t('Layout.ThemeSetup.transition.title') }}</a-divider>
      <a-list class="atom-theme-page-setting" :split="false">
        <a-list-item>
          <a-list-item-meta :title="$t('Layout.ThemeSetup.transition.disabled')"></a-list-item-meta>
          <template #actions>
            <a-switch size="small" v-model:checked="appConfig.transition.disabled" @change="handleTransitionToggle"/>
          </template>
        </a-list-item>
        <a-list-item>
          <a-list-item-meta :title="$t('Layout.ThemeSetup.transition.name')"></a-list-item-meta>
          <template #actions>
            <a-select size="small" v-model:value="appConfig.transition.name" :options="animateOptions" @change="handleAnimateToggle"></a-select>
          </template>
        </a-list-item>
        <a-list-item>
          <a-list-item-meta :title="$t('Layout.ThemeSetup.transition.direction')"></a-list-item-meta>
          <template #actions>
            <a-select size="small" v-model:value="appConfig.transition.direction" :options="directionOptions" @change="handleTransitionToggle"></a-select>
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
      // 弹窗样式
      dialogOptions: [
        { title: 'drawer', value: 'drawer' },
        { title: 'modal', value: 'modal' },
      ],
      // App应用配置
      appConfig: this.$store.getters.appConfig
    }
  },
  computed: {
    // 主题选项
    themeOptions () {
      return [
        { icon: 'atom-theme-light', title: this.$t('Layout.ThemeMode.light'), value: 'light' },
        { icon: 'atom-theme-mix', title: this.$t('Layout.ThemeMode.mix'), value: 'mix' },
        { icon: 'atom-theme-dark', title: this.$t('Layout.ThemeMode.dark'), value: 'dark' }
      ]
    },
    // 布局选项
    navOptions () {
      return [
        { icon: 'atom-layout-side', title: this.$t('Layout.Mode.side'), value: 'sider' },
        { icon: 'atom-layout-top', title: this.$t('Layout.Mode.top'), value: 'top' },
        { icon: 'atom-layout-mix', title: this.$t('Layout.Mode.mix'), value: 'mix' },
        { icon: 'atom-layout-drawer', title: this.$t('Layout.Mode.drawer'), value: 'drawer' }
      ]
    },
    // 动画效果选项
    animateOptions () {
      return Object.values(animateSet).map(animate => { return { value: animate.name, label: animate.title } })
    },
    // 动画方向选项
    directionOptions () {
      return animateSet[this.appConfig.transition.name].directionIns.map( direction => { return { value: direction } })
    }
  },
  methods: {
    // 响应主题切换
    handleThemeToggle ({ theme = this.appConfig.theme, primaryColor = this.appConfig.primaryColor }) {
      const loadding = this.$message.loading(this.$t('message.theme.loading'), 1)
      toggleTheme(theme, primaryColor).then(() => {
        this.toggleUserAppConfig(() => {
          loadding.then(
              () => { this.$message.success(this.$t('message.theme.success'), 1) },
              () => { this.$message.error(this.$t('message.theme.error'), 2) })
        })
        // 保存主题到缓存
        this.$store.dispatch('setTheme', theme)
        this.$store.dispatch('setPrimaryColor', primaryColor)
      })

    },
    // 响应布局切换
    handleLayoutSwitch (layout) {
      // 保存布局到缓存
      this.$store.dispatch('setLayout', layout)
      this.toggleUserAppConfig(() => {})
    },
    // 响应Header是否固定
    handleFixToggle (fixHeader) {
      this.$store.dispatch('setFixHeader', fixHeader)
      this.toggleUserAppConfig(() => {
        this.$nextTick(() => {
          this.$message.success(`${this.$t('message.header.message')}${fixHeader ? this.$t('message.header.fixed') : this.$t('message.header.unfixed')}`, 1)
        })
      })
    },
    // 响应弹窗改变
    handleDialogChange () {
      // modal的最小宽度设置为720
      if (this.appConfig.dialog.type === 'modal') {
        this.appConfig.dialog.size = this.appConfig.dialog.size < 720 ? 720 : this.appConfig.dialog.size
      }
      this.$store.dispatch('setDialog', this.appConfig.dialog)
      this.toggleUserAppConfig(() => {
        this.$nextTick(() => {
          this.$message.success(this.$t('message.dialog', { type: this.appConfig.type, size: this.appConfig.size }), 1)
        })
      })
    },
    // 响应是否打开多标签
    handleMultiTabToggle (multiTab) {
      this.$store.dispatch('setMultiTab', multiTab)
      this.toggleUserAppConfig(() => {
        this.$nextTick(() => {
          this.$message.success(`${this.$t('message.multiTab.message')}${multiTab ? this.$t('message.multiTab.open') : this.$t('message.multiTab.close')}`, 1)
        })
      })
    },
    // 响应多标签是否可拖拽
    handleMultiTabDraggable (multiTabDraggable) {
      this.$store.dispatch('setMultiTabDraggable', multiTabDraggable)
      this.toggleUserAppConfig(() => {
        this.$nextTick(() => {
          this.$message.success(`${this.$t('message.multiTabDraggable.message')}${multiTabDraggable ? this.$t('message.multiTabDraggable.open') : this.$t('message.multiTabDraggable.close')}`, 1)
        })
      })
    },
    // 响应页面水印是否打开
    handleWaterMarkToggle (waterMark) {
      this.$store.dispatch('setWaterMark', waterMark)
      this.toggleUserAppConfig(() => {
        this.$nextTick(() => {
          this.$message.success(`${this.$t('message.waterMark.message')}${waterMark ? this.$t('message.waterMark.open') : this.$t('message.waterMark.close')}`, 1)
        })
      })
    },
    // 响应动画效果的切换
    handleAnimateToggle () {
      // 调整默认方向
      this.appConfig.transition.direction = this.directionOptions[0].value
      this.$store.dispatch('setTransition', this.appConfig.transition)
      this.toggleUserAppConfig(() => {
        this.$nextTick(() => {
          this.$message.success(this.$t('message.transition'), 1)
        })
      })
    },
    // 响应动画方案的切换
    handleTransitionToggle () {
      this.$store.dispatch('setTransition', this.appConfig.transition)
      this.toggleUserAppConfig(() => {
        this.$nextTick(() => {
          this.$message.success(this.$t('message.transition'), 1)
        })
      })
    },
    // 用户应用配置保存
    toggleUserAppConfig (callback) {
      this.$api.system.user.updateAppConfig(this.appConfig).then(() => {
        callback()
        this.$store.dispatch('setAppConfig', this.appConfig)
      })
    }
  }
}
</script>
