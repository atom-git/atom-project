/**
 * 动画集
 */
export const animateSet = {
  back: {
    name: 'back',
    title: '渐近',
    directionIns: ['Down', 'Left', 'Right', 'Up'],
    directionOuts: ['Up', 'Right', 'Left', 'Down']
  },
  bounce: {
    name: 'bounce',
    title: '弹跳',
    directionIns: ['In', 'Down', 'Left', 'Right', 'Up'],
    directionOuts: ['Out', 'Up', 'Right', 'Left', 'Down']
  },
  fade: {
    name: 'fade',
    title: '淡化',
    directionIns: ['In', 'Down', 'DownBig', 'Left', 'LeftBig', 'Right', 'RightBig', 'Up', 'UpBig', 'TopLeft', 'TopRight', 'BottomLeft', 'BottomRight'],
    directionOuts: ['Out', 'Up', 'UpBig', 'Right', 'RightBig', 'Left', 'LeftBig', 'Down', 'DownBig', 'BottomRight', 'BottomLeft', 'TopRight', 'TopLeft']
  },
  flip: {
    name: 'flip',
    title: '翻转',
    directionIns: ['X', 'Y'],
    directionOuts: ['Y', 'X']
  },
  lightSpeed: {
    name: 'lightSpeed',
    title: '光速',
    directionIns: ['Right', 'Left'],
    directionOuts: ['Left', 'Right']
  },
  rotate: {
    name: 'rotate',
    title: '旋转',
    directionIns: ['In', 'DownLeft', 'DownRight', 'UpLeft', 'UpRight'],
    directionOuts: ['Out', 'UpRight', 'UpLeft', 'DownRight', 'DownLeft']
  },
  zoom: {
    name: 'zoom',
    title: '缩放',
    directionIns: ['In', 'Down', 'Left', 'Right', 'Up'],
    directionOuts: ['Out', 'Up', 'Right', 'Left', 'Down']
  },
  slide: {
    name: 'slide',
    title: '滑动',
    directionIns: ['Left', 'Right', 'Down', 'Up'],
    directionOuts: ['Right', 'Left', 'Up', 'Down']
  }
}

/**
 * 方向参数
 */
export const directionSet = {
  In: 'In', Down: 'Down', Left: 'Left', Right: 'Right', Up: 'Up', Out: 'Out',
  DownLeft: 'DownLeft', DownRight: 'DownRight', UpLeft: 'UpLeft', UpRight: 'UpRight',
  UpBig: 'UpBig', RightBig: 'RightBig', LeftBig: 'LeftBig', DownBig: 'DownBig',
  BottomRight: 'BottomRight', BottomLeft: 'BottomLeft', TopRight: 'TopRight', TopLeft: 'TopLeft',
  X: 'X', Y: 'Y'
}
