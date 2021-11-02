package com.atom.server.system.pojo.vo;

import com.atom.common.dao.Converter;
import com.atom.common.pojo.AbsEntity;
import com.atom.server.system.entity.SysFile;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

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
    @ApiModelProperty("文件的父节点（文件夹）id")
    private Integer parentId;
    @ApiModelProperty("文件key")
    private String fileKey;
    @ApiModelProperty("文件路径")
    private String fileUrl;
    @ApiModelProperty("创建人ID")
    private Integer creatorId;
    @ApiModelProperty("创建人名称")
    private Integer creatorName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建/修改时间")
    private Date modifyTime;

    private List<SysFileVO> children;

    public static class VOConverter extends Converter<SysFileVO, SysFile> {
        @Override
        public SysFileVO doForward(SysFile sysFile) {
            if (sysFile == null) {
                return null;
            }
            SysFileVO sysFileVO = new SysFileVO();
            BeanUtils.copyProperties(sysFile, sysFileVO);
            return sysFileVO;
        }
    }
}
