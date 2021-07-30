import { mapGetters, mapActions } from 'vuex'
/*
 * 默认配置的混入
 */
export default {
  computed: {
    ...mapGetters([
      'title',
      'primaryColor',
      'theme',
      'layout',
      'multiTab',
      'multiTabDraggable',
      'fixHeader',
      'transition',
      'device',
      'scrollTop',
      'clientWidth',
      'clientHeight',
      'contentHeight',
      'waterMarkEnable'
    ]),
    // 主题颜色 dark mix 均对应dark, light对应light
    colorTheme () {
      return this.theme === 'light' ? 'light' : 'dark'
    }
  },
  methods: {
    ...mapActions([
      'setPrimaryColor',
      'setTheme',
      'setLayout',
      'setMultiTab',
      'setMultiTabDraggable',
      'setFixHeader',
      'setTransition',
      'setWaterMarkEnable'
    ])
  }
}
