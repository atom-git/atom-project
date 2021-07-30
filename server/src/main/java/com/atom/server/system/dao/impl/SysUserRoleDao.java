package com.atom.server.system.dao.impl;

import com.atom.common.dao.AbsDao;
import com.atom.server.system.dao.ISysUserRoleDao;
import com.atom.server.system.entity.SysUserRole;
import org.springframework.stereotype.Repository;

/**
 * @author zr
 * @description 系统用户角色关系表dao
 * @date 2021/4/22
 */
@Repository
public class SysUserRoleDao extends AbsDao<SysUserRole> implements ISysUserRoleDao {
}
