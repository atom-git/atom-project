package com.atom.server.system.pojo.filter;

import cn.hutool.core.lang.Validator;
import com.atom.common.dao.Converter;
import com.atom.server.system.entity.SysTypeCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * @author zr
 * @description 系统数据字典Filter
 * @date 2021/2/1
 */
@Getter
@Setter
@ApiModel("系统数据字典Filter")
@NoArgsConstructor
@AllArgsConstructor
public class SysTypeCodeFilter {

	@ApiModelProperty("类型名称")
	private String typeName;
	@ApiModelProperty("类型父级编码，可以为空")
	private Integer parentId;
	@ApiModelProperty("数据字典ID")
	private Integer meanId;

	public static class FilterConverter extends Converter<DetachedCriteria, SysTypeCodeFilter> {

		@Override
		public DetachedCriteria doForward(SysTypeCodeFilter sysTypeCodeFilter) {
			DetachedCriteria dc = DetachedCriteria.forClass(SysTypeCode.class);
			if (sysTypeCodeFilter == null) {
				return dc;
			}
			if (Validator.isNotEmpty(sysTypeCodeFilter.getTypeName())) {
				dc.add(Restrictions.like("typeName", sysTypeCodeFilter.getTypeName(), MatchMode.ANYWHERE));
			}
			if (Validator.isNotNull(sysTypeCodeFilter.getParentId())) {
				// 等于0时只查询父级
				if (sysTypeCodeFilter.getParentId() == 0) {
					dc.add(Restrictions.isNull("parentId"));
				} else {
					dc.add(Restrictions.eq("parentId", sysTypeCodeFilter.getParentId()));
				}
			}
			if (Validator.isNotNull(sysTypeCodeFilter.getMeanId())) {
				dc.add(Restrictions.eq("meanId", sysTypeCodeFilter.getMeanId()));
			}
			// 增加排序
			dc.addOrder(Order.asc("meanId")).addOrder(Order.asc("parentId")).addOrder(Order.asc("typeOrder"));
			return dc;
		}
	}
}
