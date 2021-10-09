<template>
  <FormatDesc ref="formatDesc"
              v-bind="$attrs"
              v-model="dataSource"
              :columns="columns"
              :loading="loading"
              @desc-func-action="handleFuncAction">
    <!-- 把外部传入的form slot传入内部 -->
    <template v-for="slotName in Object.keys($slots)" #[slotName]="props">
      <slot :name="slotName" v-bind="props"></slot>
    </template>
  </FormatDesc>
</template>

<script>
import FormatDesc from './FormatDesc'
export default {
  name: 'FuncDesc',
  components: { FormatDesc },
  props: {
    // 数据请求url
    apiUrl: {
      type: String,
      required: true
    },
    // 参考FormTable的配置
    columns: {
      type: Array,
      required: true
    },
    // 外部扩展的参数
    extendParams: {
      type: Object,
      default: () => ({})
    }
  },
  data () {
    return {
      // 内部绑定的数据
      dataSource: {},
      // 数据loading
      loading: false
    }
  },
  watch: {
    // 监听到请求的变化变重新加载数据
    apiUrl () {
      this.loadDataSource()
    },
    // 监听扩展参数的变化重新加载数据
    extendParams: {
      handler () {
        this.loadDataSource()
      },
      deep: true
    }
  },
  emits: ['desc-func-action'],
  methods: {
    // 响应数据的加载
    loadDataSource () {
      return new Promise(resolve => {
        if (!this.$utils.isValid(this.apiUrl)) {
          console.warn('请提供有效的apiUrl')
          return
        }
        this.loading = true
        this.$http.get(this.apiUrl, { params: this.extendParams }).then(response => {
          this.$emit('desc-data-load', response)
          this.dataSource = response ? response.data : {}
          resolve(response)
        }).finally(() => { this.loading = false })
      })
    },
    // 响应功能按钮
    handleFuncAction (action) {
      if (action.name === 'submit') {
        // apiUrl必须存在且格式合规
        if (this.$utils.isValid(action.apiUrl)) {
          this.$http.put(action.apiUrl, this.dataSource).then(() => {
            this.$message.success('数据编辑成功！')
            this.$refs.formatDesc.toggleEditable()
          })
        } else {
          this.$message.error('编辑功能action必须配置apiUrl')
        }
      } else {
        this.$emit('desc-func-action', action)
      }
    }
  }
}
</script>
