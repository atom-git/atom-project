package com.atom.server.system.dao;

import com.atom.common.dao.IDao;
import com.atom.server.system.entity.SysAction;
import com.atom.server.system.entity.SysActionTopic;

import java.util.List;
import java.util.Set;

/**
 * @author zr
 * @description 系统api资源信息dao接口
 * @date 2021/4/22
 */
public interface ISysActionDao extends IDao<SysAction> {

	List<SysAction> findByIds(Set<Integer> actionIdSet);

	List<SysAction> findAutos();

	SysAction findByUrlAndAction(String url, Integer actionType);

	List<SysAction> saveOrUpdate(List<SysActionTopic> sysActionTopicList, List<SysAction> sysActionList);

}
