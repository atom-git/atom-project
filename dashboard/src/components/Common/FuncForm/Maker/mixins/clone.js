import { initItems } from '../Panel/WidgetOptions'
/**
 * 组件clone初始化
 */
export default {
  methods: {
    // 组件拖拽clone时初始化动作
    widgetClone (widget) {
      if (widget.type === 'grid') {
        this.gridClone(widget)
      } else if (widget.type === 'table') {
        this.tableClone(widget)
      } else if (widget.type === 'tabs') {
        this.tabsClone(widget)
      } else if (widget.type === 'steps') {
        this.stepsClone(widget)
      }
    },
    // 网格布局clone
    gridClone (widget) {
      widget['items'] = [
        { key: 'column_0', order: 0, span: 12, widgets: [] },
        { key: 'column_1', order: 1, span: 12, widgets: [] }
      ]
    },
    // 表格布局clone
    tableClone (widget) {
      widget['items'] = [
        { key: 'row_0', columns: [{ key: 'column_0_0', widgets: [] }, { key: 'column_0_1', widgets: [] }] },
        { key: 'row_1', columns: [{ key: 'column_1_0', widgets: [] }, { key: 'column_1_1', widgets: [] }] },
      ]
    },
    // 标签页布局clone
    tabsClone (widget) {
      widget['items'] = initItems('Tab').options.map(item => ({ key: item.value, tab: item.value, widgets: [] }))
    },
    // 分步布局clone
    stepsClone (widget) {
      widget['items'] = initItems('Step').options.map(item => ({ key: item.value, title: item.value, widgets: [] }))
    }
  }
}
