package com.atom.server.system.pojo.filter;

import cn.hutool.core.lang.Validator;
import com.atom.common.dao.Converter;
import com.atom.common.pojo.AbsEntity;
import com.atom.server.system.entity.SysAction;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 * @author zr
 * @description 系统动作filter
 * @date 2021/4/23
 */
@Getter
@Setter
@ApiModel("系统动作filter")
public class SysActionFilter extends AbsEntity {

	@ApiModelProperty("动作资源主题ID")
	private Integer topicId;
	@ApiModelProperty("动作资源名称")
	private String name;
	@ApiModelProperty("资源类型:1查询，2新增，3修改，4删除")
	private Integer type;
	@ApiModelProperty("授权类型 0：手动 1：自动")
	private Integer grantType;

	public static class FilterConverter extends Converter<DetachedCriteria, SysActionFilter> {
		@Override
		public DetachedCriteria doForward(SysActionFilter sysActionFilter) {
			DetachedCriteria dc = DetachedCriteria.forClass(SysAction.class);
			if (Validator.isNotNull(sysActionFilter.getTopicId())) {
				dc.add(Restrictions.eq("topicId", sysActionFilter.getTopicId()));
			}
			if (Validator.isNotEmpty(sysActionFilter.getName())) {
				dc.add(Restrictions.like("name", sysActionFilter.getName(), MatchMode.ANYWHERE));
			}
			if (Validator.isNotNull(sysActionFilter.getType())) {
				dc.add(Restrictions.eq("type", sysActionFilter.getType()));
			}
			if (Validator.isNotNull(sysActionFilter.getGrantType())) {
				dc.add(Restrictions.eq("grantType", sysActionFilter.getGrantType()));
			}
			return dc;
		}
	}
}
