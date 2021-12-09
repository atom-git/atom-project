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
                   v-model="appConfig.theme"
                   @change="handleThemeToggle"></IconRadio>
      </div>
      <a-divider class="atom-theme-divider">主题色</a-divider>
      <div class="atom-theme-setting-item">
        <ColorPicker v-model="appConfig.primaryColor" pickType="compact" @change="handleThemeToggle"></ColorPicker>
      </div>
      <a-divider class="atom-theme-divider">布局模式</a-divider>
      <div class="atom-theme-setting-item">
        <IconRadio name="layout"
                   :options="navOptions"
                   v-model="appConfig.layout"
                   size="small"
                   @change="handleLayoutSwitch"></IconRadio>
      </div>
      <a-divider class="atom-theme-divider">页面设置</a-divider>
      <a-list class="atom-theme-page-setting" :split="false">
        <a-list-item>
          <a-list-item-meta title="固定顶栏"></a-list-item-meta>
          <template #actions>
            <a-switch :disabled="appConfig.layout === 'mix'" size="small" v-model:checked="appConfig.fixHeader" @change="handleFixToggle"/>
          </template>
        </a-list-item>
        <a-list-item>
          <a-list-item-meta title="开启多页签"></a-list-item-meta>
          <template #actions>
            <a-switch :disabled="appConfig.layout === 'drawer'" size="small" v-model:checked="appConfig.multiTab" @change="handleMultiTabToggle"/>
          </template>
        </a-list-item>
        <a-list-item>
          <a-list-item-meta title="开启页签拖拽"></a-list-item-meta>
          <template #actions>
            <a-switch size="small" v-model:checked="appConfig.multiTabDraggable" @change="handleMultiTabDraggable"/>
          </template>
        </a-list-item>
        <a-list-item>
          <a-list-item-meta title="页面弹窗样式"></a-list-item-meta>
          <template #actions>
            <a-select :disabled="appConfig.layout === 'drawer'"
                      size="small"
                      v-model:value="appConfig.dialog.type"
                      :options="dialogOptions"
                      @change="handleDialogChange"/>
          </template>
        </a-list-item>
        <a-list-item>
          <a-list-item-meta title="页面弹窗大小"></a-list-item-meta>
          <template #actions>
            <a-input-number :disabled="appConfig.layout === 'drawer'"
                            size="small"
                            step="10"
                            v-model:value="appConfig.dialog.size"
                            @blur="handleDialogChange"/>
          </template>
        </a-list-item>
        <a-list-item>
          <a-list-item-meta title="开启页面水印"></a-list-item-meta>
          <template #actions>
            <a-switch size="small" v-model:checked="appConfig.waterMark" @change="handleWaterMarkToggle"/>
          </template>
        </a-list-item>
      </a-list>
      <a-divider class="atom-theme-divider">动画效果</a-divider>
      <a-list class="atom-theme-page-setting" :split="false">
        <a-list-item>
          <a-list-item-meta title="禁用动画"></a-list-item-meta>
          <template #actions>
            <a-switch size="small" v-model:checked="appConfig.transition.disabled" @change="handleTransitionToggle"/>
          </template>
        </a-list-item>
        <a-list-item>
          <a-list-item-meta title="动画效果"></a-list-item-meta>
          <template #actions>
            <a-select size="small" v-model:value="appConfig.transition.name" :options="animateOptions" @change="handleAnimateToggle"></a-select>
          </template>
        </a-list-item>
        <a-list-item>
          <a-list-item-meta title="动画方向"></a-list-item-meta>
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
import { ColorPicker, IconRadio } from '@/components/Common/FormItem'
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
        { icon: 'atom-theme-color', title: '黑白无极', value: 'color' },
        { icon: 'atom-theme-dark', title: '暗黑世界', value: 'dark' }
      ],
      // 布局选项
      navOptions: [
        { icon: 'atom-layout-side', title: '左右布局', value: 'sider' },
        { icon: 'atom-layout-top', title: '上下布局', value: 'top' },
        { icon: 'atom-layout-mix', title: '混合布局', value: 'mix' },
        { icon: 'atom-layout-drawer', title: '移动抽屉', value: 'drawer' }
      ],
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
      const loadding = this.$message.loading('正在切换主题！', 1)
      toggleTheme(theme, primaryColor).then(() => {
        this.toggleUserAppConfig(() => {
          loadding.then(
              () => { this.$message.success('主题切换成功！', 1) },
              () => { this.$message.error('主题切换失败！', 2) })
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
      this.toggleUserAppConfig(() => {
        this.$nextTick(() => {
          this.$message.success('布局切换成功！', 1)
        })
      })
    },
    // 响应Header是否固定
    handleFixToggle (fixHeader) {
      this.$store.dispatch('setFixHeader', fixHeader)
      this.toggleUserAppConfig(() => {
        this.$nextTick(() => {
          this.$message.success(`页面头部已${fixHeader ? '锁定' : '解锁'}`, 1)
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
          this.$message.success(`弹窗样式已设置为${this.appConfig.dialog.type}宽度[${this.appConfig.dialog.size}]`, 1)
        })
      })
    },
    // 响应是否打开多标签
    handleMultiTabToggle (multiTab) {
      this.$store.dispatch('setMultiTab', multiTab)
      this.toggleUserAppConfig(() => {
        this.$nextTick(() => {
          this.$message.success(`多标签已${multiTab ? '开启' : '关闭'}`, 1)
        })
      })
    },
    // 响应多标签是否可拖拽
    handleMultiTabDraggable (multiTabDraggable) {
      this.$store.dispatch('setMultiTabDraggable', multiTabDraggable)
      this.toggleUserAppConfig(() => {
        this.$nextTick(() => {
          this.$message.success(`多标签拖拽已${multiTabDraggable ? '开启' : '关闭'}`, 1)
        })
      })
    },
    // 响应页面水印是否打开
    handleWaterMarkToggle (waterMark) {
      this.$store.dispatch('setWaterMark', waterMark)
      this.toggleUserAppConfig(() => {
        this.$nextTick(() => {
          this.$message.success('已经开启水印！', 1)
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
          this.$message.success('动画效果设置成功！', 1)
        })
      })
    },
    // 响应动画方案的切换
    handleTransitionToggle () {
      this.$store.dispatch('setTransition', this.appConfig.transition)
      this.toggleUserAppConfig(() => {
        this.$nextTick(() => {
          this.$message.success('动画效果设置成功！', 1)
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
