package com.atom.server.system.dao.impl;

import cn.hutool.core.lang.Validator;
import com.atom.common.dao.AbsDao;
import com.atom.common.pojo.mapper.IfValid;
import com.atom.server.system.dao.ISysUserDao;
import com.atom.server.system.entity.SysUser;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zr
 * @description 系统用户信息表dao
 * @date 2021/4/22
 */
@Repository
public class SysUserDao extends AbsDao<SysUser> implements ISysUserDao {

	/**
	 * 根据帐户查找系统用户
	 * @param platform 登录的平台，这里的平台用来区别门户还是管理后台
	 * @param account 帐户信息
	 * @return 返回系统用户
	 */
	@Override
	public SysUser findByAccount(String platform, String account) {
		if (Validator.isEmpty(account)) {
			return null;
		}
		DetachedCriteria dc = DetachedCriteria.forClass(SysUser.class);
		dc.add(Restrictions.eq("platform", platform));
		dc.add(Restrictions.eq("account", account));
		dc.add(Restrictions.eq("ifValid", IfValid.VALID.getCode()));
		return this.findOneByDC(dc);
	}

	/**
	 * 根据帐号模糊匹配用户信息
	 * @param keyword 帐号信息，或者手机号
	 * @return 匹配的用户列表
	 */
	@Override
	public List<SysUser> searchByKeyword(String keyword) {
		DetachedCriteria dc = DetachedCriteria.forClass(SysUser.class);
		if (Validator.isNotEmpty(keyword)) {
			dc.add(Restrictions.or(Restrictions.like("account", keyword, MatchMode.ANYWHERE),
					Restrictions.like("phone", keyword, MatchMode.ANYWHERE),
					Restrictions.like("name", keyword, MatchMode.ANYWHERE)));
		}
		return this.findByDC(dc);
	}

	/**
	 * 根据帐户查找系统用户
	 *
	 * @param platform 登录的平台，这里的平台用来区别门户还是管理后台
	 * @param phone 手机号码
	 * @return 返回系统用户
	 */
	@Override
	public SysUser findByPhone(String platform, String phone) {
		if (Validator.isEmpty(phone)) {
			return null;
		}
		DetachedCriteria dc = DetachedCriteria.forClass(SysUser.class);
		dc.add(Restrictions.eq("platform", platform));
		dc.add(Restrictions.eq("phone", phone));
		dc.add(Restrictions.eq("ifValid", IfValid.VALID.getCode()));
		return this.findOneByDC(dc);
	}

	/**
	 * 根据组织id查找用户
	 * @param deptIds deptId的集合
	 * @return 返回用户集合
	 */
	@Override
	public List<SysUser> findByDepts(Object... deptIds) {
		if (deptIds == null || deptIds.length <= 0) {
			return null;
		}
		DetachedCriteria dc = DetachedCriteria.forClass(SysUser.class);
		dc.add(Restrictions.in("deptId", deptIds));
		return this.findByDC(dc);
	}
}
