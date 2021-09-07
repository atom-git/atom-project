import axios from '@/config/lib/axios'
import Default from '@/config/default'
import Utils from '@/utils'
import { saveAs } from 'file-saver'

/*
 * 系统附件api
 */
export default class file {
  // 上传文件地址
  static UPLOAD_URL = '/api/system/file/upload'

  // 删除文件地址
  static DELETE_URL = '/api/system/file/operate/delete'

  /**
   * 删除文件
   * @param file 文件
   */
  static delete (file) {
    return axios({
      url: Utils.formatStr('/system/file/delete/{s}', file.key),
      method: Default.HTTP_METHOD.DELETE
    })
  }

  /**
   * 下载文件
   * @param file 文件
   */
  static download (file) {
    saveAs(file.url, file.name)
  }
}
