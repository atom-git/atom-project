/**
 * 表格表头拖拽
 * v-column-resize 标记到a-table即可，v-column-resize=true or false用于动态调整是否可拖动
 * 存在拖动宽度超过该列可以拥有的最大宽度后，出现回弹拖动条位置不准确的问题
 */
export default {
  mounted: function (el, binding) {
    const tableNodes = el.getElementsByTagName('Table')
    if (!binding.value || !(tableNodes && tableNodes.length > 0)) {
      return
    }
    // 只取第一个table
    const table = tableNodes[0]
    const thead = table.querySelector('thead')
    const ths = thead.querySelectorAll('th')
    let moving = false
    let movingIndex = 0
    // 给每一列增加一个可拖拽的bar，必须配合.ant-table-thead > tr > th 的相关样式来完成
    ths.forEach((th, index) => {
      // 创建操作bar
      const resizeBar = document.createElement('div')
      resizeBar.className = 'atom-column-resize-bar'
      th.appendChild(resizeBar)
      resizeBar.addEventListener('mousedown', () => {
        moving = true
        movingIndex = index
        document.body.style.cursor = 'col-resize'
        document.body.style.userSelect = 'none'
      })
    })
    // 响应鼠标抬起
    document.body.addEventListener('mouseup', () => {
      if (!moving) return
      moving = false
      document.body.style.cursor = ''
      document.body.style.userSelect = ''
    })
    // 响应鼠标移动
    document.body.addEventListener('mousemove', (event) => {
      if (moving) {
        const th = ths[movingIndex]
        th.style.width = (th.offsetWidth + event.movementX) + 'px'
      }
    })
  }
}
