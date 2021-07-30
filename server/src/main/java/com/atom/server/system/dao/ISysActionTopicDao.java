package com.atom.server.system.dao;

import com.atom.common.dao.IDao;
import com.atom.server.system.entity.SysActionTopic;

import java.util.List;
import java.util.Set;

/**
 * @author zr
 * @description 系统动作响应资源对应的主题dao
 * @date 2021/4/22
 */
public interface ISysActionTopicDao extends IDao<SysActionTopic> {

	List<SysActionTopic> saveOrUpdate(Set<String> topicSet);
}
