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
 * 默认多项目选项
 */
export function initItems (title) {
  return {
    labelShow: false,
    multiple: false,
    default: [`${title}1`],
    options: [
      { value: `${title}1` },
      { value: `${title}2` },
      { value: `${title}3` }
    ]
  }
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
    { name: 'col', type: 'select', label: '列数', default: 2, options: [
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
  table: [
    { name: 'rowCol', type: 'inputGroup', label: '表格行列', group: [
        { name: 'row', label: '行', default: 2, type: 'number', min: 2, max: 6, formatter: value => `${value}行`, parser: value => value.replace('行', ''), style: { width: '50%' } },
        { name: 'col', label: '列', default: 2, type: 'number', min: 2, max: 6, formatter: value => `${value}列`, parser: value => value.replace('列', ''), style: { width: '50%' } }
      ]
    },
    { name: 'borderWidth', type: 'number', label: '边框宽度', default: 1, formatter: value => `${value}px`, parser: value => value.replace('px', '') },
    { name: 'borderStyle', type: 'select', label: '边框样式', default: 'solid', options: [
        { value: 'solid', title: '实线-solid' }, { value: 'dashed', title: '虚线-dashed' }, { value: 'dotted', title: '圆点-dotted' }, { value: 'double', title: '双线-double' },
        { value: 'groove', title: '沟槽-groove' }, { value: 'ridge', title: '山脊-ridge' }, { value: 'hidden', title: '隐藏-hidden' }
      ]
    },
    { name: 'borderColor', type: 'colorPicker', label: '边框颜色', default: '' }
  ],
  tabs: [
    { name: 'tabType', type: 'radio', label: '标签页风格', default: 'line', mode: 'button', buttonStyle: 'solid', options: [
      { value: 'line', title: '选项卡' }, { value: 'card', title: '卡片式' }]
    },
    { name: 'tabPosition', type: 'radio', label: '标签位置', default: 'top', mode: 'button', buttonStyle: 'solid', options: [
        { value: 'top', title: '顶部' }, { value: 'right', title: '右侧' },
        { value: 'bottom', title: '底部' }, { value: 'left', title: '左侧' }
      ]
    },
    { name: 'tabs', type: 'optionTree', label: '选项标签', showLabelOption: false, default: initItems('Tab'), help: '必须保证值的全局惟一性' }
  ],
  steps: [
    { name: 'stepType', type: 'radio', label: '步骤条类型', default: 'navigation', mode: 'button', buttonStyle: 'solid', options: [
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
    { name: 'steps', type: 'optionTree', label: '步骤标签', showLabelOption: false, default: initItems('Step'), help: '必须保证值的全局惟一性' }
  ],
  title: [
    { name: 'title', type: 'text', label: '自定义标题' }
  ],
  alert: [
    { name: 'message', type: 'textarea', label: '内容', default: '提示内容' },
    { name: 'alertType', type: 'radio', label: '提示样式', default: 'warning', mode: 'button', buttonStyle: 'solid', options: [
        { value: 'success', title: '成功' }, { value: 'info', title: '消息' },
        { value: 'warning', title: '提醒' }, { value: 'error', title: '失败' }
      ]
    },
    { name: 'banner', type: 'switch', label: '是否顶部公告', checkedValue: true, unCheckedValue: false, default: true },
    { name: 'closable', type: 'switch', label: '是否可关闭', checkedValue: true, unCheckedValue: false, default: false },
    { name: 'showIcon', type: 'switch', label: '是否显示图标', checkedValue: true, unCheckedValue: false, default: true }
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
  text: [
    { name: 'default', type: 'textarea', label: '默认值', default: '文本提示' }
  ],
  link: [
    { name: 'content', type: 'textarea', label: '替代文本', default: '链接文本' },
    { name: 'href', type: 'text', label: '链接' }
  ],
  html: [
    { name: 'content', type: 'textarea', label: '页面内容', default: '<div>HTML内容</div>' }
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
    { name: 'count', type: 'number', label: 'Star总数', default: 5 }
  ],
  fileUpload: [
    { name: 'multiple', type: 'switch', label: '是否多选', checkedValue: true, unCheckedValue: false, default: true },
    { name: 'acceptType', type: 'radio', label: '文件类型', default: 'file', mode: 'button', buttonStyle: 'solid', options: [
        { value: 'file', title: '文件' }, { value: 'img', title: '图片' },
        { value: 'radio', title: '音频' }, { value: 'video', title: '视频' }
      ],
      help: '支持指定类型【file|img|radio|video】或者自定义'
    },
    { name: 'fileType', type: 'text', label: '自定义文件类型', placeholder: '请输入以逗号分隔的字符串' },
    { name: 'max', type: 'number', label: '最多支持上传文件数', default: 5 },
  ],
  tagCheck: [
    { name: 'multiple', type: 'switch', label: '是否多选', checkedValue: true, unCheckedValue: false, default: true },
    { name: 'options', type: 'optionTree', label: '选项', default: monoOptions, help: '必须保证值的全局惟一性' }
  ],
  imagePicker: [],
  inputGroup: [],
  mapPicker: [],
  tableSelect: [],
  richText: []
}
