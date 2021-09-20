import moment from 'moment'
import 'moment/locale/zh-cn'
import Default from '@/config/default'
/*
 * 定义工具类
 * isValid: 任意对象是否有效
 * isArray: 是否数组
 * isObject: 是否对象
 * isInt: 是否正整数
 * isBoolean: 是否布尔值
 * isFunction: 是否函数
 * clearObject: 清除obj中undefined或者null的key
 * deepClone: 对象深度克隆，采对属性部门进行克隆
 * buildArray: 根据长度构建array
 * arrayRemove: 删除数组中从from到to的元素
 * firstUpperCase: 字符串首字母大写
 * fromNow: 从目前开始的时间命名
 * toDate: 把时间字符串转日期
 * formatDate: 格式化日期
 * dateStr: 取格式化的日期字符串
 * regExpNum: 判断是否为length长度的数字
 * randomNum: 取范围内的随机数
 * randomColor: 随机颜色
 * filterTreePath: 从树结构中按照keyPath过滤数据
 * filterTree: 从树结构中过滤数据
 * filterObject: 过滤对象key值
 * buildTree: 从list构建tree
 * simpleList: 取list中的某个属性值构成数组
 * simpleTree: 取tree中的某个属性值构成数组
 * concatStr: 多个字符串连接成字符串
 * randomStr: 随机生成字符串
 * formatNum: 格式化数字
 * formatStr: 格式化字符串
 * formatMoney: 格式化钱字符串
 * sortNumAsc: 数字升序排列
 * sortNumDesc: 数字降序排列
 * download: 下载文件
 * base64ToFile: base64转成file
 * fileToBase64: file转成base64
 */
export default class Utils {
  /**
   * 判断一个值是否有效
   * 即非空，非undefined，非空数组，非空对象
   */
  static isValid (obj) {
    if (this.isArray(obj)) {
      return obj.length > 0
    } else if (this.isObject(obj)) {
      return Object.keys(obj).length > 0
    } else {
      return obj !== undefined && obj !== null && obj !== ''
    }
  }

  /**
   * 判断是否是一个数组
   */
  static isArray (obj) {
    return Array.isArray(obj)
  }

  /**
   * 判断是否是一个对象
   */
  static isObject (obj) {
    return Object.prototype.toString.call(obj) === '[object Object]'
  }

  /**
   * 验证是否是正整数
   */
  static isInt (value) {
    return new RegExp(/^[0-9]*$/).test(value)
  }

  /**
   * 验证是否是true false
   */
  static isBoolean (value) {
    return typeof value === 'boolean'
  }

  /**
   * 判断参数是否是方法
   */
  static isFunction (value) {
    return typeof value === 'function'
  }

  /**
   * 清除obj中undefined或者null的key
   * @param obj 对象
   */
  static clearObject (obj) {
    // 把undefined和null值的属性删除
    Object.keys(obj).map(key => { if (obj[key] === undefined || obj[key] === null) { delete obj[key] } })
    return obj
  }

  /**
   * 对象深度克隆，通过递归，除函数外采用JSON深复，函数采用直接复制
   * @param obj source对象
   */
  static deepClone (obj) {
    if (this.isValid(obj)) {
      // 对象的处理方式
      if (this.isObject(obj)) {
        const clone = {}
        Object.getOwnPropertyNames(obj).forEach(key => {
          if (this.isFunction(obj[key])) {
            clone[key] = obj[key]
          } else {
            clone[key] = this.deepClone(obj[key])
          }
        })
        return clone
        // 数组的处理方法
      } else if (this.isArray(obj)) {
        let clone = []
        for (let index = 0; index < obj.length; index++) {
          if (this.isFunction(obj[index])) {
            clone[index] = obj[index]
          } else {
            clone[index] = this.deepClone(obj[index])
          }
        }
        return clone
        // 函数的处理方法
      } else if (this.isFunction(obj)) {
        return obj
      } else {
        return JSON.parse(JSON.stringify(obj))
      }
    } else {
      return obj
    }
  }

  /**
   * 根据长度构建array
   * @param length 长度
   */
  static buildArray (length) {
    return Array.from(new Array(length).keys().map(item => Number.parseInt(item)))
  }

  /**
   * 删除数组中从from到to的元素
   * @param array 原数组
   * @param from 开始
   * @param to 结束
   */
  static arrayRemove (array, from, to) {
    const rest = array.slice((to || from) + 1 || array.length)
    array.length = from < 0 ? array.length + from : from
    array.push(...rest)
  }

  /**
   * 首字母大写
   * @param str
   */
  static firstUpperCase (str) {
    return str.toLowerCase().replace(/(\s|^)[a-z]/g, function(char){
      return char.toUpperCase()
    })
  }

  /**
   * 从目前开始的时间命名
   * @param time 时间
   */
  static fromNow (time) {
    if (!this.isValid(time)) {
      return null
    }
    return this.toDate(time).fromNow()
  }

  /**
   * 把时间字符串转日期
   * @param time 时间字符串
   * @param format 格式化字符串
   */
  static toDate (time, format) {
    if (!this.isValid(time)) {
      return null
    }
    // format存在时，按照format来进行格式化
    if (format) {
      return moment(time, format)
    }
    if (this.isArray(time)) {
      return time.map(t => { return this.toDate(t) })
    } else {
      if (this.regExpNum(time, 13)) {
        return moment(time, 'x')
      } else if (this.regExpNum(time, 10)) {
        return moment.unix(time)
      } else {
        return moment(time)
      }
    }
  }

  /**
   * 格式化日期
   * @param time 时间字符串
   * @param formater 日期格式，参考moment.js的配置http://momentjs.cn/docs/#/displaying/format/
   */
  static formatDate (time, formater) {
    if (this.isValid(time)) {
      formater = formater || 'YYYY-MM-DD'
      return this.toDate(time).format(formater)
    } else {
      return ''
    }
  }

  /**
   * 格式化日期
   * @param formater 日期格式
   */
  static dateStr (formater) {
    formater = formater || 'YYYY-MM-DD'
    return moment().format(formater)
  }

  /**
   * 判断是否为length长度的数字
   * @param num 数字
   * @param length 长度
   */
  static regExpNum (num, length) {
    return new RegExp('^\\d{' + length + '}$').test(num)
  }

  /**
   * 取范围内的随机数
   * @param min 最小值
   * @param max 最大值
   */
  static randomNum (min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min
  }

  /**
   * 随机颜色
   * @param more 是否包括扩展颜色
   */
  static randomColor (more = false) {
    let colorSet = Default.colorSet
    if (more) {
      colorSet = colorSet.concat(Default.extendColorSet)
    }
    return colorSet[this.randomNum(0, colorSet.length)]
  }

  /**
   * 从树结构中过滤数据
   * @param tree 树结构
   * @param keyPath keyPath是按照值的层级进行过滤，类似于ant design树结构中返回的keyPath的反序
   * @param keyName key的字段名称，默认为id
   * @param childName child的字段名称，默认为children
   */
  static filterTreePath (tree, keyPath, keyName, childName) {
    if (!this.isValid(tree)) {
      return []
    }
    // 对属性名进行默认处理
    keyName = keyName || 'id'
    childName = childName || 'children'
    let treeNode
    if (keyPath.length === 1) {
      treeNode = tree.find(node => node[keyName] === keyPath[0])
    } else {
      keyPath.forEach((key, index) => {
        if (index === 0) {
          treeNode = this.filterTreePath(tree, [key], keyName, childName)
        } else {
          treeNode = this.filterTreePath(treeNode[childName], [key], keyName, childName)
        }
      })
    }
    return treeNode
  }

  /**
   * 从树结构中过滤数据
   * @param tree 树结构
   * @param keys keys是需要过滤出来的多个对象的Key
   * @param keyName key的字段名称，默认为id
   * @param childName child的字段名称，默认为children
   */
  static filterTree (tree, keys, keyName, childName) {
    if (!this.isValid(tree)) {
      return []
    }
    // 对属性名进行默认处理
    keyName = keyName || 'id'
    childName = childName || 'children'
    let treeNode = []
    tree.filter(node => {
      if (keys.toString().indexOf(node[keyName]) > -1) {
        treeNode.push(node)
      }
      if (this.isValid(node[childName])) {
        treeNode = treeNode.concat(this.filterTree(node[childName], keys, keyName, childName))
      }
    })
    return treeNode
  }

  /**
   * 从树结构中过滤数据
   * @param tree 树结构
   * @param key key是需要过滤出来对象包含Key
   * @param childName child的字段名称，默认为children
   */
  static filterTreeByKey (tree, key, childName) {
    if (!this.isValid(tree)) {
      return []
    }
    childName = childName || 'children'
    let treeNode = []
    tree.filter(node => {
      if (Object.keys(node).includes(key)) {
        treeNode.push(node)
      }
      if (this.isValid(node[childName])) {
        treeNode = treeNode.concat(this.filterTreeByKey(node[childName], key, childName))
      }
    })
    return treeNode
  }

  /**
   * 过滤obj中的key值
   * @param obj 过滤的对象
   * @param filters 过滤条件array
   */
  static filterObject (obj, filters) {
    const result = {}
    Object.keys(obj).filter(key => filters.includes(key)).forEach(key => {
      result[key] = obj[key]
    })
    return result
  }

  /**
   * 从list构建tree
   * @param list 原list格式数据
   * @param id id
   * @param pid pid
   * @param children 子集属性名
   */
  static buildTree (list, id, pid, children) {
    if (this.isValid(list)) {
      id = id || 'id'
      pid = pid || 'pid'
      children = children || 'children'
      const cloneList = list.concat()
      // 这里选择浅复制的原因是为了让对象之间保持关系用于list和tree的节点数据同步
      return cloneList.filter(parent => {
        const child = cloneList.filter(child => parent[id] === child[pid])
        parent[children] = child.length > 0 ? child : null
        return parent[pid] === 0 || !parent[pid]
      })
    } else {
      return []
    }
  }

  /**
   * 平铺树结构
   * @param map 平铺成map结构
   * @param tree 树数据
   * @param key 平铺成map的key字段选择
   * @param children 子集属性名
   */
  static tileTree (map = {}, tree, key = 'id', children = 'children') {
    if (this.isValid(tree)) {
      tree.forEach(node => {
        map[[node[key]]] = node
        if (this.isValid(node[children])) {
          this.tileTree(map, node[children], key, children)
        }
      })
    }
  }

  /**
   * 取list中的某个属性值构成数组
   * @param list list数据
   * @param key 属性key
   */
  static simpleList (list, key) {
    if (!this.isValid(list)) {
      return []
    }
    key = key || 'id'
    const simple = []
    list.forEach(node => simple.push(node[key]))
    return simple
  }

  /**
   * 取list中的某个属性值构成数组
   * @param tree 树形结构数据
   * @param key 属性key
   */
  static simpleTree (tree, key) {
    if (!this.isValid(tree)) {
      return []
    }
    key = key || 'id'
    const simple = []
    tree.forEach(node => simple.push(node[key]))
    if (this.isValid(tree.children)) {
      simple.concat(this.simpleTree(tree.children))
    }
    return simple
  }

  /**
   * 多个字符串连接成字符串
   * @param str 字符串数组
   */
  static concatStr (...str) {
    let url = ''
    if (this.isArray(str) && this.isValid(str)) {
      str.forEach(s => { url = url.concat('/', s) })
      // 替换多个/为一个/
      url = url.replace(/\/+/g, '/')
      return url
    } else {
      return url
    }
  }

  /**
   * 随机生成固定长度字符串-根据16进制进行生成
   * @param len 长度
   */
  static randomStr (len) {
    let str = Math.random().toString(16).slice(2)
    let strLen = str.length

    while (strLen < len) {
      str += Math.random().toString(16).slice(2)
      strLen = str.length
    }
    return str.substr(0, len)
  }

  /**
   * 补足数字位数
   * @param num 数字
   * @param length 位数
   */
  static formatNum (num, length) {
    const len = ('' + num).length
    if (length > len) {
      // 新建一个空数组，长度为所缺位数+1，利用join(0)，得到一个000...的字符串
      num = Array(length - len + 1 || 0).join(0) + num
    }
    return num
  }

  /**
   * 格式化字符串
   * @param str 被格式化的字符串
   * @param data 数据
   * @returns {string}
   */
  static formatStr (str, data) {
    if (!str || !this.isValid(data)) {
      return ''
    } else {
      if (this.isObject(data)) {
        Object.keys(data).forEach(key => {
          str = str.replace(new RegExp('{' + key + '}', 'g'), data[key])
        })
      } else if (arguments && arguments.length > 2) {
        data = Array.prototype.slice.apply(arguments).splice(1)
        data.forEach(d => {
          str = str.replace(/{s}/, d)
        })
      } else {
        str = str.replace(/{s}/g, data)
      }
      return str
    }
  }

  /**
   * 格式化钱字符串
   * @param num 钱数
   * @param splitor 是否要分隔符
   * @param prefix 前缀
   */
  static formatMoney (num, splitor = true, prefix = '￥') {
    if (splitor) {
      return num ? `${prefix} ${Number.parseFloat(num).toFixed(2)}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',') : `${prefix} 0.00`
    } else {
      return num ? `${prefix} ${Number.parseFloat(num).toFixed(2)}` : `${prefix} 0.00`
    }
  }

  /**
   * 数字前面自动补齐位数0
   * @param num 数字
   * @param length 位数
   */
  static prefixNum (num, length) {
    length = length || 3
    return (Array(length).join('0') + num).slice(-length)
  }

  /**
   * 数字升序排列
   * @param num1 数字1
   * @param num2 数字2
   */
  static sortNumAsc (num1, num2) {
    if (num1 < num2) {
      return -1
    } else if (num1 > num2) {
      return 1
    } else {
      return 0
    }
  }

  /**
   * 数字降序排列
   * @param num1 数字1
   * @param num2 数字2
   */
  static sortNumDesc (num1, num2) {
    if (num1 < num2) {
      return 1
    } else if (num1 > num2) {
      return -1
    } else {
      return 0
    }
  }

  /**
   * 下载文件
   * @param response 响应
   */
  static download (response) {
    // 提取文件名
    const fileName = response.headers['content-disposition'].match(/filename=(.*)/)[1]
    // 将二进制流转为blob
    const blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8' })
    if (typeof window.navigator.msSaveBlob !== 'undefined') {
      // 兼容IE，window.navigator.msSaveBlob：以本地方式保存文件
      window.navigator.msSaveBlob(blob, decodeURI(fileName))
    } else {
      // 创建新的URL并指向File对象或者Blob对象的地址
      const blobURL = window.URL.createObjectURL(blob)
      // 创建a标签，用于跳转至下载链接
      const tempLink = document.createElement('a')
      tempLink.style.display = 'none'
      tempLink.href = blobURL
      tempLink.setAttribute('download', decodeURI(fileName))
      // 兼容：某些浏览器不支持HTML5的download属性
      if (typeof tempLink.download === 'undefined') {
        tempLink.setAttribute('target', '_blank')
      }
      // 挂载a标签
      document.body.appendChild(tempLink)
      tempLink.click()
      document.body.removeChild(tempLink)
      // 释放blob URL地址
      window.URL.revokeObjectURL(blobURL)
    }
  }

  /**
   * base64串转成file
   * @param base64 字符串
   * @param fileName 文件名
   */
  static base64ToFile (base64, fileName) {
    const arr = base64.split(',')
    const mime = arr[0].match(/:(.*?);/)[1]
    const bstr = atob(arr[1])
    let n = bstr.length
    const u8arr = new Uint8Array(n)
    while (n--) {
      u8arr[n] = bstr.charCodeAt(n)
    }
    return new File([u8arr], fileName, { type: mime })
  }

  /**
   * file转成base64
   * @param file 文件
   * @param callback 回调
   */
  static fileToBase64 (file, callback) {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => {
      callback(reader.result)
    }
  }
}
