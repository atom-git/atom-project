package com.atom.server.system.dao.impl;

import cn.hutool.core.lang.Validator;
import com.atom.common.dao.AbsDao;
import com.atom.common.pojo.mapper.IfValid;
import com.atom.server.system.dao.ISysMenuDao;
import com.atom.server.system.entity.SysMenu;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author zr
 * @description 系统菜单信息dao
 * @date 2021/4/22
 */
@Repository
public class SysMenuDao extends AbsDao<SysMenu> implements ISysMenuDao {
	/**
	 * 根据菜单id来查询菜单列表，有效且按照level,menuOrder排序
	 * @param menuIdSet 菜单ids
	 * @return 返回菜单列表
	 */
	@Override
	public List<SysMenu> findValidByIds(Set<Integer> menuIdSet) {
		if (menuIdSet == null || menuIdSet.size() <= 0) {
			return null;
		}
		DetachedCriteria dc = DetachedCriteria.forClass(SysMenu.class);
		dc.add(Restrictions.in("id", menuIdSet));
		dc.add(Restrictions.eq("ifValid", 1));
		dc.addOrder(Order.asc("parentId")).addOrder(Order.asc("menuOrder"));
		return this.findByDC(dc);
	}

	/**
	 * 查找某个菜单的子菜单
	 * @param pids 菜单父级ids
	 * @return 返回菜单的子菜单
	 */
	@Override
	public List<SysMenu> findChildren(Object... pids) {
		return this.findChildren(null,pids);
	}

	/**
	 * 查找某个菜单的子菜单
	 * @param pids 菜单父级ids
	 * @param ifValid 是否有效
	 * @return 返回菜单的子菜单
	 */
	@Override
	public List<SysMenu> findChildren(IfValid ifValid, Object... pids) {
		if (Validator.isNull(pids) || pids.length <= 0) {
			return null;
		}
		DetachedCriteria dc = DetachedCriteria.forClass(SysMenu.class);
		dc.add(Restrictions.in("parentId", pids));
		if (Validator.isNotNull(ifValid)) {
			dc.add(Restrictions.eq("ifValid", ifValid.getCode()));
		}
		List<SysMenu> sysMenuList = this.findByDC(dc);
		// 查询子菜单的子集
		if (sysMenuList != null && sysMenuList.size() > 0) {
			Object[] childIds;
			childIds = sysMenuList.stream().map(SysMenu::getId).toArray();
			List<SysMenu> childMenuList = this.findChildren(childIds);
			if (childMenuList != null && childMenuList.size() > 0) {
				sysMenuList.addAll(childMenuList);
			}
		}
		return sysMenuList;
	}
}
