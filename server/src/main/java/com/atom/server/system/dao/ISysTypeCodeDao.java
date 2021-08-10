package com.atom.server.system.dao;

import com.atom.common.dao.IDao;
import com.atom.common.pojo.table.PageData;
import com.atom.server.system.entity.SysTypeCode;

import java.util.List;

/**
 * @author zr
 * @description 系统码表dao接口
 * @date 2021/4/22
 */
public interface ISysTypeCodeDao extends IDao<SysTypeCode> {
	List<SysTypeCode> findByType(PageData pageData, Object... sysTypeIds);

	long countByType(Object... sysTypeIds);

	boolean ifExist(SysTypeCode sysTypeCode);
}
