package com.atom.common.pojo;

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
     * 文件名称
     */
    private String name;

    /**
     * 文件类型
     */
    private String type;

    /**
     * cos上的key
     */
    private String key;

    /**
     * 文件预览地址
     */
    private String url;

    /**
     * 关联表主键id
     */
    private Integer uid;
}
