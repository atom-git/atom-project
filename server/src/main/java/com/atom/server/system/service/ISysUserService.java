package com.atom.server.system.service;

import com.atom.common.pojo.table.PageData;
import com.atom.common.pojo.table.TableData;
import com.atom.common.security.SessionUser;
import com.atom.server.system.pojo.dto.AppConfigDTO;
import com.atom.server.system.pojo.dto.SysUserDTO;
import com.atom.server.system.pojo.filter.SysUserFilter;
import com.atom.server.system.pojo.vo.SysUserRoleVO;
import com.atom.server.system.pojo.vo.SysUserVO;

import javax.servlet.http.HttpServletResponse;

/**
 * @author zr
 * @description 系统用户管理服务接口
 * @date 2021/4/23
 */
public interface ISysUserService {

	void updatePassword(Integer userId, SysUserDTO sysUserDTO);

	void updateHead(Integer userId, SysUserDTO sysUserDTO);

	TableData<SysUserVO> list(SysUserFilter sysUserFilter, PageData pageData, HttpServletResponse response);

	String saveOrUpdate(SysUserDTO sysUserDTO);

	String resetPassword(Integer userId);

	void toggleValid(Integer userId);

	SysUserRoleVO roleList(Integer userId);

	void updateRole(Integer userId, Integer[] userRoleList);

	void updateAppConfig(SessionUser sessionUser, AppConfigDTO appConfigDTO);
}
