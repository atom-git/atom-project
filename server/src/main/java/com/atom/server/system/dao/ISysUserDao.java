package com.atom.server.system.dao;

import com.atom.common.dao.IDao;
import com.atom.server.system.entity.SysUser;

import java.util.List;

/**
 * @author zr
 * @description 系统用户信息表dao接口
 * @date 2021/4/22
 */
public interface ISysUserDao extends IDao<SysUser> {

	SysUser findByAccount(String platform, String account);

	List<SysUser> searchByKeyword(String keyword);

	SysUser findByPhone(String platform, String phone);

	List<SysUser> findByDepts(Object... deptIds);
}
