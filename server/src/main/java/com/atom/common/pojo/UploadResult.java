package com.atom.common.pojo;

import com.atom.server.system.entity.SysFile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author zr
 * @description 文件上传结果传输实体
 * @date 2021/1/10
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UploadResult {

    /**
     * 是否上传成功
     */
    private Boolean success;

    /**
     * 如果失败，错误代码
     */
    private String errCode;

    /**
     * 如果失败，失败信息
     */
    private String errMsg;

    /**
     * 上传进度
     */
    private Double progress;

    /**
     * 文件key
     */
    private Long key;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 文件预览地址
     */
    private String url;

    /**
     * 创建失败的请求响应
     * @param errCode 失败code
     * @param errMsg 失败原因
     */
    public UploadResult(String errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    /**
     * 创建上传成功的返回
     * @param sysFile 系统附件对象
     * @return 上传结果
     */
    public static UploadResult success (SysFile sysFile) {
        UploadResult uploadResult = new UploadResult();
        uploadResult.setSuccess(true);
        uploadResult.setKey(sysFile.getId());
        uploadResult.setName(sysFile.getName());
        uploadResult.setType(sysFile.getFileType());
        uploadResult.setUrl(sysFile.getFileKey());
        return uploadResult;
    }

    /**
     * 创建上传失败的返回
     * @param errCode 失败code
     * @param errMsg 失败原因
     * @return 上传结果
     */
    public static UploadResult error (String errCode, String errMsg) {
        return new UploadResult(errCode, errMsg);
    }
}
