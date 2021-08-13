package com.atom.server.system.controller;

import com.atom.common.pojo.annotation.Permission;
import com.atom.common.pojo.http.RestResponse;
import com.atom.common.pojo.mapper.ActionType;
import com.atom.common.pojo.mapper.GrantType;
import com.atom.common.pojo.table.PageData;
import com.atom.common.pojo.table.TableData;
import com.atom.server.system.pojo.dto.SysTypeCodeDTO;
import com.atom.server.system.pojo.dto.SysTypeDTO;
import com.atom.server.system.pojo.filter.SysTypeCodeFilter;
import com.atom.server.system.pojo.filter.SysTypeFilter;
import com.atom.server.system.pojo.vo.SysTypeCodeVO;
import com.atom.server.system.pojo.vo.SysTypeVO;
import com.atom.server.system.service.ISysTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author zr
 * @description 系统数据字典管理
 * 数据字典：字典列表、字典编辑（新增/修改）、删除
 * 维值数据：列表、树、编辑（新增/修改）、删除、上移/下移
 * @date 2021/4/23
 */
@RestController
@RequestMapping("/system/type")
@Api("系统数据字典管理")
@Permission
public class SysTypeController {

	/**
	 * 维值管理服务
	 */
	@Resource
	private ISysTypeService sysTypeService;

	/**
	 * 查询数据字典的列表
	 * @param sysTypeFilter 数字字典过滤器
	 * @param pageData 分页信息
	 * @param response 响应
	 * @return 返回列表
	 */
	@GetMapping("list")
	@ApiOperation("查询数据字典的列表")
	@Permission(actionType = ActionType.Q, grantType = GrantType.AUTO)
	public RestResponse<TableData<SysTypeVO>> list(SysTypeFilter sysTypeFilter, PageData pageData, HttpServletResponse response) {
		TableData<SysTypeVO> tableData = sysTypeService.list(sysTypeFilter, pageData, response);
		return RestResponse.success(tableData);
	}

	/**
	 * 新增或者编辑数据字典类型
	 * @param sysTypeDTO 系统数据字典类型DTO
	 * @return 返回新增编辑的结果
	 */
	@PostMapping("update")
	@ApiOperation("新增或者编辑数据字典类型")
	@Permission(actionType = {ActionType.N, ActionType.E}, grantType = GrantType.MANUAL)
	public RestResponse<?> update(@RequestBody SysTypeDTO sysTypeDTO) {
		sysTypeService.saveOrUpdate(sysTypeDTO);
		return RestResponse.success();
	}

	/**
	 * 删除系统数据字典
	 * @param typeId 数据字典ID
	 * @return 返回是否删除成功
	 */
	@DeleteMapping("delete/{typeId}")
	@ApiOperation("删除系统数据字典")
	@Permission(actionType = ActionType.D, grantType = GrantType.MANUAL)
	public RestResponse<?> delete(@PathVariable Integer typeId) {
		sysTypeService.delete(typeId);
		return RestResponse.success();
	}

	/**
	 * 查询数据字典列表
	 * @param sysTypeCodeFilter 数据字典查询条件
	 * @return 返回数据字典列表
	 */
	@GetMapping("code/list")
	@ApiOperation("查询数据字典列表")
	@Permission(actionType = ActionType.Q, grantType = GrantType.AUTO)
	public RestResponse<List<SysTypeCodeVO>> codeList(SysTypeCodeFilter sysTypeCodeFilter) {
		List<SysTypeCodeVO> sysTypeCodeVOList = sysTypeService.codeList(sysTypeCodeFilter);
		return RestResponse.success(sysTypeCodeVOList);
	}

	/**
	 * 查询数据字典对结构，这里所有的过滤条件仅对树的第一级有效，是先找到树，然后再过滤
	 * @param sysTypeCodeFilter 数据字典查询条件
	 * @return 返回数据字典树
	 */
	@GetMapping("code/tree")
	@ApiOperation("查询数据字典树")
	@Permission(actionType = ActionType.Q, grantType = GrantType.AUTO)
	public RestResponse<List<SysTypeCodeVO>> codeTree(SysTypeCodeFilter sysTypeCodeFilter) {
		List<SysTypeCodeVO> sysTypeCodeVOTree = sysTypeService.codeTree(sysTypeCodeFilter);
		return RestResponse.success(sysTypeCodeVOTree);
	}

	/**
	 * 新增或者编辑数据字典
	 * @param meanId 字典类型ID
	 * @param sysTypeCodeDTO 字典数据
	 * @return 返回是否更新成功
	 */
	@PutMapping("{meanId}/code/update")
	@ApiOperation(("新增或者编辑数据字典"))
	@Permission(actionType = {ActionType.N, ActionType.E}, grantType = GrantType.MANUAL)
	public RestResponse<SysTypeCodeVO> updateCode(@PathVariable Integer meanId, @RequestBody SysTypeCodeDTO sysTypeCodeDTO) {
		return RestResponse.success(sysTypeService.saveOrUpdateCode(meanId, sysTypeCodeDTO));
	}

	/**
	 * 删除数据字典
	 * @param codeId 数据字典ID
	 * @return 返回是否删除成功
	 */
	@DeleteMapping("code/delete/{codeId}")
	@ApiOperation("删除数据字典")
	@Permission(actionType = ActionType.D, grantType = GrantType.MANUAL)
	public RestResponse<?> deleteCode(@PathVariable Integer codeId) {
		sysTypeService.deleteCode(codeId);
		return RestResponse.success();
	}

	/**
	 * 字典顺序上移或者下移
	 * @param move 上移还是下移moveup|movedown
	 * @param sysTypeCodeDTO 移动的菜单
	 * @return 返回是否调整成功
	 */
	@PostMapping("exchange/{move}")
	@ApiOperation("字典顺序上移或者下移")
	@Permission(actionType = ActionType.E, grantType = GrantType.MANUAL)
	public RestResponse<?> exchange(@PathVariable String move, @RequestBody SysTypeCodeDTO sysTypeCodeDTO) {
		sysTypeService.exchange(move, sysTypeCodeDTO);
		return RestResponse.success();
	}
}
