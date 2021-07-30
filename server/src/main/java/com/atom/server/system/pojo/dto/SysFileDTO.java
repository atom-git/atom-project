package com.atom.server.system.pojo.dto;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.atom.common.dao.Converter;
import com.atom.common.pojo.AbsEntity;
import com.atom.common.security.SessionUser;
import com.atom.server.system.entity.SysFile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author tansd
 * @description 附件新增/更新传输实体
 * @date 2021/1/9
 */
@Getter
@Setter
@ApiModel("附件新增/更新传输实体")
public class SysFileDTO extends AbsEntity {

    @ApiModelProperty("主键ID")
    private Integer id;
    @NotEmpty
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("文件类型")
    private String fileType;
    @ApiModelProperty("文件大小")
    private Long size;
    @NotEmpty
    @ApiModelProperty("文件/文件夹的操作权限")
    private Integer authority;
    @ApiModelProperty("cos上的key")
    private String key;
    @ApiModelProperty("cos上的桶名称")
    private String bucketName;
    @NotEmpty
    @ApiModelProperty("文件的父节点（文件夹）id")
    private Integer parentId;
    @ApiModelProperty("创建人ID")
    private Integer creatorId;
    @ApiModelProperty("要复制/移动/下载的文件或目录")
    private List<Integer> sourceIds;
    @ApiModelProperty("复制/移动的目标目录id")
    private Integer targetId;

    public static class DTOConverter extends Converter<SysFile, SysFileDTO> {
        public SysFile doForward(SysFileDTO sysFileDTO, SessionUser sessionUser) {
            if (sysFileDTO == null) {
                return null;
            }
            SysFile sysFile = new SysFile();
            BeanUtil.copyProperties(sysFileDTO, sysFile);
            sysFile.setCreatorId(sessionUser.getId());
            sysFile.setModifyTime(DateUtil.date());
            return sysFile;
        }
    }
}
