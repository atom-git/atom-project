/**
 * 水印指令
 * content: 水印内容，由外部传入
 * flag: 水印是否启用，默认为启用
 * options: 水印样式配置
 */
const options = {
  zIndex: 1000,
  font: '16px -apple-system, BlinkMacSystemFont, sans-serif',
  lightColor: 'rgba(0, 0, 0, 0.1)',
  darkColor: 'rgba(255, 255, 255, 0.1)',
  rotate: -30,
  width: 300,
  height: 200
}
/*
  构建水印图层
 */
function initMark (el, content, theme) {
  // 设置水印canvas
  const markBase64 = generateMark(content, theme)
  // 创建水印元素
  let markEl = document.createElement('div')
  markEl.id = 'atom-watermark'
  markEl.setAttribute(
    'style',
    `position: absolute; top: 0; left: 0; width: 100%; height: 100%; z-index: ${options.zIndex}; pointer-events: none; background-repeat: repeat; background-image: url('${markBase64}');`
  )
  // 给水印增加删除事件的判断，如果节点被清除，则重新加载
  markEl.addEventListener('DOMNodeRemoved', () => {
    initMark(el, content, theme)
  }, false)
  el.style.position = 'relative'
  el.appendChild(markEl)
}

/**
 * 生成水印图片
 */
function generateMark (content, theme) {
  // 设置水印canvas
  let canvas = document.createElement('canvas')
  canvas.setAttribute('width', `${options.width}px`)
  canvas.setAttribute('height', `${options.height}px`)
  let ctx = canvas.getContext('2d')

  ctx.textAlign = 'center'
  ctx.textBaseline = 'top'
  ctx.font = options.font
  ctx.fillStyle = theme === 'dark' ? options.darkColor : options.lightColor
  ctx.translate(options.width / 2, options.height / 2)
  ctx.rotate(Math.PI / 180 * options.rotate)
  ctx.fillText(content, 0, 0)
  return canvas.toDataURL('image/png')
}
export default {
  mounted: function (el, binding) {
    const { content, enable = true, theme = 'light' } = binding.value
    if (enable) {
      initMark(el, content, theme)
    }
  },
  updated: function (el, binding) {
    const { content, enable = true, theme = 'light' } = binding.value
    // 判断是否有水印dom
    let markEl = document.getElementById('atom-watermark')
    // 先清除原有水印
    if (markEl) {
      if (enable) {
        markEl.style.backgroundImage = `url('${generateMark(content, theme)}')`
      } else {
        // 不生成水印时，把背景去除
        markEl.style.backgroundImage = ''
      }
    } else {
      // 如果水印不存在，且设置要求增加时，则直接增加
      if (enable) {
        initMark(el, content, theme)
      }
    }
  }
}
