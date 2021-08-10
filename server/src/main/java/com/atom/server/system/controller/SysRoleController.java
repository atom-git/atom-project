package com.atom.server.system.controller;

import com.atom.common.pojo.annotation.Permission;
import com.atom.common.pojo.http.RestResponse;
import com.atom.common.pojo.mapper.ActionType;
import com.atom.common.pojo.mapper.GrantType;
import com.atom.server.system.pojo.dto.SysPermissionDTO;
import com.atom.server.system.pojo.dto.SysRoleDTO;
import com.atom.server.system.pojo.vo.SysRoleMenuVO;
import com.atom.server.system.pojo.vo.SysRoleVO;
import com.atom.server.system.service.ISysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zr
 * @description 系统角色管理，仅dashboard管理功能
 * 角色列表、编辑（新增，修改）、删除、角色权限（菜单/资源）、角色权限更新
 * @date 2021/4/23
 */
@RestController
@RequestMapping("/system/role")
@Api("系统角色管理")
@Permission
public class SysRoleController {

	/**
	 * 系统角色管理服务
	 */
	@Resource
	private ISysRoleService sysRoleService;

	/**
	 * 获取系统角色数据表，展示为左侧是角色树，右侧为菜单与资源权限配置
	 * @return 返回当前页的角色数据列表
	 */
	@GetMapping("list")
	@ApiOperation("获取系统角色数据表")
	@Permission(actionType = ActionType.Q, grantType = GrantType.AUTO)
	public RestResponse<List<SysRoleVO>> list() {
		return RestResponse.success(sysRoleService.list());
	}

	/**
	 * 新增或者编辑角色信息
	 * @param sysRoleDTO 角色传输dto
	 * @return 是否修改成功
	 */
	@PutMapping("update")
	@ApiOperation("新增或者编辑角色信息")
	@Permission(actionType = {ActionType.N, ActionType.E}, grantType = GrantType.MANUAL)
	public RestResponse<?> update(@RequestBody SysRoleDTO sysRoleDTO) {
		sysRoleService.saveOrUpdate(sysRoleDTO);
		return RestResponse.success();
	}

	/**
	 * 删除系统角色
	 * @param roleId 角色id
	 * @return 返回是否删除成功
	 */
	@DeleteMapping("delete/{roleId}")
	@ApiOperation("删除角色")
	@Permission(actionType = ActionType.D, grantType = GrantType.MANUAL)
	public RestResponse<?> delete(@PathVariable Integer roleId) {
		sysRoleService.delete(roleId);
		return RestResponse.success();
	}

	/**
	 * 查询角色权限
	 * @param roleId 角色id
	 * @return 返回角色权限
	 */
	@GetMapping("{roleId}/permission")
	@ApiOperation("给角色赋权菜单和资源按钮")
	@Permission(actionType = ActionType.E, grantType = GrantType.MANUAL)
	public RestResponse<SysRoleMenuVO> permission(@PathVariable Integer roleId) {
		SysRoleMenuVO sysRoleMenuVO = sysRoleService.permission(roleId);
		return RestResponse.success(sysRoleMenuVO);
	}

	/**
	 * 给角色赋权
	 * @param roleId 角色id
	 * @param sysPermissionDTO 角色权限信息
	 * @return 返回是否成功
	 */
	@PostMapping("{roleId}/update/permission")
	@ApiOperation("给角色赋权菜单和资源按钮")
	@Permission(actionType = ActionType.E, grantType = GrantType.MANUAL)
	public RestResponse<?> updatePermission(@PathVariable Integer roleId, @RequestBody SysPermissionDTO sysPermissionDTO) {
		sysRoleService.updatePermission(roleId, sysPermissionDTO);
		return RestResponse.success();
	}

}
