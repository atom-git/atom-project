/**
 * websocket stomp消息发送混入
 */
export default {
  mounted () {
    // 启动websocket连接
    this.$stomp.connect()
  },
  unmounted () {
    // 销毁websocket连接
    this.$stomp.disconnect()
  }
}
