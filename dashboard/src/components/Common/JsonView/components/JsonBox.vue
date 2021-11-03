<script>
import JsonString from './JsonString'
import JsonUndefined from './JsonUndefined'
import JsonNumber from './JsonNumber'
import JsonBoolean from './JsonBoolean'
import JsonObject from './JsonObject'
import JsonArray from './JsonArray'
import JsonFunction from './JsonFunction'
import JsonDate from './JsonDate'
import { h } from "vue"
export default {
  name: 'JsonBox',
  inject: ['expandDepth'],
  props: {
    value: {
      type: [Object, Array, String, Number, Boolean, Function, Date],
      default: null
    },
    keyName: {
      type: String,
      default: ''
    },
    sort: Boolean,
    depth: {
      type: Number,
      default: 0
    },
    previewMode: Boolean,
  },
  data() {
    return {
      expand: true
    }
  },
  mounted() {
    this.expand = this.previewMode || (this.depth < this.expandDepth)
  },
  methods: {
    toggle() {
      this.expand = !this.expand

      try {
        this.$el.dispatchEvent(new Event('resized'))
      } catch (e) {
        // handle IE not supporting Event constructor
        var evt = document.createEvent('Event')
        evt.initEvent('resized', true, false)
        this.$el.dispatchEvent(evt)
      }
    }
  },
  render () {
    let elements = []
    let dataType

    if (this.value === null || this.value === undefined) {
      dataType = JsonUndefined
    } else if (Array.isArray(this.value)) {
      dataType = JsonArray
    } else if (Object.prototype.toString.call(this.value) === '[object Date]') {
      dataType = JsonDate
    } else if (typeof this.value === 'object') {
      dataType = JsonObject
    } else if (typeof this.value === 'number') {
      dataType = JsonNumber
    } else if (typeof this.value === 'string') {
      dataType = JsonString
    } else if (typeof this.value === 'boolean') {
      dataType = JsonBoolean
    } else if (typeof this.value === 'function') {
      dataType = JsonFunction
    }
    const complex = this.keyName && (this.value && (Array.isArray(this.value) || (typeof this.value === 'object' && Object.prototype.toString.call(this.value) !== '[object Date]')))

    if (!this.previewMode && complex) {
      elements.push(h('IconFont', {
        type: this.expand ? 'CaretRightOutlined' : 'CaretDownOutlined',
        // class: {
        //   'jv-toggle': true,
        //   open: !!this.expand
        // },
        onClick:this.toggle
      }))
    }

    if (this.keyName) {
      elements.push(h('span', {
        class: {
          'jv-key': true
        },
        innerText: `${this.keyName}:`
      }))
    }

    elements.push(h(dataType, {
      class: {
        'jv-push': true
      },
      jsonValue: this.value,
      keyName: this.keyName,
      sort: this.sort,
      depth: this.depth,
      expand: this.expand,
      previewMode: this.previewMode,
      'onUpdate:expand': value => {
        this.expand = value
      }
    }))

    return h('div', {
      class: {
        'jv-node': true,
        'jv-key-node': Boolean(this.keyName) && !complex,
        'toggle': !this.previewMode && complex
      }
    }, elements)
  }
}
</script>

<style lang="less">
.jv-node {
  position: relative;
}
.jv-node:after {
  content: ",";
}
.jv-node:last-of-type:after {
  content: "";
}
.jv-node.toggle {
  margin-left: 13px !important;
}
.jv-node .jv-node {
  margin-left: 25px;
}
</style>
