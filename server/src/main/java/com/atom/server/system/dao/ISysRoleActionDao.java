package com.atom.server.system.dao;

import com.atom.common.dao.IDao;
import com.atom.server.system.entity.SysRoleAction;

import java.util.List;
import java.util.Set;

/**
 * @author zr
 * @description 系统角色资源关系表dao接口
 * @date 2021/4/22
 */
public interface ISysRoleActionDao extends IDao<SysRoleAction> {
	List<SysRoleAction> findByRoleIds(Set<Integer> roleIdSet);
}
