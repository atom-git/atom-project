package com.atom.server.system.controller;

import com.atom.common.pojo.annotation.Permission;
import com.atom.common.pojo.http.RestResponse;
import com.atom.common.pojo.mapper.ActionType;
import com.atom.common.pojo.mapper.GrantType;
import com.atom.server.system.pojo.dto.SysDeptDTO;
import com.atom.server.system.pojo.vo.SysDeptVO;
import com.atom.server.system.service.ISysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zr
 * @description 系统组织机构管理，树结构查询、编辑（新增/修改）、删除
 * 系统组织初始化默认有两个:管理平台、门户平台
 * @date 2021/4/23
 */
@RestController
@RequestMapping("/system/dept")
@Api("系统组织管理")
@Permission
public class SysDeptController {

	/**
	 * 系统组织机构管理服务
	 */
	@Resource
	private ISysDeptService sysDeptService;

	/**
	 * 查询组织树结构
	 * @return 返回list结构的数据
	 */
	@GetMapping("tree")
	@ApiOperation("按照树结构查询组织机构")
	@Permission(actionType = ActionType.Q, grantType = GrantType.AUTO)
	public RestResponse<List<SysDeptVO>> tree() {
		return RestResponse.success(sysDeptService.tree());
	}

	/**
	 * 新增或者编辑组织数据
	 * @param sysDeptDTO 系统组织传输对象
	 * @return 返回修改是否成功
	 */
	@PutMapping("update")
	@ApiOperation("新增或者编辑组织数据")
	@Permission(actionType = {ActionType.N, ActionType.E}, grantType = GrantType.MANUAL)
	public RestResponse<?> update(@RequestBody SysDeptDTO sysDeptDTO) {
		sysDeptService.saveOrUpdate(sysDeptDTO);
		return RestResponse.success();
	}

	/**
	 * 删除当前组织，这里是物理删除，编辑里能够把其置成失效
	 * @param deptId 组织的id
	 * @return 返回是否删除成功
	 */
	@DeleteMapping("delete/{deptId}")
	@ApiOperation("删除当前组织")
	@Permission(actionType = ActionType.D, grantType = GrantType.MANUAL)
	public RestResponse<?> delete(@PathVariable Integer deptId) {
		sysDeptService.delete(deptId);
		return RestResponse.success();
	}
}
