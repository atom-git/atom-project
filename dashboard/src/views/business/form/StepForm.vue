<template>
  <FormatDesc title="测试FuncDesc"
              :columns="columns"
              :span="3"
              size="small"
              :funcZone="funcZone"
              v-model="dataSource">
    <template #terminal="{ data, editable }">
      <template v-if="editable">
        <a-form-item label="交换机端口">
          <a-input v-model:value="data.swPort" placeholder="请输入交换机端口" />
        </a-form-item>
        <a-form-item label="终端品牌">
          <a-input v-model:value="data.brand" placeholder="请输入终端品牌" />
        </a-form-item>
        <a-form-item label="终端型号">
          <a-input v-model:value="data.model" placeholder="请输入终端型号" />
        </a-form-item>
        <a-form-item label="操作系统">
          <a-input v-model:value="data.system" placeholder="请输入操作系统" />
        </a-form-item>
        <a-form-item label="常用软件">
          <a-input v-model:value="data.software" placeholder="请输入常用软件" />
        </a-form-item>
        <a-form-item label="杀毒软件">
          <a-input v-model:value="data.antiVirus" placeholder="请输入杀毒软件" />
        </a-form-item>
        <a-form-item label="对应墙口">
          <a-input v-model:value="data.wallNum" placeholder="请输入对应墙口" />
        </a-form-item>
      </template>
      <template v-else>
        <p>交换机端口：{{ data.swPort || '-' }}</p>
        <p>终端品牌：{{ data.brand || '-' }}</p>
        <p>终端型号：{{ data.model || '-' }}</p>
        <p>操作系统：{{ data.system || '-' }}</p>
        <p>常用软件：{{ data.software || '-' }}</p>
        <p>杀毒软件：{{ data.antiVirus || '-' }}</p>
        <p>对应墙口：{{ data.wallNum || '-' }}</p>
      </template>
    </template>
  </FormatDesc>
</template>

<script>
/**
 * 分步表单示例
 */
import { FormatDesc } from '@/components/Advance/FuncTable'
export default {
  name: 'StepForm',
  components: { FormatDesc },
  data () {
    return {
      // 列
      columns: [
        { title: 'MAC地址', dataIndex: 'mac' },
        { title: 'IP地址', dataIndex: 'ip' },
        { title: '网络类型', dataIndex: 'netType', type: 'select', format: 'formatStatus', options: [{ value: 1, title: '互联网', status: 'success' }, { value: 2, title: '办公网', status: 'processing' }, { value: 3, title: '安防网', status: 'warning' }] },
        { title: '终端类型', dataIndex: 'terminalType', type: 'select', format: 'formatTag', options: [
            { value: 1, title: '台式电脑', color: 'green' },
            { value: 2, title: '笔记本电脑', color: 'pink' },
            { value: 3, title: '打印机', color: 'cyan' },
            { value: 4, title: 'IP电话', color: 'blue' },
            { value: 5, title: '无线路由器', color: 'red' },
            { value: 6, title: '会议终端', color: 'purple' }
          ]
        },
        { title: '终端信息', dataIndex: 'terminal', slot: 'terminal' }
      ],
      // 数据源
      dataSource: {
        mac: 'c8d3-ffad-8334',
        ip: '20.48.21.29',
        netType: 1,
        terminalType: 3,
        swPort: '2#2F:G0/0/11',
        brand: '惠普',
        model: 'm226dn',
        system: '',
        software: '',
        antiVirus: '',
        wallNum: ''
      },
      // 功能按钮
      funcZone: {
        edit: true
      }
    }
  }
}
</script>
