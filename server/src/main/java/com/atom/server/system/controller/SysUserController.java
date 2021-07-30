package com.atom.server.system.controller;

import com.atom.common.pojo.annotation.Permission;
import com.atom.common.pojo.http.RestResponse;
import com.atom.common.pojo.mapper.ActionType;
import com.atom.common.pojo.mapper.GrantType;
import com.atom.common.pojo.table.PageData;
import com.atom.common.pojo.table.TableData;
import com.atom.common.security.SessionUser;
import com.atom.server.system.pojo.dto.SysUserDTO;
import com.atom.server.system.pojo.filter.SysUserFilter;
import com.atom.server.system.pojo.vo.SysUserRoleVO;
import com.atom.server.system.pojo.vo.SysUserVO;
import com.atom.server.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zr
 * @description 系统用户管理，包括portal(dept默认为1000)，dashboard(dept默认为1001)两类用户
 * 公共api:个人信息、个人密码修改、修改头像
 * portal:暂无
 * dashboard:列表（按照dept查询）、编辑（新增，修改）、重置密码、禁用/启用、用户角色列表
 * @date 2021/4/23
 */
@RestController
@RequestMapping("/system/user")
@Api("系统用户管理")
@Permission
public class SysUserController {

	/**
	 * 用户管理服务
	 */
	@Resource
	private ISysUserService sysUserService;

	/**
	 * 取用户信息SessionUser
	 * @param sessionUser 注入的SessionUser
	 * @return 返回SessionUser
	 */
	@GetMapping("me")
	@ApiOperation("取用户信息SessionUser")
	@Permission(actionType = ActionType.Q, grantType = GrantType.AUTO)
	public RestResponse<SessionUser> me(@ApiIgnore SessionUser sessionUser) {
		return RestResponse.success(sessionUser);
	}

	/**
	 * 更新用户密码
	 * @param userId 用户ID
	 * @param sysUserDTO 用户信息实体
	 * @return 返回是否成功
	 */
	@PostMapping("update/{userId}/password")
	@ApiOperation("更新用户密码")
	@Permission(actionType = ActionType.E, grantType = GrantType.AUTO)
	public RestResponse<?> updatePassword(@PathVariable Integer userId, @RequestBody SysUserDTO sysUserDTO) {
		sysUserService.updatePassword(userId, sysUserDTO);
		return RestResponse.success();
	}

	/**
	 * 更新用户头像
	 * @param userId 用户ID
	 * @param sysUserDTO 用户信息实体
	 * @return 返回是否成功
	 */
	@PostMapping("update/{userId}/head")
	@ApiOperation("更新用户头像")
	@Permission(actionType = ActionType.E, grantType = GrantType.AUTO)
	public RestResponse<?> updateHead(@PathVariable Integer userId, @RequestBody SysUserDTO sysUserDTO) {
		sysUserService.updateHead(userId, sysUserDTO);
		return RestResponse.success();
	}

	/**
	 * 获取系统用户数据表，下载的量由前端进行控制
	 * @param sysUserFilter 系统用户Filter对象
	 * @param pageData 分页信息
	 * @param response 请求响应
	 * @return 返回当前页的用户数据列表
	 */
	@GetMapping("list")
	@ApiOperation("获取系统用户数据表")
	@Permission(actionType = ActionType.Q, grantType = GrantType.MANUAL)
	public RestResponse<TableData<SysUserVO>> list(SysUserFilter sysUserFilter, PageData pageData, HttpServletResponse response) {
		TableData<SysUserVO> tableData = sysUserService.list(sysUserFilter, pageData, response);
		// 下载时必须返回空，否则会继续做类型转换为报错
		return RestResponse.success(tableData);
	}

	/**
	 * 新增或者编辑用户
	 * @param sysUserDTO 用户传输dto
	 * @return 新增时返回用户默认密码，修改时返回为空
	 */
	@PostMapping("update")
	@ApiOperation("新增或者编辑用户信息")
	@Permission(actionType = {ActionType.N, ActionType.E}, grantType = GrantType.MANUAL)
	public RestResponse<?> update(@RequestBody SysUserDTO sysUserDTO) {
		return RestResponse.success(sysUserService.saveOrUpdate(sysUserDTO));
	}

	/**
	 * 重置用户密码
	 * @param userId 用户id
	 * @return 返回是否重置成功
	 */
	@PostMapping("reset/{userId}/password")
	@ApiOperation("重置用户密码")
	@Permission(actionType = ActionType.E, grantType = GrantType.MANUAL)
	public RestResponse<String> resetPassword(@PathVariable Integer userId) {
		String password = sysUserService.resetPassword(userId);
		return RestResponse.success(password);
	}

	/**
	 * 用户禁用/启用
	 * @param userId 用户id
	 * @return 禁用/启用是否成功
	 */
	@GetMapping("toggle/valid/{userId}")
	@ApiOperation("用户禁用/启用")
	@Permission(actionType = ActionType.E, grantType = GrantType.MANUAL)
	public RestResponse<?> toggleValid(@PathVariable Integer userId) {
		sysUserService.toggleValid(userId);
		return RestResponse.success();
	}

	/**
	 * 查询用户角色信息
	 * @param userId 用户id
	 * @return 返回角色列表及用户角色信息
	 */
	@GetMapping("{userId}/role")
	@ApiOperation("查询角色列表")
	@Permission(actionType = ActionType.E, grantType = GrantType.MANUAL)
	public RestResponse<SysUserRoleVO> roleList(@PathVariable Integer userId) {
		return RestResponse.success(sysUserService.roleList(userId));
	}

	/**
	 * 更新用户角色赋权
	 * @param userId 用户id
	 * @param userRoleList 角色列表
	 * @return 返回是否成功
	 */
	@PostMapping("{userId}/update/role")
	@ApiOperation("用户角色赋权")
	@Permission(actionType = ActionType.E, grantType = GrantType.MANUAL)
	public RestResponse<?> updateRole(@PathVariable Integer userId, @RequestBody Integer[] userRoleList) {
		sysUserService.updateRole(userId, userRoleList);
		return RestResponse.success();
	}
}
