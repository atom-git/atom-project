/**
 * 组件特有配置
 * FormItem公共属性
 * 一、key,title,width,default,labelvisible,placeholder公共生成
 * 二、disabled:是否禁用默认否,rules:校验规则,设置默认选项
 * 三、labelvisible:默认显示, style:自定义样式, placeholder:不设置时自动根据label生成
 */
export default {
  // 布局组件
  grid: [
    { name: 'gutter', type: 'number', label: '栅格间隔', default: 0 },
    { name: 'align', type: 'radio', label: '水平布局', default: 'top', mode: 'button', buttonStyle: 'solid', options: [
      { value: 'top', title: '顶部对齐' }, { value: 'middle', title: '垂直居中' }, { value: 'bottom', title: '底部对齐' }]
    },
    { name: 'justify', type: 'select', label: '垂直布局', default: 'start', options: [
        { value: 'start', title: '左对齐' }, { value: 'end', title: '右对齐' },
        { value: 'center', title: '水平居中' }, { value: 'space-around', title: '两端间隔' },
        { value: 'space-between', title: '两端对齐' }
      ]
    },
    { name: 'wrap', type: 'switch', label: '是否换行', default: 1 }
  ],
  col: [
    { name: 'span', type: 'number', label: '栅格列数', default: 12 },
    { name: 'offset', type: 'number', label: '栅格左侧的间隔格数' },
    { name: 'pull', type: 'number', label: '栅格向左移动格数' },
    { name: 'push', type: 'number', label: '栅格向右移动格数' },
    { name: 'xs', type: 'text', label: '<576px 响应式栅格' },
    { name: 'sm', type: 'text', label: '≥576px 响应式栅格' },
    { name: 'md', type: 'text', label: '≥768px 响应式栅格' },
    { name: 'lg', type: 'text', label: '≥992px 响应式栅格' },
    { name: 'xl', type: 'text', label: '≥1200px 响应式栅格' },
    { name: 'xxl', type: 'text', label: '≥1600px 响应式栅格' },
  ],
  table: [
    { name: 'title', type: 'text', label: '标题' },
    { name: 'width', type: 'number', label: '表格宽度', min: 20, max: 100, default: 100, formatter: value => `${value}%`, parser: value => value.replace('%', '') },
    { name: 'rowCol', type: 'inputGroup', label: '表格行列', group: [
        { name: 'row', type: 'text', suffix: 'atom-add-row-after', slot: 'tableRow' },
        { name: 'col', type: 'text', suffix: 'atom-add-col-after', slot: 'tableCol' }
      ]
    },
    { name: 'borderStyle', type: 'select', label: '边框样式', option: [
        { value: 'dashed', title: '虚线-dashed' }, { value: 'dotted', title: '圆点-dotted' }, { value: 'double', title: '虚线-double' },
        { value: 'groove', title: '沟槽-groove' }, { value: 'ridge', title: '山脊-ridge' }, { value: 'hidden', title: '隐藏-hidden' }
      ]
    }
  ],
  tab: [
    { name: 'type', type: 'radio', label: '标签页风格', default: 'line', mode: 'button', buttonStyle: 'solid', options: [
      { value: 'line', title: '选项卡' }, { value: 'card', title: '卡片式' }]
    },
    { name: 'tabPosition', type: 'radio', label: '标签位置', default: 'top', mode: 'button', buttonStyle: 'solid', options: [
        { value: 'top', title: '顶部' }, { value: 'right', title: '右侧' },
        { value: 'bottom', title: '底部' }, { value: 'left', title: '左侧' }
      ]
    },
    { name: 'tabs', type: 'input', slot: 'tabs' }
  ],
  space: [
    { name: 'direction', type: 'radio', label: '间距方向', default: 'horizontal', mode: 'button', buttonStyle: 'solid', options: [
        { value: 'vertical', title: '垂直对齐' }, { value: 'horizontal', title: '水平对齐' },
      ]
    },
    { name: 'align', type: 'radio', label: '对齐方式', default: 'center', mode: 'button', buttonStyle: 'solid', options: [
        { value: 'start', title: '头部对齐' }, { value: 'end', title: '尾部对齐' },
        { value: 'center', title: '居中对齐' }, { value: 'baseline', title: '基线对齐' }
      ]
    },
    { name: 'size', type: 'radio', label: '对齐方式', default: 'center', mode: 'button', buttonStyle: 'solid', options: [
        { value: 'small', title: '较小' }, { value: 'middle', title: '默认' }, { value: 'large', title: '较大' }
      ],
      slot: 'space'
    }
  ],
  step: [
    { name: 'type', type: 'radio', label: '步骤条类型', default: 'default', mode: 'button', buttonStyle: 'solid', options: [
        { value: 'default', title: '默认' }, { value: 'navigation', title: '导航条' }]
    },
    { name: 'direction', type: 'radio', label: '步骤条方向', default: 'horizontal', mode: 'button', buttonStyle: 'solid', options: [
        { value: 'vertical', title: '垂直对齐' }, { value: 'horizontal', title: '水平对齐' }
      ]
    },
    { name: 'labelPlacement', type: 'radio', label: '标签位置', default: 'horizontal', mode: 'button', buttonStyle: 'solid', options: [
        { value: 'vertical', title: '垂直对齐' }, { value: 'horizontal', title: '水平对齐' }
      ]
    },
    { name: 'progressDot', type: 'switch', label: '设置为点状步骤条', default: false },
    { name: 'size', type: 'radio', label: '组件大小', default: 'default', mode: 'button', buttonStyle: 'solid', options: [
        { value: 'default', title: '默认' }, { value: 'small', title: '迷你' }
      ]
    }
  ],
  desc: [
    { name: 'title', type: 'text', label: '标题' },
    { name: 'colon', type: 'switch', label: '是否显示冒号', default: false },
    { name: 'column', type: 'inputGroup', label: '每行组件数量', group: [
        { name: 'columnType', type: 'select',default: 'span', slot: 'columnType', options: [{ value: 'span', title: '固定' }, { value: 'grid', title: '响应式' }] },
        { name: 'columnSize', type: 'text', default: 3, slot: 'columnSize'}
      ]
    },
    { name: 'layout', type: 'radio', label: '布局方式', default: 'horizontal', mode: 'button', buttonStyle: 'solid', options: [
        { value: 'vertical', title: '垂直布局' }, { value: 'horizontal', title: '水平布局' }
      ]
    },
    { name: 'size', type: 'radio', label: '组件大小', default: 'default', mode: 'button', buttonStyle: 'solid', options: [
        { value: 'small', title: '较小' }, { value: 'middle', title: '中等' }, { value: 'default', title: '默认' }
      ]
    }
  ],
  divider: [
    { name: 'title', type: 'text', label: '标题', default: '分割线' },
    { name: 'orientation', type: 'radio', label: '标题位置', default: 'left', mode: 'button', buttonStyle: 'solid', options: [
        { value: 'left', title: '左侧' }, { value: 'center', title: '居中' }, { value: 'right', title: '右侧' }
      ]
    },
    { name: 'dashed', type: 'switch', label: '是否虚线', default: false },
    { name: 'plain', type: 'switch', label: '是否普通正文样式', default: false }
  ],
  // 基础组件
  input: [
    { name: 'maxlength', type: 'number', label: '最大长度' },
    { name: 'prefix', type: 'iconPicker', label: '前缀图标' },
    { name: 'suffix', type: 'iconPicker', label: '后缀图标' }
  ],
  textarea: [
    { name: 'maxlength', type: 'number', label: '最大长度', step: 10 },
    { name: 'rows', type: 'number', label: '行数', default: 4 },
    { name: 'autosize', type: 'switch', label: '自适应内容高度', default: 0 },
    { name: 'showCount', type: 'switch', label: '是否展示字数', default: 1 }
  ],
  number: [
    { name: 'range', type: 'inputGroup', label: '区间步长', group: [
        { name: 'min', type: 'number', label: '最小值', default: 0, style: { width: '40%' } },
        { name: 'max', type: 'number', label: '最大值', default: 0, style: { width: '40%' } },
        { name: 'step', type: 'text', label: '步长', style: { width: '20%' } }
      ]
    },
    { name: 'formatter', type: 'text', label: '展示格式', default: '`${value}`.replace(/\\B(?=(\\d{3})+(?!\\d))/g, \',\')' },
    { name: 'parser', type: 'text', label: '展示格式', default: 'value.replace(/\\$\\s?|(,*)/g, \'\')' }
  ],
  select: [
    { name: 'mode', type: 'radio', label: '选择模式', default: 'horizontal', mode: 'button', buttonStyle: 'solid', options: [
        { value: '', title: '默认' }, { value: 'multiple', title: '多选' }, { value: 'tags', title: '标签' }
      ]
    },
    { name: 'options', type: '', label: '选项' }
  ],
  radio: [],
  checkbox: [],
  switch: [],
  cascader: [],
  datePicker: [],
  monthPicker: [],
  rangePicker: [],
  weekPicker: [],
  timePicker: [],
  title: [],
  text: [],
  button: [],
  link: [],
  html: [],
  // 高阶组件
  treeSelect: [],
  transfer: [],
  slider: [],
  mentions: [],
  rate: [],
  inputGroup: [],
  fileUpload: [],
  imagePicker: [],
  iconPicker: [],
  tagCheck: [],
  mapPicker: [],
  tableSelect: [],
  richText: []
}
