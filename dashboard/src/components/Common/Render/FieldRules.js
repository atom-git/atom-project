/*
 * 属性校验规则的快速设置
 */
export default class FieldRules {
  // 密码[必须包含大小写字母、数字、特殊字符，至少8位]
  static passwordPattern = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/
  // 手机+固话
  static phonePattern = /(^(0[0-9]{2,3}-)?([2-9][0-9]{6,7})+(-[0-9]{1,4})?$)|(^((\d3)|(\d{3}-))?(1[35678]\d{9})$)/
  // 手机
  static mobilePattern = /^1[3456789]\d{9}$/
  // 身份证号码
  static idcardPattern = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/

  /**
   * 根据rule类型决定是哪种规则，生成相应的message，如果required是false，则去除规则
   * @param field field 字段属性
   * @param rule 校验规则
   */
  static resolve (field, rule) {
    const label = field.label
    if (rule.required) {
      switch (rule.type) {
        case 'string':
          this.string(label, rule)
          break
        case 'number':
          // 对于number only的值提示特例
          if (field.type === 'number') {
            this.numberOnly(label, rule)
          } else {
            this.number(label, rule)
          }
          break
        case 'boolean':
          this.boolean(label, rule)
          break
        case 'method':
          this.method(label, rule)
          break
        case 'regexp':
          this.regexp(label, rule)
          break
        case 'integer':
          this.integer(label, rule)
          break
        case 'float':
          this.float(label, rule)
          break
        case 'array':
          this.array(label, rule)
          break
        case 'object':
          this.object(label, rule)
          break
        case 'enum':
          this.enum(label, rule)
          break
        case 'date':
          this.date(label, rule)
          break
        case 'url':
          this.url(label, rule)
          break
        case 'hex':
          this.hex(label, rule)
          break
        case 'email':
          this.email(label, rule)
          break
        // 密码
        case 'password':
          this.password(label, rule)
          break
        // 手机+固话
        case 'phone':
          this.phone(label, rule)
          break
        // 手机
        case 'mobile':
          this.mobile(label, rule)
          break
        // 身份证号码
        case 'idcard':
          this.idcard(label, rule)
          break
        default:
          // 如果自定义validator存在，且是function，则不自动生成message
          if (typeof rule.validator === 'function') {
            break
          } else {
            this.any(label, rule)
          }
      }
    }
  }

  // 至少1个大写字母
  static upperLetterPattern = /(?=.*?[A-Z])/
  // 至少1个小写字母
  static lowerLetterPattern = /(?=.*?[a-z])/
  static numberPattern = /(?=.*?[0-9])/
  static specialPattern = /(?=.*?[#?!@$%^&*-])/
  static lengthPattern = /.{8,}/

  /**
   * 校验密码强度等级
   * @param password 密码
   */
  static passwordLevel (password) {
    let level = 0
    if (password.length <= 5) {
      // 如果小于5位，永远是弱密码
      level = 1
    } else {
      if (this.upperLetterPattern.test(password)) {
        level++
      }
      if (this.lowerLetterPattern.test(password)) {
        level++
      }
      if (this.numberPattern.test(password)) {
        level++
      }
      if (this.specialPattern.test(password)) {
        level++
      }
      if (this.lengthPattern.test(password)) {
        level++
      }
    }
    // 1至2为1级，3至4为2级，5为3级
    return level <= 3 ? 1 : level <= 4 ? 2 : 3
  }

  /**
   * any 类型的message
   * @param label
   * @param rule
   */
  static any (label, rule) {
    rule.message = label.concat('必须填写')
  }

  /**
   * string 类型的message
   */
  static string (label, rule) {
    rule.type = rule.type || 'string'
    if (rule.len) {
      rule.message = label.concat('仅支持输入', rule.len, '个字')
    } else {
      if (rule.min && rule.max) {
        rule.message = label.concat('仅支持输入', rule.min, '至', rule.max, '个字')
      } else if (rule.min) {
        rule.message = label.concat('至少输入', rule.min, '个字')
      } else if (rule.max) {
        rule.message = label.concat('不能超过', rule.max, '个字')
      } else {
        rule.message = label.concat('必须填写')
      }
    }
  }

  /**
   * number 类型的message
   * min 值判断异常，比如min:3 21也是合格的，因此使用len来进行判断
   */
  static number (label, rule, type) {
    if (rule.len) {
      rule.message = label.concat('必须是', rule.len, type || '位数')
    } else {
      if (rule.min && rule.max) {
        rule.message = label.concat('必须是', rule.min, '至', rule.max, type || '位数')
      } else if (rule.min) {
        rule.message = label.concat('至少输入', rule.min, type || '位数')
      } else if (rule.max) {
        rule.message = label.concat('不能超过', rule.max, type || '位数')
      } else {
        rule.message = label.concat('必须是', type || '数字')
      }
    }
  }

  /**
   * numberOnly 类型的message
   * min 值判断异常，比如min:3 21也是合格的，因此使用len来进行判断
   */
  static numberOnly (label, rule) {
    if (rule.len) {
      rule.message = label.concat('只能是数字', rule.len)
    } else {
      if (rule.min && rule.max) {
        rule.message = label.concat('数值范围是', rule.min, '至', rule.max)
      } else if (rule.min) {
        rule.message = label.concat('最小值是', rule.min)
      } else if (rule.max) {
        rule.message = label.concat('最大值是', rule.max)
      } else {
        rule.message = label.concat('必须是数字')
      }
    }
  }

  /**
   * boolean 类型的message
   */
  static boolean (label, rule) {
    rule.message = label.concat('必须是是或者否')
  }

  /**
   * method 类型的message
   */
  static method (label, rule) {
    rule.message = label.concat('必须是有效的方法')
  }

  /**
   * regexp 类型的message
   */
  static regexp (label, rule) {
    rule.message = label.concat('必须是有效的正则表达式')
  }

  /**
   * integer 类型的message
   */
  static integer (label, rule) {
    this.number(label, rule, '位整数')
  }

  /**
   * float 类型的message
   */
  static float (label, rule) {
    this.number(label, rule, '位浮点数')
  }

  /**
   * array 类型的message
   */
  static array (label, rule) {
    if (rule.len) {
      rule.message = label.concat('必须选中', rule.len, '个选项')
    } else {
      if (rule.min && rule.max) {
        rule.message = label.concat('必须选中', rule.min, '至', rule.max, '个选项')
      } else if (rule.min) {
        rule.message = label.concat('至少选中', rule.min, '个选项')
      } else if (rule.max) {
        rule.message = label.concat('最多只能选中', rule.max, '个选项')
      } else {
        rule.message = label.concat('必须选择')
      }
    }
  }

  /**
   * object 类型的message
   */
  static object (label, rule) {
    rule.message = label.concat('必须是有效的对象')
  }

  /**
   * enum 类型的message
   */
  static enum (label, rule) {
    rule.message = label.concat('必须是【', rule.enum.join(','), '】中的一个')
  }

  /**
   * date 类型的message
   */
  static date (label, rule) {
    rule.message = label.concat('必须是有效的日期')
  }

  /**
   * url 类型的message
   */
  static url (label, rule) {
    rule.message = label.concat('必须是有效的url链接')
  }

  /**
   * hex 类型的message
   */
  static hex (label, rule) {
    rule.message = label.concat('必须是有效十六进制字符串')
  }

  /**
   * email 类型的message
   */
  static email (label, rule) {
    rule.message = label.concat('必须是有效的邮箱地址')
  }

  /**
   * password 密码类型的message
   */
  static password (label, rule) {
    rule.type = 'string'
    rule.pattern = this.passwordPattern
    rule.message = '请按要求设置密码【必须包含大小写字母、数字、特殊字符，长度8-10位】'
  }

  /**
   * phone 联系方式的message
   */
  static phone (label, rule) {
    rule.type = 'string'
    rule.pattern = this.phonePattern
    rule.message = '请输入正确的联系方式【手机或者固话】'
  }

  /**
   * mobile 手机号码类型的message
   */
  static mobile (label, rule) {
    rule.type = 'string'
    rule.pattern = this.mobilePattern
    rule.message = '请输入正确的手机号码'
  }

  /**
   * idcard 身份证类型的message
   */
  static idcard (label, rule) {
    rule.type = 'string'
    rule.pattern = this.idcardPattern
    rule.message = '请输入正确的身份证号码'
  }
}
