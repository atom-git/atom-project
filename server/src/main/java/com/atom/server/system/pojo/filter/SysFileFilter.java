package com.atom.server.system.pojo.filter;

import cn.hutool.core.lang.Validator;
import com.atom.common.dao.Converter;
import com.atom.common.pojo.AbsEntity;
import com.atom.server.system.entity.SysFile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 * @author tansd
 * @description 附件管理 filter
 * @date 2021/1/9
 */
@Getter
@Setter
@ApiModel("附件管理filter")
public class SysFileFilter extends AbsEntity {

    @ApiModelProperty("文件/文件夹名称")
    private String name;
    @ApiModelProperty("文件类型")
    private String fileType;
    @ApiModelProperty("父节点ID")
    private Integer parentId;
    @ApiModelProperty("创建人ID")
    private Integer creatorId;

    public static class FilterConverter extends Converter<DetachedCriteria, SysFileFilter> {

        @Override
        public DetachedCriteria doForward(SysFileFilter sysFileFilter) {
            return DetachedCriteria.forClass(SysFile.class);
        }
    }

}
