/**
 * 组件复制混入
 */
export default {
  methods: {
    // 深度复制
    deepCopy (widget) {
      const key = widget.type + '_' + this.$utils.randomStr(8)
      const copy = Object.assign(this.$utils.deepClone(widget), { key: key})
      // 修改fields, options, widgetConfig中的key值
      copy.fields.filter(field => field.name === 'key').forEach(field => field.default = copy.key)
      copy.options['key'] = copy.key
      copy.widgetConfig['key'] = copy.key
      return copy
    },
    /**
     * 组件复制
     * @param widget 待复制组件
     * @param index 组件位置
     * @param widgetGroup 组件组，在单纯form组件中即为widgets，在布局组件中可能是column.widgets等情况
     */
    widgetCopy (widget, index, widgetGroup) {
      const copy = this.deepCopy(widget)
      // 布局组件的复制
      if (widget.group === 'layout') {
        /**
         * 对内部的每个子组件进行深复制并改key
         * 如果是grid布局，则对每个column中的内容进行复制
         */
        if (widget.type === 'grid') {
          this.gridWidgetCopy(copy)
        } else if (widget.type === 'table') {
          this.tableWidgetCopy(copy)
        }
      }
      widgetGroup.splice(index + 1, 0, copy)
      return copy
    },
    // grid布局组件复制
    gridWidgetCopy (gridWidget) {
      const columns = gridWidget.columns
      columns.forEach(column => {
        // 判断是否存在子组件，如果存在子组件对齐进行递归的复制
        if (this.$utils.isValid(column.widgets)) {
          column.widgets = column.widgets.map((widget, index) => this.widgetCopy(widget, index, column.widgets))
        }
      })
    },
    // table组件复制
    tableWidgetCopy (tableWidget) {
      const rows = tableWidget.rows
      rows.forEach(row => {
        if (row.columns) {
          row.columns.forEach(column => {
            // 判断是否存在子组件，如果存在子组件对齐进行递归的复制
            if (this.$utils.isValid(column.widgets)) {
              column.widgets = column.widgets.map((widget, index) => this.widgetCopy(widget, index, column.widgets))
            }
          })
        }
      })
    }
  }
}
