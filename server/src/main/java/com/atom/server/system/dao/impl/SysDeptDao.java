package com.atom.server.system.dao.impl;

import com.atom.common.dao.AbsDao;
import com.atom.server.system.dao.ISysDeptDao;
import com.atom.server.system.entity.SysDept;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zr
 * @description 系统组织机构dao
 * @date 2021/4/22
 */
@Repository
public class SysDeptDao extends AbsDao<SysDept> implements ISysDeptDao {
	/**
	 * 根据id查找有效的组织机构
	 * @param deptId 组织id
	 * @return 返回有效的组织
	 */
	@Override
	public SysDept findValidById(Integer deptId) {
		if (deptId == null) {
			return null;
		}
		DetachedCriteria dc = DetachedCriteria.forClass(SysDept.class);
		dc.add(Restrictions.eq("id", deptId));
		dc.add(Restrictions.eq("ifValid", 1));
		return this.findOneByDC(dc);
	}

	/**
	 * 根据父级id找到子集组织
	 * @param deptId 父级id
	 * @return 返回子集列表
	 */
	@Override
	public List<SysDept> findChildren(Integer deptId) {
		if (deptId == null) {
			return null;
		}
		DetachedCriteria dc = DetachedCriteria.forClass(SysDept.class);
		dc.add(Restrictions.eq("deptParent", deptId));
		return this.findByDC(dc);
	}
}
