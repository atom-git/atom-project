package com.atom.server.system.dao.impl;

import com.atom.common.dao.AbsDao;
import com.atom.server.system.dao.ISysMenuTopicDao;
import com.atom.server.system.entity.SysMenu;
import com.atom.server.system.entity.SysMenuTopic;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zr
 * @description 系统菜单对应的数据主题dao
 * @date 2021/4/22
 */
@Repository
public class SysMenuTopicDao extends AbsDao<SysMenuTopic> implements ISysMenuTopicDao {
	/**
	 * 保存或者更新菜单的动作资源主题数据
	 * @param sysMenu 菜单信息
	 * @param topicList 动作资源主题列表
	 */
	@Override
	public void saveOrUpdate(SysMenu sysMenu, List<Integer> topicList) {
		List<SysMenuTopic> originMenuTopicList = this.findAllByField("menuId", sysMenu.getId());
		if (originMenuTopicList != null && originMenuTopicList.size() > 0) {
			this.deleteAll(originMenuTopicList);
		}
		if (topicList != null && topicList.size() > 0) {
			List<SysMenuTopic> menuTopicList = new ArrayList<>();
			topicList.forEach(topic -> {
				SysMenuTopic sysMenuTopic = new SysMenuTopic();
				sysMenuTopic.setMenuId(sysMenu.getId());
				sysMenuTopic.setTopicId(topic);
				menuTopicList.add(sysMenuTopic);
			});
			this.save(menuTopicList);
		}
	}
}
