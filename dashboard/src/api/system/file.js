import axios from '@/config/lib/axios'
import Default from '@/config/default'
import Utils from '@/utils'
/*
 * 系统附件api
 */
export default class file {
  // 上传文件地址
  static UPLOAD_URL = '/api/system/file/upload'

  /**
   * 删除文件
   * @param fileKey 文件key值
   */
  static delete (fileKey) {
    return axios({
      url: Utils.formatStr('/system/file/delete/{s}', fileKey),
      method: Default.HTTP_METHOD.DELETE
    })
  }

  /**
   * 下载文件
   * @param fileKey 文件key值
   */
  static download (fileKey) {
    return axios({
      url: Utils.formatStr('/system/file/download/{s}', fileKey),
      method: Default.HTTP_METHOD.GET,
      responseType: 'blob'
    })
  }
}
