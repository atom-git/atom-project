package com.atom.server.system.controller;

import com.atom.common.pojo.annotation.Permission;
import com.atom.common.pojo.http.RestResponse;
import com.atom.common.pojo.mapper.ActionType;
import com.atom.common.pojo.mapper.GrantType;
import com.atom.common.pojo.table.PageData;
import com.atom.common.pojo.table.TableData;
import com.atom.common.security.SessionUser;
import com.atom.server.system.pojo.dto.SysFormDTO;
import com.atom.server.system.pojo.filter.SysFormFilter;
import com.atom.server.system.pojo.vo.SysFormVO;
import com.atom.server.system.service.ISysFormService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zr
 * @description 系统自定义表单管理，表单查询，新增，编辑，禁用/启用，记录日志
 * @date 2021/11/18
 */
@RestController
@RequestMapping("/system/form")
@Api("系统自定义表单管理")
@Permission
public class SysFormController {

	/**
	 * 系统自定义表单服务
	 */
	@Resource
	private ISysFormService sysFormService;

	/**
	 * 获取系统自定义表单数据表
	 * @param sysFormFilter 系统自定义表单filter
	 * @param pageData 分页信息
	 * @return 返回当前页的系统自定义表单列表
	 */
	@GetMapping("list")
	@ApiOperation("获取系统自定义表单数据表")
	@Permission(actionType = ActionType.Q, grantType = GrantType.MANUAL)
	public RestResponse<TableData<SysFormVO>> list(SysFormFilter sysFormFilter, PageData pageData) {
		TableData<SysFormVO> tableData = sysFormService.list(sysFormFilter, pageData);
		return RestResponse.success(tableData);
	}

	/**
	 * 新增或者编辑系统自定义表单
	 * @param sessionUser 系统用户
	 * @param sysFormDTO 系统自定义表单数据
	 * @return 返回编辑是否成功
	 */
	@PutMapping("update")
	@ApiOperation("新增或者编辑用户信息")
	@Permission(actionType = {ActionType.N, ActionType.E}, grantType = GrantType.MANUAL)
	public RestResponse<?> update(SessionUser sessionUser, @RequestBody SysFormDTO sysFormDTO) {
		sysFormService.saveOrUpdate(sessionUser, sysFormDTO);
		return RestResponse.success();
	}

	/**
	 * 系统自定义表单禁用/启用
	 * @param formId 系统自定义表单id
	 * @return 禁用/启用是否成功
	 */
	@GetMapping("toggle/valid/{formId}")
	@ApiOperation("系统自定义表单禁用/启用")
	@Permission(actionType = ActionType.E, grantType = GrantType.MANUAL)
	public RestResponse<?> toggleValid(@PathVariable Integer formId) {
		sysFormService.toggleValid(formId);
		return RestResponse.success();
	}
}
