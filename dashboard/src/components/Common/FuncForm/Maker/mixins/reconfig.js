/**
 * 组件重置大小混入，包括grid，table等布局
 */
export default {
  methods: {
    /**
     * 组件大小改变
     * @param curWidget 当前操作的组件
     * @param widgetConfig 组件配置
     */
    widgetReconfig (curWidget, widgetConfig) {
      // 栅格布局的参数调整
      if (curWidget.type === 'grid') {
        this.gridReconfig(curWidget, widgetConfig)
      } else if (curWidget.type === 'table') {
        this.tableReconfig(curWidget, widgetConfig)
      } else if (curWidget.type === 'tabs') {
        this.tabsReconfig(curWidget, widgetConfig)
      } else if (curWidget.type === 'steps') {
        this.stepsReconfig(curWidget, widgetConfig)
      }
    },
    // 栅格布局的参数调整
    gridReconfig (curWidget, widgetConfig) {
      // 根据列数，计算列宽
      const span = 24 / (widgetConfig['col'] || 2)
      // 变化的列数
      const colChange = widgetConfig['col'] - curWidget.columns.length
      // 如果列数增加，在最后面增加，并调整列宽
      if (colChange > 0) {
        // 调整列宽
        curWidget.columns.forEach(column => column.span = span)
        for (let index = 0; index < colChange; index++) {
          curWidget.columns.push({
            key: 'column_' + (curWidget.columns.length + index),
            order: curWidget.columns.length + index,
            span: span,
            widgets: []
          })
        }
      } else {
        // 如果列数减少，从最后面减，并调整列宽
        curWidget.columns.splice(widgetConfig['col'], Math.abs(colChange))
        curWidget.columns.forEach(column => column.span = span)
      }
    },
    // 表格布局的参数调整
    tableReconfig (curWidget, widgetConfig) {
      // 表格的边框样式调整
      curWidget.options.style.border = `${widgetConfig.borderWidth}px ${widgetConfig.borderStyle} ${widgetConfig.borderColor}`
      // 根据配置重新生成行列
      const row = widgetConfig.row
      const col = widgetConfig.col
      const originRow = curWidget.rows.length
      const originCol = curWidget.rows[0].columns.length
      // 先判断列是否变化
      const colChange = col - originCol
      if (colChange > 0) {
        curWidget.rows.forEach((row, rowIndex) => {
          if (row.columns) {
            for (let index = 0; index < colChange; index++) {
              row.columns.push({
                key: 'column_' + rowIndex + '_' + (originCol + index),
                widgets: []
              })
            }
          }
        })
      } else {
        curWidget.rows.forEach(row => {
          if (row.columns) {
            row.columns.splice(col, Math.abs(colChange))
          }
        })
      }
      // 再判断行是否变化
      const rowChange = row - originRow
      if (rowChange > 0) {
        for (let index = 0; index < rowChange; index++) {
          curWidget.rows.push({
            key: 'row_' + (originRow + index),
            columns: this.initTableColumns(originRow + index, col)
          })
        }
      } else {
        curWidget.rows.splice(row, Math.abs(rowChange))
      }
    },
    // 根据行和列数初始化列组
    initTableColumns (rowIndex, colCnt) {
      const columns = []
      for (let index = 0; index < colCnt; index++) {
        columns.push({
          key: 'column_' + rowIndex + '_' + index,
          widgets: []
        })
      }
      return columns
    },
    // 标签页布局的参数调整
    tabsReconfig (curWidget, widgetConfig) {
      this.itemsReconfig(curWidget, widgetConfig, 'tabs', 'tab')
    },
    // 分步布局的参数调整
    stepsReconfig (curWidget, widgetConfig) {
      this.itemsReconfig(curWidget, widgetConfig, 'steps')
      // 判断step当前的stepType是否为'navigation'，是的时候把其他选项都禁用掉
      if (widgetConfig['stepType'] === 'navigation') {
        widgetConfig['direction'] = widgetConfig['labelPlacement'] = 'horizontal'
        widgetConfig['progressDot'] = false
        curWidget.fields.forEach(field => {
          if (['direction', 'labelPlacement', 'progressDot'].includes(field.name)) {
            field.disabled = true
          }
        })
      } else {
        curWidget.fields.forEach(field => field.disabled = false)
      }
    },
    // 多个元素的统一设置调试
    itemsReconfig (curWidget, widgetConfig, key = 'items', titleKey = 'title') {
      /**
       * 判断items长度，用widgetConfig中的items去按顺序匹配，
       * 位置一样的改item名称
       * 位置超出的删除
       * 位置不足的补齐
       */
      if (widgetConfig[key] && widgetConfig[key].options) {
        const items = widgetConfig[key].options
        for (let index = 0; index < items.length; index++) {
          const item = items[index]
          if (curWidget[key] && curWidget[key][index]) {
            curWidget[key][index].key = item.value
            curWidget[key][index][titleKey] = item.label
          } else {
            const add = { key: item.value, widgets: [] }
            add[titleKey] = item.label
            curWidget[key].push(add)
          }
        }
        // 删除超出的部分
        curWidget[key].splice(items.length)
      }
    }
  }
}
