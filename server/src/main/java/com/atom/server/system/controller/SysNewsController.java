package com.atom.server.system.controller;

import com.atom.common.pojo.annotation.Permission;
import com.atom.common.pojo.http.RestResponse;
import com.atom.common.pojo.mapper.ActionType;
import com.atom.common.pojo.mapper.GrantType;
import com.atom.common.pojo.table.PageData;
import com.atom.common.pojo.table.TableData;
import com.atom.server.system.pojo.filter.SysNewsFilter;
import com.atom.server.system.pojo.vo.SysNewsVO;
import com.atom.server.system.service.ISysNewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zr
 * @description 系统通知消息待办管理，包括websocket请求及http请求
 * websocket:消息广播和点对点发送，广播仅针对在线用户，信息消费过一次就删除，点对点是针对目标用户，用户不在线，则等用户在线后发送
 * http:消息列表查询，消息详情查询，消息已读/未读操作，消息删除，管理员具有消息重发的功能
 * @date 2021/8/19
 */
@RestController
@RequestMapping("/system/news")
@Api("系统通知消息待办管理")
@Permission
public class SysNewsController {

	/**
	 * 系统通知消息待办服务
	 */
	@Resource
	private ISysNewsService sysNewsService;

	/**
	 * 获取系统提醒消息待办列表
	 * @param sysNewsFilter 系统提醒消息待办过滤对象
	 * @param pageData 分页信息
	 * @param response 请求响应
	 * @return 系统提醒消息待办列表
	 */
	@GetMapping("list")
	@ApiOperation("获取系统提醒消息待办列表")
	@Permission(actionType = ActionType.Q, grantType = GrantType.MANUAL)
	public RestResponse<TableData<SysNewsVO>> list(SysNewsFilter sysNewsFilter, PageData pageData, HttpServletResponse response) {
		TableData<SysNewsVO> tableData = sysNewsService.list(sysNewsFilter, pageData, response);
		// 下载时必须返回空，否则会继续做类型转换为报错
		if (pageData.getDownload()) {
			return null;
		} else {
			return RestResponse.success(tableData);
		}
	}

	/**
	 * 设置一条消息为已读
	 * @param newsId 消息id
	 * @return 返回是否请求成功
	 */
	@PutMapping("read/{newsId}")
	@ApiOperation("设置一条消息为已读")
	@Permission(actionType = ActionType.E, grantType = GrantType.MANUAL)
	public RestResponse<?> read(@PathVariable Integer newsId) {
		sysNewsService.read(newsId);
		return RestResponse.success();
	}

	/**
	 * 设置多条消息为已读
	 * @param newsIds 消息id
	 * @return 返回是否请求成功
	 */
	@PutMapping("read")
	@ApiOperation("设置多条消息为已读")
	@Permission(actionType = ActionType.E, grantType = GrantType.MANUAL)
	public RestResponse<?> read(@RequestBody Integer... newsIds) {
		sysNewsService.read(newsIds);
		return RestResponse.success();
	}

	/**
	 * 设置一条消息为未读
	 * @param newsId 消息id
	 * @return 返回是否请求成功
	 */
	@PutMapping("unread/{newsId}")
	@ApiOperation("设置一条消息为未读")
	@Permission(actionType = ActionType.E, grantType = GrantType.MANUAL)
	public RestResponse<?> unread(@PathVariable Integer newsId) {
		sysNewsService.unread(newsId);
		return RestResponse.success();
	}

	/**
	 * 删除消息
	 * @param newsId 消息id
	 * @return 返回是否请求成功
	 */
	@DeleteMapping("delete/{newsId}")
	@ApiOperation("删除消息")
	@Permission(actionType = ActionType.D, grantType = GrantType.MANUAL)
	public RestResponse<?> delete(@PathVariable Integer newsId) {
		sysNewsService.delete(newsId);
		return RestResponse.success();
	}
}
