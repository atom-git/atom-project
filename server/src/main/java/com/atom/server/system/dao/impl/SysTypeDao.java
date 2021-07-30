package com.atom.server.system.dao.impl;

import com.atom.common.dao.AbsDao;
import com.atom.server.system.dao.ISysTypeDao;
import com.atom.server.system.entity.SysType;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @author zr
 * @description 系统数据字典类型表dao
 * @date 2021/4/22
 */
@Repository
public class SysTypeDao extends AbsDao<SysType> implements ISysTypeDao {

	/**
	 * 判断类型是否已经存在
	 * @param meanName 数据字典名称
	 * @return 返回是否存在
	 */
	@Override
	public boolean ifExist(String meanName) {
		DetachedCriteria dc = DetachedCriteria.forClass(SysType.class);
		dc.add(Restrictions.eq("meanName", meanName));
		return this.findOneByDC(dc) != null;
	}
}
