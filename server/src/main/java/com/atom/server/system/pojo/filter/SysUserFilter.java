package com.atom.server.system.pojo.filter;

import cn.hutool.core.lang.Validator;
import com.atom.common.dao.Converter;
import com.atom.server.system.entity.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 * @author zr
 * @description 系统用户filter
 * @date 2021/4/23
 */
@Getter
@Setter
@ApiModel("系统用户filter")
public class SysUserFilter {
	@ApiModelProperty("帐号")
	private String account;
	@ApiModelProperty("电话")
	private String phone;
	@ApiModelProperty("名称")
	private String name;
	@ApiModelProperty("组织部门ID")
	private Integer deptId;
	@ApiModelProperty("是否启用 0：禁用 1：启用")
	private Integer ifValid;

	public static class FilterConverter extends Converter<DetachedCriteria, SysUserFilter> {

		@Override
		public DetachedCriteria doForward(SysUserFilter sysUserFilter) {
			DetachedCriteria dc = DetachedCriteria.forClass(SysUser.class);
			if (sysUserFilter != null) {
				if (Validator.isNotEmpty(sysUserFilter.getAccount())) {
					dc.add(Restrictions.like("account", sysUserFilter.getAccount(), MatchMode.ANYWHERE));
				}
				if (Validator.isNotEmpty(sysUserFilter.getPhone())) {
					dc.add(Restrictions.like("phone", sysUserFilter.getPhone(), MatchMode.ANYWHERE));
				}
				if (Validator.isNotEmpty(sysUserFilter.getName())) {
					dc.add(Restrictions.like("name", sysUserFilter.getName(), MatchMode.ANYWHERE));
				}

				if (Validator.isNotNull(sysUserFilter.getDeptId())) {
					dc.add(Restrictions.eq("deptId", sysUserFilter.getDeptId()));
				}
				if (Validator.isNotNull(sysUserFilter.getIfValid())) {
					dc.add(Restrictions.eq("ifValid", sysUserFilter.getIfValid()));
				}
			}
			return dc;
		}
	}
}
