package com.atom.server.system.dao.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Validator;
import com.atom.common.dao.AbsDao;
import com.atom.common.pojo.mapper.GrantType;
import com.atom.server.system.dao.ISysActionDao;
import com.atom.server.system.entity.SysAction;
import com.atom.server.system.entity.SysActionTopic;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zr
 * @description 系统api资源信息dao
 * @date 2021/4/22
 */
@Repository
public class SysActionDao extends AbsDao<SysAction> implements ISysActionDao {
	/**
	 * 根据菜单id来查询菜单列表，有效且按照menuId, id排序
	 * @param actionIdSet 资源ids
	 * @return 资源列表
	 */
	@Override
	public List<SysAction> findByIds(Set<Integer> actionIdSet) {
		if (actionIdSet == null || actionIdSet.size() <= 0) {
			return null;
		}
		DetachedCriteria dc = DetachedCriteria.forClass(SysAction.class);
		dc.add(Restrictions.in("id", actionIdSet));
		dc.addOrder(Order.asc("topicId")).addOrder(Order.asc("id"));
		return this.findByDC(dc);
	}

	/**
	 * 查找默认赋权的动作资源
	 * @return 返回动作资源列表
	 */
	@Override
	public List<SysAction> findAutos() {
		return this.findAllByField("grantType", GrantType.AUTO.getCode());
	}

	/**
	 * 根据url和操作类型查询动作资源
	 * @param url url
	 * @param actionType 动作类型
	 * @return 返回动作资源
	 */
	@Override
	public SysAction findByUrlAndAction(String url, Integer actionType) {
		if (Validator.isEmpty(url)) {
			return null;
		}
		DetachedCriteria dc = DetachedCriteria.forClass(SysAction.class);
		dc.add(Restrictions.eq("url", url));
		if (actionType != null) {
			dc.add(Restrictions.eq("type", actionType));
		}
		return this.findOneByDC(dc);
	}

	/**
	 * 批量更新动作资源
	 * @param sysActionTopicList 动作资源主题清单
	 * @param sysActionList 动作资源清单
	 * @return 返回更新后的数据
	 */
	@Override
	public List<SysAction> saveOrUpdate(List<SysActionTopic> sysActionTopicList, List<SysAction> sysActionList) {
		if (sysActionList != null && sysActionList.size() > 0) {
			Map<String, SysActionTopic> topicMap = new HashMap<>();
			if (sysActionTopicList != null && sysActionTopicList.size() > 0) {
				sysActionTopicList.forEach(sysActionTopic -> {
					topicMap.put(sysActionTopic.getName(), sysActionTopic);
				});
			}
			sysActionList.forEach(sysAction -> {
				// 查询该资源是否存在，存在时更新ActionType,GrantType,Name字段，其他不变，不存在时保存
				SysAction originAction = this.findByUrlAndAction(sysAction.getUrl(), sysAction.getType());
				if (originAction == null) {
					sysAction.setTopicId(topicMap.get(sysAction.getTopicName()).getId());
					sysAction.setCreateTime(DateUtil.date());
					this.save(sysAction);
				} else {
					originAction.setName(sysAction.getName());
					originAction.setType(sysAction.getType());
					originAction.setTopicId(topicMap.get(sysAction.getTopicName()).getId());
					originAction.setTopicName(sysAction.getTopicName());
					originAction.setGrantType(sysAction.getGrantType());
					this.update(originAction);
					BeanUtils.copyProperties(originAction, sysAction);
				}
			});
		}
		return sysActionList;
	}
}
