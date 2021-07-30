package com.atom.server.system.controller;

import com.atom.common.pojo.annotation.Permission;
import com.atom.common.pojo.http.RestResponse;
import com.atom.common.pojo.mapper.ActionType;
import com.atom.common.pojo.mapper.GrantType;
import com.atom.common.pojo.table.PageData;
import com.atom.common.pojo.table.TableData;
import com.atom.server.system.pojo.filter.SysActionFilter;
import com.atom.server.system.pojo.vo.SysActionTopicVO;
import com.atom.server.system.pojo.vo.SysActionVO;
import com.atom.server.system.service.ISysActionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author zr
 * @description 系统资源管理，主题列表，资源列表
 * @date 2021/4/23
 */
@RestController
@RequestMapping("/system/action")
@Api("系统资源管理")
@Permission
public class SysActionController {

	/**
	 * 系统资源服务
	 */
	@Resource
	private ISysActionService sysActionService;

	/**
	 * 查询动作资源主题列表
	 * @return 返回动作资源主题列表
	 */
	@GetMapping("topic/list")
	@ApiOperation("查询动作资源主题列表")
	@Permission(actionType = ActionType.Q, grantType = GrantType.AUTO)
	public RestResponse<List<SysActionTopicVO>> topicList () {
		return RestResponse.success(sysActionService.topicList());
	}

	/**
	 * 查询动作资源列表，下载的量由前端进行控制
	 * @param sysActionFilter 动作资源过滤条件
	 * @param pageData 分页条件
	 * @param response 响应
	 * @return 返回列表
	 */
	@GetMapping("list")
	@ApiOperation("查询动作资源列表")
	@Permission(actionType = ActionType.Q, grantType = GrantType.AUTO)
	public RestResponse<TableData<SysActionVO>> list (SysActionFilter sysActionFilter, PageData pageData, HttpServletResponse response) {
		TableData<SysActionVO> tableData = sysActionService.actionList(sysActionFilter, pageData, response);
		return RestResponse.success(tableData);
	}
}
