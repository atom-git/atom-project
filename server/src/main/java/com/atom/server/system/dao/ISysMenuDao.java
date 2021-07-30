package com.atom.server.system.dao;

import com.atom.common.dao.IDao;
import com.atom.common.pojo.mapper.IfValid;
import com.atom.server.system.entity.SysMenu;

import java.util.List;
import java.util.Set;

/**
 * @author zr
 * @description 系统菜单信息dao接口
 * @date 2021/4/22
 */
public interface ISysMenuDao extends IDao<SysMenu> {
	List<SysMenu> findValidByIds(Set<Integer> menuIdSet);

	List<SysMenu> findChildren(Object... pids);

	List<SysMenu> findChildren(IfValid ifValid, Object... pids);
}
