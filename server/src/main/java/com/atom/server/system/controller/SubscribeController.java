package com.atom.server.system.controller;

import com.atom.common.pojo.http.RestResponse;
import com.atom.common.security.SessionUser;
import com.atom.server.system.pojo.vo.UserNewsVO;
import com.atom.server.system.service.ISysNewsService;
import com.atom.server.system.service.ISystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zr
 * @description 系统消息订阅管理
 * @date 2021/8/30
 */
@RestController
@Api("系统消息订阅管理")
public class SubscribeController {

	/**
	 * 系统服务
	 */
	@Resource
	private ISystemService systemService;

	/**
	 * 系统通知消息待办服务
	 */
	@Resource
	private ISysNewsService sysNewsService;

	/**
	 * 订阅在线用户数信息
	 * @return 返回STOMP响应
	 */
	@SubscribeMapping("/topic/onlineUser")
	@ApiOperation("订阅在线用户数信息")
	public RestResponse<Integer> onlineUser() {
		return RestResponse.success(systemService.onlineUser());
	}

	/**
	 * 订阅用户提醒消息待办信息
	 * @param sessionUser 用户信息，SessionUser为java.security.Principal的了类，因此可以注入
	 * @return 返回STOMP响应
	 */
	@SubscribeMapping("/fetch/news")
	@ApiOperation("订阅用户提醒消息待办信息")
	public RestResponse<UserNewsVO> fetchNews(SessionUser sessionUser) {
		UserNewsVO userNewsVO = sysNewsService.fetchNews(sessionUser);
		return RestResponse.success(userNewsVO);
	}
}
