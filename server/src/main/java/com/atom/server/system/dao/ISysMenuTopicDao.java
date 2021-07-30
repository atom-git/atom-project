package com.atom.server.system.dao;

import com.atom.common.dao.IDao;
import com.atom.server.system.entity.SysMenu;
import com.atom.server.system.entity.SysMenuTopic;

import java.util.List;

/**
 * @author zr
 * @description 系统菜单对应的数据主题dao
 * @date 2021/4/22
 */
public interface ISysMenuTopicDao extends IDao<SysMenuTopic> {
	void saveOrUpdate(SysMenu sysMenu, List<Integer> topicList);
}
