package com.atom.server.system.dao;

import com.atom.common.dao.IDao;
import com.atom.server.system.entity.SysType;

/**
 * @author zr
 * @description 系统数据字典类型表dao接口
 * @date 2021/4/22
 */
public interface ISysTypeDao extends IDao<SysType> {
	boolean ifExist(String meanName);
}
