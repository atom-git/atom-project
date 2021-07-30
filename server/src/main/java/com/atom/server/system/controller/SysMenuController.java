package com.atom.server.system.controller;

import com.atom.common.pojo.annotation.Permission;
import com.atom.common.pojo.http.RestResponse;
import com.atom.common.pojo.mapper.ActionType;
import com.atom.common.pojo.mapper.GrantType;
import com.atom.server.system.pojo.dto.SysMenuDTO;
import com.atom.server.system.pojo.vo.SysMenuVO;
import com.atom.server.system.service.ISysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zr
 * @description 系统菜单管理，树结构、编辑（新增/修改）、删除、上移/下移
 * @date 2021/4/23
 */
@RestController
@RequestMapping("/system/menu")
@Api("系统菜单管理")
@Permission
public class SysMenuController {
	/**
	 * 系统菜单服务
	 */
	@Resource
	private ISysMenuService sysMenuService;

	/**
	 * 查询菜单树结构
	 * @return 返回list结构的数据
	 */
	@GetMapping("tree")
	@ApiOperation("查询菜单树结构")
	@Permission(actionType = ActionType.Q, grantType = GrantType.AUTO)
	public RestResponse<List<SysMenuVO>> tree() {
		return RestResponse.success(sysMenuService.tree());
	}

	/**
	 * 新增或者编辑菜单数据
	 * @param sysMenuDTO 系统菜单传输对象
	 * @return 返回修改是否成功
	 */
	@PutMapping("update")
	@ApiOperation("新增或者编辑组织数据")
	@Permission(actionType = {ActionType.N, ActionType.E}, grantType = GrantType.MANUAL)
	public RestResponse<?> update(@RequestBody SysMenuDTO sysMenuDTO) {
		sysMenuService.saveOrUpdate(sysMenuDTO);
		return RestResponse.success();
	}

	/**
	 * 删除当前菜单，这里是物理删除，编辑里能够把其置成失效
	 * @param menuId 菜单的id
	 * @return 返回是否删除成功
	 */
	@DeleteMapping("delete/{menuId}")
	@ApiOperation("删除当前菜单")
	@Permission(actionType = ActionType.D, grantType = GrantType.MANUAL)
	public RestResponse<?> delete(@PathVariable Integer menuId) {
		sysMenuService.delete(menuId);
		return RestResponse.success();
	}

	/**
	 * 菜单顺序上移或者下移
	 * @param move 上移还是下移moveup|movedown
	 * @param sysMenuDTO 移动的菜单
	 * @return 返回是否调整成功
	 */
	@PostMapping("exchange/{move}")
	@ApiOperation("菜单顺序上移或者下移")
	@Permission(actionType = ActionType.E, grantType = GrantType.MANUAL)
	public RestResponse<?> exchange(@PathVariable String move, @RequestBody SysMenuDTO sysMenuDTO) {
		sysMenuService.exchange(move, sysMenuDTO);
		return RestResponse.success();
	}
}
