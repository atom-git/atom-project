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
    widgetResize (curWidget, widgetConfig) {
      // 栅格布局的参数调整
      if (curWidget.type === 'grid') {
        this.gridResize(curWidget, widgetConfig)
      } else if (curWidget.type === 'table') {
        this.tableResize(curWidget, widgetConfig)
      }
    },
    // 栅格布局的参数调整
    gridResize (curWidget, widgetConfig) {
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
    tableResize (curWidget, widgetConfig) {
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
    }
  }
}
