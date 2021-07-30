package com.atom.server.system.dao;

import com.atom.common.dao.IDao;
import com.atom.server.system.entity.SysRoleMenu;

import java.util.List;
import java.util.Set;

/**
 * @author zr
 * @description 系统角色菜单关系表dao接口
 * @date 2021/4/22
 */
public interface ISysRoleMenuDao extends IDao<SysRoleMenu> {
	List<SysRoleMenu> findByRoleIds(Set<Integer> roleIdSet);
}
