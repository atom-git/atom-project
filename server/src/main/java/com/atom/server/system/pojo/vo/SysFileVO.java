package com.atom.server.system.pojo.vo;

import com.atom.common.dao.Converter;
import com.atom.common.pojo.AbsEntity;
import com.atom.server.system.entity.SysFile;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @author zr
 * @description 附件显示层传输实体
 * @date 2021/4/22
 */
@Getter
@Setter
@ApiModel("附件显示层传输实体")
public class SysFileVO extends AbsEntity {
    @ApiModelProperty("主题id")
    private Integer id;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("文件类型")
    private String fileType;
    @ApiModelProperty("文件大小")
    private Long size;
    @ApiModelProperty("文件/文件夹的操作权限")
    private Integer authority;
    @ApiModelProperty("cos上的key")
    private String key;
    @ApiModelProperty("cos上的桶名称")
    private String bucketName;
    @ApiModelProperty("附件预览地址")
    private String url;
    @ApiModelProperty("文件的父节点（文件夹）id")
    private Integer parentId;
    @ApiModelProperty("创建人ID")
    private Integer creatorId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建/修改时间")
    private Date modifyTime;

    private List<SysFileVO> children;

    public static class VOConverter extends Converter<SysFileVO, SysFile> {
        @Override
        public SysFileVO doForward(SysFile sysFile) {
            return null;
        }
    }


}
