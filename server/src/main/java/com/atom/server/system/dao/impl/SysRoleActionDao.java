package com.atom.server.system.dao.impl;

import com.atom.common.dao.AbsDao;
import com.atom.server.system.dao.ISysRoleActionDao;
import com.atom.server.system.entity.SysRoleAction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author zr
 * @description 系统角色资源关系表dao
 * @date 2021/4/22
 */
@Repository
public class SysRoleActionDao extends AbsDao<SysRoleAction> implements ISysRoleActionDao {
	/**
	 * 根据用户角色ID列表查询资源信息，按照菜单、ID进行去重排列
	 * @param roleIdSet 用户角色IDs
	 * @return 返回角色资源列表
	 */
	@Override
	public List<SysRoleAction> findByRoleIds(Set<Integer> roleIdSet) {
		if (roleIdSet == null || roleIdSet.size() <= 0) {
			return null;
		}
		DetachedCriteria dc = DetachedCriteria.forClass(SysRoleAction.class);
		dc.add(Restrictions.in("roleId", roleIdSet));
		return this.findByDC(dc);
	}
}
