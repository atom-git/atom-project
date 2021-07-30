package com.atom.server.system.dao.impl;

import com.atom.common.dao.AbsDao;
import com.atom.common.pojo.table.OrderData;
import com.atom.common.pojo.table.PageData;
import com.atom.server.system.dao.ISysRoleDao;
import com.atom.server.system.entity.SysRole;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zr
 * @description 系统角色信息表dao
 * @date 2021/4/22
 */
@Repository
public class SysRoleDao extends AbsDao<SysRole> implements ISysRoleDao {

	/**
	 * 根据离线查询条件和分页信息查询
	 * @param dc 离线查询条件
	 * @param pageData 分页信息
	 * @return 分页信息
	 */
	@Override
	public List<SysRole> findDataByPage(DetachedCriteria dc, PageData pageData) {
		// 有排序条件时增加排序条件
		this.addOrder(dc, pageData.getOrderData(), OrderData.DEFAULT_ORDER);
		// 下载和查询执行不同的方法
		if (pageData.getDownload()) {
			return this.download(dc);
		} else {
			return this.findPage(dc, pageData);
		}
	}

	/**
	 * 判断角色名称是否有重复
	 * @param sysRole 编辑的角色
	 * @return 返回角色名称是否重复
	 */
	@Override
	public boolean ifNameDuplicate(SysRole sysRole) {
		DetachedCriteria dc = DetachedCriteria.forClass(SysRole.class);
		if (sysRole.getId() != null) {
			dc.add(Restrictions.eq("roleName", sysRole.getRoleName()));
			dc.add(Restrictions.ne("id", sysRole.getId()));
		} else {
			dc.add(Restrictions.eq("roleName", sysRole.getRoleName()));
		}
		return this.findOneByDC(dc) != null;
	}
}
