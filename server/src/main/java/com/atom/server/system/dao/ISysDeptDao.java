package com.atom.server.system.dao;

import com.atom.common.dao.IDao;
import com.atom.server.system.entity.SysDept;

import java.util.List;

/**
 * @author zr
 * @description 系统组织机构dao接口
 * @date 2021/4/22
 */
public interface ISysDeptDao extends IDao<SysDept> {
	SysDept findValidById(Integer deptId);

	List<SysDept> findChildren(Integer deptId);
}
