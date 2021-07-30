package com.atom.server.system.dao.impl;

import com.atom.common.dao.AbsDao;
import com.atom.server.system.dao.ISysActionTopicDao;
import com.atom.server.system.entity.SysActionTopic;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author zr
 * @description 系统动作响应资源对应的主题dao
 * @date 2021/4/22
 */
@Repository
public class SysActionTopicDao extends AbsDao<SysActionTopic> implements ISysActionTopicDao {
	/**
	 * 根据动作资源主题保存主题数据
	 * @param topicSet 主题集
	 * @return 返回主题信息
	 */
	@Override
	public List<SysActionTopic> saveOrUpdate(Set<String> topicSet) {
		if (topicSet != null && topicSet.size() > 0) {
			DetachedCriteria dc = DetachedCriteria.forClass(SysActionTopic.class);
			dc.add(Restrictions.in("name", topicSet));
			List<SysActionTopic> sysActionTopicList = this.findByDC(dc);
			topicSet.stream().filter(topic -> {
				if (sysActionTopicList == null || sysActionTopicList.size() <= 0) {
					return true;
				} else {
					boolean exist = false;
					for (SysActionTopic sysActionTopic : sysActionTopicList) {
						if (sysActionTopic.getName().equals(topic)) {
							exist = true;
							break;
						}
					}
					return !exist;
				}
			}).forEach(topic -> {
				this.save(new SysActionTopic(topic));
			});
		}
		// 刷新缓存
		this.getCurSession().flush();
		// 查询全部的主题
		return this.findAll();
	}
}
