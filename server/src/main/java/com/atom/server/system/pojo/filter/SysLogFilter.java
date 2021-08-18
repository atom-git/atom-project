package com.atom.server.system.pojo.filter;

import cn.hutool.core.lang.Validator;
import com.atom.common.dao.Converter;
import com.atom.common.pojo.AbsEntity;
import com.atom.server.system.entity.SysLog;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * @author liushilei
 * @description 系统日志filter
 * @date 2020/12/30
 */
@Getter
@Setter
@ApiModel("系统日志filter")
public class SysLogFilter extends AbsEntity {
	@ApiModelProperty("用户帐户")
	private String account;
	@ApiModelProperty("请求类型")
	private Integer type;
	@ApiModelProperty("请求状态")
	private Integer resultStatus;
	@ApiModelProperty("日期时间区间")
	private List<Long> logTimeRange;
	@ApiModelProperty("搜索关键字")
	private String keyword;

	public static class FilterConverter extends Converter<DetachedCriteria, SysLogFilter> {

		@Override
		public DetachedCriteria doForward(SysLogFilter sysLogFilter) {
			DetachedCriteria dc = DetachedCriteria.forClass(SysLog.class);
			if (sysLogFilter != null) {
				if (Validator.isNotEmpty(sysLogFilter.getAccount())) {
					dc.add(Restrictions.like("account", sysLogFilter.getAccount(), MatchMode.ANYWHERE));
				}
				if (Validator.isNotNull(sysLogFilter.getType())) {
					dc.add(Restrictions.eq("type", sysLogFilter.getType()));
				}
				if (Validator.isNotNull(sysLogFilter.getResultStatus())) {
					dc.add(Restrictions.eq("resultStatus", sysLogFilter.getResultStatus()));
				}
				if (Validator.isNotNull(sysLogFilter.getLogTimeRange()) && sysLogFilter.getLogTimeRange().size() >= 2) {
					dc.add(Restrictions.between("createTime", sysLogFilter.getLogTimeRange().get(0) * 1000, sysLogFilter.getLogTimeRange().get(1)  * 1000));
				}
				if (Validator.isNotEmpty(sysLogFilter.getKeyword())) {
					dc.add(Restrictions.or(
						Restrictions.like("requestUrl", sysLogFilter.getKeyword(), MatchMode.ANYWHERE),
						Restrictions.like("exception", sysLogFilter.getKeyword(), MatchMode.ANYWHERE)
					));
				}
			}
			dc.addOrder(Order.desc("id"));
			return dc;
		}
	}
}
