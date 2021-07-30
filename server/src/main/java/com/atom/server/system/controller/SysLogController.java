package com.atom.server.system.controller;

import com.atom.common.pojo.annotation.Permission;
import com.atom.common.pojo.http.RestResponse;
import com.atom.common.pojo.mapper.ActionType;
import com.atom.common.pojo.mapper.GrantType;
import com.atom.common.pojo.table.PageData;
import com.atom.common.pojo.table.TableData;
import com.atom.server.system.pojo.filter.SysLogFilter;
import com.atom.server.system.pojo.vo.SysLogVO;
import com.atom.server.system.service.ISysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zr
 * @description 系统日志管理，日志列表
 * @date 2021/4/23
 */
@RestController
@RequestMapping("/system/log")
@Api("系统日志管理")
@Permission
public class SysLogController {

	/**
	 * 系统日志服务
	 */
	@Resource
	private ISysLogService sysLogService;

	/**
	 * 获取系统日志数据表，或下载数据
	 * @param sysLogFilter 系统日志Filter对象
	 * @param pageData 分页信息
	 * @param response 请求响应
	 * @return 返回当前页的日志数据列表
	 */
	@GetMapping("list")
	@ApiOperation("获取系统日志数据表")
	@Permission(actionType = ActionType.Q, grantType = GrantType.AUTO)
	public RestResponse<TableData<SysLogVO>> list(SysLogFilter sysLogFilter, PageData pageData, HttpServletResponse response) {
		TableData<SysLogVO> tableData = sysLogService.list(sysLogFilter, pageData, response);
		// 下载时必须返回空，否则会继续做类型转换为报错
		return RestResponse.success(tableData);
	}
}
