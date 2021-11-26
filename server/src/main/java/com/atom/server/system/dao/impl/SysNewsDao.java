package com.atom.server.system.dao.impl;

import cn.hutool.core.lang.Validator;
import com.atom.common.dao.AbsDao;
import com.atom.common.pojo.mapper.IfValid;
import com.atom.common.pojo.mapper.NewsType;
import com.atom.common.pojo.table.PageData;
import com.atom.server.system.dao.ISysNewsDao;
import com.atom.server.system.entity.SysNews;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zr
 * @description 系统提醒消息待办信息dao
 * @date 2021/4/22
 */
@Repository
public class SysNewsDao extends AbsDao<SysNews> implements ISysNewsDao {

	/**
	 * 根据用户id获取有效系统提醒消息待办信息
	 * @param userId 用户id
	 * @param newsType 系统信息类型1待办，2通知，3消息
	 * @param pageData 分页信息
	 * @return 返回消息列表
	 */
	@Override
	public List<SysNews> findValidByUser(Integer userId, NewsType newsType, PageData pageData) {
		DetachedCriteria dc = DetachedCriteria.forClass(SysNews.class);
		if (Validator.isNotNull(userId)) {
			dc.add(Restrictions.eq("toUser", userId));
		}
		if (Validator.isNotNull(newsType)) {
			dc.add(Restrictions.eq("type", newsType.getCode()));
		}
		dc.add(Restrictions.eq("ifValid", IfValid.VALID.getCode()));
		dc.addOrder(Order.desc("id"));
		return this.findPage(dc, pageData);
	}

	/**
	 * 根据用户id获取有效系统提醒消息待办未读信息
	 * @param userId 用户id
	 * @param newsType 系统信息类型1待办，2通知，3消息
	 * @param pageData 分页信息
	 * @return 返回未读消息列表
	 */
	@Override
	public List<SysNews> findUnreadByUser(Integer userId, NewsType newsType, PageData pageData) {
		DetachedCriteria dc = DetachedCriteria.forClass(SysNews.class);
		if (Validator.isNotNull(userId)) {
			dc.add(Restrictions.eq("toUser", userId));
		}
		if (Validator.isNotNull(newsType)) {
			dc.add(Restrictions.eq("type", newsType.getCode()));
		}
		// 未读消息
		dc.add(Restrictions.eq("status", 0));
		dc.add(Restrictions.eq("ifValid", IfValid.VALID.getCode()));
		dc.addOrder(Order.desc("id"));
		return this.findPage(dc, pageData);
	}

	/**
	 * 根据用户id获取有效系统提醒消息待办信息
	 * @param userId 用户id
	 * @param newsType 系统信息类型1待办，2通知，3消息
	 * @return 返回未读消息数
	 */
	@Override
	public long countUnreadByUser(Integer userId, NewsType newsType) {
		DetachedCriteria dc = DetachedCriteria.forClass(SysNews.class);
		if (Validator.isNotNull(userId)) {
			dc.add(Restrictions.eq("toUser", userId));
		}
		if (Validator.isNotNull(newsType)) {
			dc.add(Restrictions.eq("type", newsType.getCode()));
		}
		// 未读消息
		dc.add(Restrictions.eq("status", 0));
		dc.add(Restrictions.eq("ifValid", IfValid.VALID.getCode()));
		return this.countByDC(dc);
	}

	/**
	 * 根据ids查询消息列表
	 * @param newsIds 消息ids
	 * @return 消息列表
	 */
	@Override
	public List<SysNews> findByIds(Integer... newsIds) {
		DetachedCriteria dc = DetachedCriteria.forClass(SysNews.class);
		if (newsIds != null && newsIds.length > 0) {
			dc.add(Restrictions.in("id", (Object[]) newsIds));
		}
		return this.findByDC(dc);
	}
}
