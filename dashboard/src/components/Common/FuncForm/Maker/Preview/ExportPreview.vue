<template>
  <Dialog v-model="dialogVisible"
          title="配置预览"
          :maskClosable="false"
          :destroyOnClose="true"
          :footer="null"
          @cancel="handleCancel">
    <!-- JSON格式预览区域 -->
<!--    <JsonViewer v-model="jsonData"></JsonViewer>-->
    <JsonView :value="jsonData"></JsonView>
  </Dialog>
</template>

<script>
/**
 * 导出预览
 */
import Dialog from '@/components/Common/Dialog'
// import JsonViewer from 'vue-json-viewer'
import JsonView from '@/components/Common/JsonView'
export default {
  name: 'ExportPreview',
  components: { Dialog, JsonView },
  props: {
    // 用于控制显隐
    visible: {
      type: Boolean,
      default: false
    },
    // 组件清单
    widgets: {
      type: Array,
      required: true
    }
  },
  data () {
    return {
      // 弹窗是否显示
      dialogVisible: false,
      // 构建内部展示JsonData
      jsonData: {
        total: 25,
        limit: 10,
        skip: 0,
        links: {
          previous: undefined,
          next: function () {},
        },
        data: [
          {
            id: '5968fcad629fa84ab65a5247',
            firstname: 'Ada',
            lastname: 'Lovelace',
            awards: null,
            known: [
              'mathematics',
              'computing'
            ],
            position: {
              lat: 44.563836,
              lng: 6.495139
            },
            description: `Augusta Ada King, Countess of Lovelace (née Byron; 10 December 1815 – 27 November 1852) was an English mathematician and writer,
            chiefly known for her work on Charles Babbage's proposed mechanical general-purpose computer,
            the Analytical Engine. She was the first to recognise that the machine had applications beyond pure calculation,
            and published the first algorithm intended to be carried out by such a machine.
            As a result, she is sometimes regarded as the first to recognise the full potential of a "computing machine" and the first computer programmer.`,
            bornAt: '1815-12-10T00:00:00.000Z',
            diedAt: '1852-11-27T00:00:00.000Z'
          }, {
            id: '5968fcad629fa84ab65a5246',
            firstname: 'Grace',
            lastname: 'Hopper',
            awards: [
              'Defense Distinguished Service Medal',
              'Legion of Merit',
              'Meritorious Service Medal',
              'American Campaign Medal',
              'World War II Victory Medal',
              'National Defense Service Medal',
              'Armed Forces Reserve Medal',
              'Naval Reserve Medal',
              'Presidential Medal of Freedom'
            ],
            known: null,
            position: {
              lat: 43.614624,
              lng: 3.879995
            },
            description: `Grace Brewster Murray Hopper (née Murray; December 9, 1906 – January 1, 1992)
            was an American computer scientist and United States Navy rear admiral.
            One of the first programmers of the Harvard Mark I computer,
            she was a pioneer of computer programming who invented one of the first compiler related tools.
            She popularized the idea of machine-independent programming languages, which led to the development of COBOL,
            an early high-level programming language still in use today.`,
            bornAt: '1815-12-10T00:00:00.000Z',
            diedAt: '1852-11-27T00:00:00.000Z'
          }
        ]
      }
    }
  },
  watch: {
    // 监听外部visible变化
    visible (newValue) {
      this.dialogVisible = newValue
    },
    // 监听外部传入的组件变化
    // widgets: {
    //   deep: true,
    //   handler (newValue) {
    //     this.jsonData = {
    //       total: newValue.length,
    //       widgets: newValue
    //     }
    //   }
    // }
  },
  emits: ['maker-export-cancel'],
  methods: {
    // 响应预览取消
    handleCancel () {
      this.$emit('maker-export-cancel')
    }
  }
}
</script>
