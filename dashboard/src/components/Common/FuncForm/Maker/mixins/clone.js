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
      } else if (widget.type === 'tab') {
        this.tabClone(widget)
      } else if (widget.type === 'step') {
        this.stepClone(widget)
      } else if (widget.type === 'desc') {
        this.descClone(widget)
      }
    },
    // 网格布局clone
    gridClone (widget) {
      widget['columns'] = [
        { key: 'column_0', order: 0, span: 12, widgets: [] },
        { key: 'column_1', order: 1, span: 12, widgets: [] }
      ]
    },
    // 表格布局clone
    tableClone (widget) {
      widget['rows'] = [
        { key: 'row_0', columns: [{ key: 'column_0_0', widgets: [] }, { key: 'column_0_1', widgets: [] }] },
        { key: 'row_1', columns: [{ key: 'column_1_0', widgets: [] }, { key: 'column_1_1', widgets: [] }] },
      ]
    },
    // 标签页布局clone
    tabClone (widget) {
      widget['tabs'] = initItems('Tab').options.map(item => ({ key: item.value, tab: item.value, widgets: [] }))
    },
    // 分步布局clone
    stepClone (widget) {
      widget['steps'] = initItems('Step').options.map(item => ({ key: item.value, title: item.value, widgets: [] }))
    },
    // 描述布局clone
    descClone (widget) {
      widget['items'] = initItems('Item').options.map(item => ({ key: item.value, label: item.value, widgets: [] }))
    }
  }
}
