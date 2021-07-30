package com.atom.server.system.dao;

import com.atom.common.dao.IDao;
import com.atom.common.pojo.table.PageData;
import com.atom.server.system.entity.SysRole;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

/**
 * @author zr
 * @description 系统角色信息表dao接口
 * @date 2021/4/22
 */
public interface ISysRoleDao extends IDao<SysRole> {
	List<SysRole> findDataByPage(DetachedCriteria dc, PageData pageData);

	boolean ifNameDuplicate(SysRole sysRole);
}
