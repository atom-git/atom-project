package com.atom.server.system.service;

import com.atom.server.system.pojo.dto.SysPermissionDTO;
import com.atom.server.system.pojo.dto.SysRoleDTO;
import com.atom.server.system.pojo.vo.SysRoleMenuVO;
import com.atom.server.system.pojo.vo.SysRoleVO;

import java.util.List;

/**
 * @author zr
 * @description 系统角色管理服务接口
 * @date 2021/4/23
 */
public interface ISysRoleService {

	List<SysRoleVO> list();

	void saveOrUpdate(SysRoleDTO sysRoleDTO);

	void delete(Integer roleId);

	SysRoleMenuVO permission(Integer roleId);

	void updatePermission(Integer roleId, SysPermissionDTO sysPermissionDTO);
}
