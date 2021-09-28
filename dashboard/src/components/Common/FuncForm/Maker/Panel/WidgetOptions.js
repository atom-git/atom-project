/**
 * 默认单级选项
 */
const monoOptions = {
  labelShow: false,
  multiple: false,
  default: ['option1'],
  options:  [
    { value: 'option1' },
    { value: 'option2' },
    { value: 'option3' }
  ]
}
/**
 * 默认多级选项
 */
const cascadeOptions = {
  labelShow: false,
  default: ['option1', 'option1-2'],
  options:  [
    { value: 'option1', children: [{ value: 'option1-1' }, { value: 'option1-2' }] },
    { value: 'option2', children: [{ value: 'option2-1' }, { value: 'option2-2' }] },
    { value: 'option3', children: [{ value: 'option3-1' }, { value: 'option3-2' }, { value: 'option3-3' }] }
  ]
}
/**
 * 通用配置信息
 */
export const CommonOptions = {
  key: { name: 'key', type: 'text', label: '字段标识[需当前表单惟一]', required: true },
  label: { name: 'label', type: 'text', label: '标题', required: true },
  width: { name: 'width', type: 'number', label: '宽度', min: 0, max: 100, default: 100, formatter: value => `${value}%`, parser: value => value.replace('%', '') },
  disabled: { name: 'disabled', type: 'switch', label: '是否禁用', checkedValue: true, unCheckedValue: false, default: false },
  labelVisible: { name: 'labelVisible', type: 'switch', label: '显示标签', checkedValue: true, unCheckedValue: false, default: true },
  rules: { name: 'rules', type: 'textarea', label: '校验规则' },
  placeholder: { name: 'placeholder', type: 'text', label: '占位提示' }
}
/**
 * 组件特有配置
 * FormItem公共属性
 * 一、key,title,width基础属性
 * 二、disabled:是否禁用默认否, labelvisible:默认显示
 * 三、rules:校验规则,设置默认选项, style:自定义样式
 * 四、placeholder:不设置时自动根据label生成
 */
export default {
  // 布局组件
  grid: [
    { name: 'colCount', type: 'select', label: '列数', default: 2, options: [
        { value: 2, title: '2列' }, { value: 3, title: '3列' }, { value: 4, title: '4列' },
        { value: 6, title: '6列' }, { value: 8, title: '8列' }, { value: 12, title: '12列' }
      ]
    },
    { name: 'gutter', type: 'number', label: '栅格间隔', default: 8 },
    { name: 'align', type: 'radio', label: '水平布局', default: 'top', mode: 'button', buttonStyle: 'solid', options: [
      { value: 'top', title: '顶部对齐' }, { value: 'middle', title: '垂直居中' }, { value: 'bottom', title: '底部对齐' }]
    },
    { name: 'justify', type: 'select', label: '垂直布局', default: 'start', options: [
        { value: 'start', title: '左对齐' }, { value: 'end', title: '右对齐' },
        { value: 'center', title: '水平居中' }, { value: 'space-around', title: '两端间隔' },
        { value: 'space-between', title: '两端对齐' }
      ]
    },
    { name: 'wrap', type: 'switch', label: '是否换行', checkedValue: true, unCheckedValue: false, default: true }
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
    { name: 'progressDot', type: 'switch', label: '设置为点状步骤条', checkedValue: true, unCheckedValue: false, default: false },
    { name: 'size', type: 'radio', label: '组件大小', default: 'default', mode: 'button', buttonStyle: 'solid', options: [
        { value: 'default', title: '默认' }, { value: 'small', title: '迷你' }
      ]
    }
  ],
  desc: [
    { name: 'title', type: 'text', label: '标题' },
    { name: 'colon', type: 'switch', label: '是否显示冒号', checkedValue: true, unCheckedValue: false, default: false },
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
    { name: 'dashed', type: 'switch', label: '是否虚线', checkedValue: true, unCheckedValue: false, default: false },
    { name: 'plain', type: 'switch', label: '是否普通正文样式', checkedValue: true, unCheckedValue: false, default: false }
  ],
  // 基础组件
  input: [
    { name: 'default', type: 'text', label: '默认值' },
    { name: 'maxlength', type: 'number', label: '最大长度' },
    { name: 'prefix', type: 'iconPicker', label: '前缀图标' },
    { name: 'suffix', type: 'iconPicker', label: '后缀图标' }
  ],
  textarea: [
    { name: 'default', type: 'textarea', label: '默认值' },
    { name: 'maxlength', type: 'number', label: '最大长度', step: 10 },
    { name: 'rows', type: 'number', label: '行数', default: 4 },
    { name: 'autoSize', type: 'switch', label: '自适应内容高度', checkedValue: true, unCheckedValue: false, default: false },
    { name: 'showCount', type: 'switch', label: '是否展示字数', checkedValue: true, unCheckedValue: false, default: true }
  ],
  number: [
    { name: 'default', type: 'number', label: '默认值' },
    { name: 'range', type: 'inputGroup', label: '区间步长', group: [
        { name: 'min', type: 'number', label: '最小值', default: 0, style: { width: '40%' } },
        { name: 'max', type: 'number', label: '最大值', default: 0, style: { width: '40%' } },
        { name: 'step', type: 'text', label: '步长', style: { width: '20%' } }
      ]
    },
    { name: 'formatter', type: 'text', label: '展示格式', default: '(value) => `${value}`.replace(/\\B(?=(\\d{3})+(?!\\d))/g, \',\')' },
    { name: 'parser', type: 'text', label: '展示格式', default: '(value) => value.replace(/\\$\\s?|(,*)/g, \'\')' }
  ],
  select: [
    { name: 'mode', type: 'radio', label: '选择模式', default: '-', mode: 'button', buttonStyle: 'solid', options: [
        { value: '-', title: '默认' }, { value: 'multiple', title: '多选' }, { value: 'tags', title: '标签' }
      ]
    },
    { name: 'options', type: 'optionTree', label: '选项', default: monoOptions, help: '必须保证值的全局惟一性' }
  ],
  radio: [
    { name: 'mode', type: 'radio', label: '选择模式', default: '-', mode: 'button', buttonStyle: 'solid', options: [
        { value: '-', title: '默认' }, { value: 'button', title: '按钮' }
      ]
    },
    { name: 'buttonStyle', type: 'radio', label: '按钮模式', default: '-', mode: 'button', buttonStyle: 'solid', options: [
        { value: 'outline', title: '边框' }, { value: 'solid', title: '填底' }
      ]
    },
    { name: 'options', type: 'optionTree', label: '选项', default: monoOptions, help: '必须保证值的全局惟一性' }
  ],
  checkbox: [
    { name: 'options', type: 'optionTree', label: '选项', default: monoOptions, help: '必须保证值的全局惟一性' }
  ],
  switch: [
    { name: 'default', type: 'switch', label: '默认值' }
  ],
  cascader: [
    { name: 'options', type: 'optionTree', label: '选项', default: cascadeOptions, cascade: true, help: '必须保证值的全局惟一性' },
    { name: 'showSearch', type: 'switch', label: '是否支持搜索', checkedValue: true, unCheckedValue: false, default: false }
  ],
  datePicker: [
    { name: 'default', type: 'datePicker', label: '默认值' }
  ],
  monthPicker: [
    { name: 'default', type: 'monthPicker', label: '默认值' }
  ],
  rangePicker: [
    { name: 'default', type: 'rangePicker', label: '默认值' }
  ],
  weekPicker: [
    { name: 'default', type: 'weekPicker', label: '默认值' }
  ],
  timePicker: [
    { name: 'default', type: 'timePicker', label: '默认值' }
  ],
  title: [
    { name: 'default', type: 'text', label: '默认值' }
  ],
  text: [
    { name: 'default', type: 'textarea', label: '默认值', default: '文本提示' }
  ],
  link: [
    { name: 'content', type: 'textarea', label: '替代文本', default: '链接文本' },
    { name: 'href', type: 'text', label: '链接' }
  ],
  html: [
    { name: 'content', type: 'textarea', label: '页面内容', default: 'HTML内容' }
  ],
  // 高阶组件
  treeSelect: [
    { name: 'multiple', type: 'switch', label: '是否支持多选', checkedValue: true, unCheckedValue: false, default: false },
    { name: 'options', type: 'optionTree', label: '选项', default: cascadeOptions, cascade: true, help: '必须保证值的全局惟一性' },
    { name: 'showSearch', type: 'switch', label: '是否支持搜索', checkedValue: true, unCheckedValue: false, default: false }
  ],
  transfer: [
    { name: 'options', type: 'optionTree', label: '选项', default: cascadeOptions, cascade: true, help: '必须保证值的全局惟一性' },
    { name: 'titles', type: 'select', label: '选框标题', mode: 'tags', default: ['来源', '目标'], help: '仅支持填写两个' },
  ],
  slider: [
    { name: 'range', type: 'switch', label: '区间模式', checkedValue: true, unCheckedValue: false, default: false },
    { name: 'min', type: 'number', label: '最小值', default: 0 },
    { name: 'max', type: 'number', label: '最大值', default: 100 },
    { name: 'step', type: 'number', label: '步长', default: 1 },
  ],
  mentions: [
    { name: 'options', type: 'optionTree', label: '选项', default: monoOptions, help: '必须保证值的全局惟一性' }
  ],
  rate: [
    { name: 'allowHalf', type: 'switch', label: '是否允许半星', checkedValue: true, unCheckedValue: false, default: false },
    { name: 'count', type: 'number', label: 'Star总数' }
  ],
  inputGroup: [],
  fileUpload: [
    { name: 'multiple', type: 'switch', label: '是否多选', checkedValue: true, unCheckedValue: false, default: true },
    { name: 'fileType', type: 'number', label: '支持的文件类型', default: 'file', help: '支持指定类型【file|img|radio|video】或者自定义' },
    { name: 'max', type: 'number', label: '最多支持上传文件数', default: 5 },
  ],
  imagePicker: [],
  tagCheck: [
    { name: 'multiple', type: 'switch', label: '是否多选', checkedValue: true, unCheckedValue: false, default: true },
    { name: 'options', type: 'optionTree', label: '选项', default: monoOptions, help: '必须保证值的全局惟一性' }
  ],
  mapPicker: [],
  tableSelect: [],
  richText: []
}
