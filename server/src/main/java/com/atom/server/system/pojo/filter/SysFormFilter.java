package com.atom.server.system.pojo.filter;

import cn.hutool.core.lang.Validator;
import com.atom.common.dao.Converter;
import com.atom.common.pojo.AbsEntity;
import com.atom.server.system.entity.SysForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * @author zr
 * @description 系统自定义表单filter
 * @date 2021/11/22
 */
@Getter
@Setter
@ApiModel("系统自定义表单filter")
public class SysFormFilter extends AbsEntity {

	@ApiModelProperty("表单标题")
	private String title;
	@ApiModelProperty("是否启用 0：禁用 1：启用")
	private Integer ifValid;

	public static class FilterConverter extends Converter<DetachedCriteria, SysFormFilter> {

		@Override
		public DetachedCriteria doForward(SysFormFilter sysFormFilter) {
			DetachedCriteria dc = DetachedCriteria.forClass(SysForm.class);
			if (sysFormFilter != null) {
				if (Validator.isNotEmpty(sysFormFilter.getTitle())) {
					dc.add(Restrictions.like("title", sysFormFilter.getTitle(), MatchMode.ANYWHERE));
				}
				if (Validator.isNotNull(sysFormFilter.getIfValid())) {
					dc.add(Restrictions.eq("ifValid", sysFormFilter.getIfValid()));
				}
			}
			dc.addOrder(Order.desc("modifyTime"));
			return dc;
		}
	}
}
