/*
 * 系统提醒消息待办api
 */
import axios from '@/config/lib/axios'
import Default from '@/config/default'
import Utils from '@/utils'

export default class news {
  /**
   * 订阅时拉取用户提醒消息及待办
   */
  static STOMP_FETCH_NEWS = '/stomp/fetch/news'

  /**
   * 标记消息已读
   * @param newsIds 消息ids
   */
  static read (...newsIds) {
    if (Utils.isValid(newsIds)) {
      if (newsIds.length === 1) {
        return axios({
          url: Utils.formatStr('/system/news/read/{s}', newsIds[0]),
          method: Default.HTTP_METHOD_PUT
        })
      } else {
        return axios({
          url: '/system/news/read',
          method: Default.HTTP_METHOD_PUT,
          data: newsIds
        })
      }
    }
  }

  /**
   * 标记消息未读
   * @param newsId 消息ids
   */
  static unread (newsId) {
    return axios({
      url: Utils.formatStr('/system/news/unread/{s}', newsId),
      method: Default.HTTP_METHOD_PUT
    })
  }

  /**
   * 删除消息
   * @param newsId 消息id
   */
  static delete (newsId) {
    return axios({
      url: Utils.formatStr('/system/news/delete/{s}', newsId),
      method: Default.HTTP_METHOD_DELETE
    })
  }
}