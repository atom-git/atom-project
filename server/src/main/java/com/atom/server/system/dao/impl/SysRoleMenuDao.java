package com.atom.server.system.dao.impl;

import com.atom.common.dao.AbsDao;
import com.atom.server.system.dao.ISysRoleMenuDao;
import com.atom.server.system.entity.SysRoleMenu;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author zr
 * @description 系统角色菜单关系表dao
 * @date 2021/4/22
 */
@Repository
public class SysRoleMenuDao extends AbsDao<SysRoleMenu> implements ISysRoleMenuDao {
	/**
	 * 根据用户角色ID列表查询菜单信息，按照菜单层级和排序进行去重排列
	 * @param roleIdSet 用户角色ids
	 * @return 返回角色菜单列表
	 */
	@Override
	public List<SysRoleMenu> findByRoleIds(Set<Integer> roleIdSet) {
		if (roleIdSet == null || roleIdSet.size() <= 0) {
			return null;
		}
		DetachedCriteria dc = DetachedCriteria.forClass(SysRoleMenu.class);
		dc.add(Restrictions.in("roleId", roleIdSet));
		return this.findByDC(dc);
	}
}
