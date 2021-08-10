package com.atom.server.system.pojo.filter;

import cn.hutool.core.lang.Validator;
import com.atom.common.dao.Converter;
import com.atom.common.pojo.AbsEntity;
import com.atom.server.system.entity.SysType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 * @author zr
 * @description 系统数据字典类型Filter
 * @date 2020/6/23
 */
@Getter
@Setter
@ApiModel("系统数据字典类型Filter")
public class SysTypeFilter extends AbsEntity {
	@ApiModelProperty("字典名称")
	private String meanName;

	public static class FilterConverter extends Converter<DetachedCriteria, SysTypeFilter> {
		@Override
		public DetachedCriteria doForward(SysTypeFilter sysTypeFilter) {
			DetachedCriteria dc = DetachedCriteria.forClass(SysType.class);
			if (Validator.isNotEmpty(sysTypeFilter.getMeanName())) {
				dc.add(Restrictions.like("meanName", sysTypeFilter.getMeanName(), MatchMode.ANYWHERE));
			}
			return dc;
		}
	}
}
