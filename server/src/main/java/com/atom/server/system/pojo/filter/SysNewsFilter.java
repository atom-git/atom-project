package com.atom.server.system.pojo.filter;

import cn.hutool.core.lang.Validator;
import com.atom.common.dao.Converter;
import com.atom.common.pojo.AbsEntity;
import com.atom.server.system.dao.ISysUserDao;
import com.atom.server.system.entity.SysNews;
import com.atom.server.system.entity.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zr
 * @description 系统信息filter
 * @date 2021/8/23
 */
@Getter
@Setter
@ApiModel("系统信息filter")
public class SysNewsFilter extends AbsEntity {

	@ApiModelProperty("信息类型1待办，2通知，3消息")
	private Integer type;
	@ApiModelProperty("标题")
	private String title;
	@ApiModelProperty("用户帐号")
	private String account;
	@ApiModelProperty("消息状态：0未读 1已读")
	private Integer status;
	@ApiModelProperty("是否有效1有效，0失效")
	private Integer ifValid;

	public static class FilterConverter extends Converter<DetachedCriteria, SysNewsFilter> {
		/**
		 * 查询用户dao
		 */
		@Resource
		private ISysUserDao sysUserDao;
		@Override
		public DetachedCriteria doForward(SysNewsFilter sysNewsFilter) {
			DetachedCriteria dc = DetachedCriteria.forClass(SysNews.class);
			if (sysNewsFilter != null) {
				if (Validator.isNotNull(sysNewsFilter.getType())) {
					dc.add(Restrictions.eq("type", sysNewsFilter.getType()));
				}
				if (Validator.isNotEmpty(sysNewsFilter.getTitle())) {
					dc.add(Restrictions.like("title", sysNewsFilter.getTitle(), MatchMode.ANYWHERE));
				}
				// 查询用户
				if (Validator.isNotEmpty(sysNewsFilter.getAccount())) {
					List<SysUser> sysUserList = sysUserDao.searchByKeyword(sysNewsFilter.getAccount());
					List<Integer> userIdList = sysUserList.stream().map(SysUser::getId).collect(Collectors.toList());
					dc.add(Restrictions.in("toUser", userIdList));
				}
				if (Validator.isNotNull(sysNewsFilter.getStatus())) {
					dc.add(Restrictions.eq("status", sysNewsFilter.getStatus()));
				}
				if (Validator.isNotNull(sysNewsFilter.getIfValid())) {
					dc.add(Restrictions.eq("ifValid", sysNewsFilter.getIfValid()));
				}
			}
			// 排序
			dc.addOrder(Order.desc("id"));
			return dc;
		}
	}
}
