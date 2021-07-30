package com.atom.server.system.dao;

import com.atom.common.dao.IDao;
import com.atom.server.system.entity.SysTypeCode;

/**
 * @author zr
 * @description 系统码表dao接口
 * @date 2021/4/22
 */
public interface ISysTypeCodeDao extends IDao<SysTypeCode> {
	boolean ifExist(SysTypeCode sysTypeCode);
}
