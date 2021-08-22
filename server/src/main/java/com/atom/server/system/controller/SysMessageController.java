package com.atom.server.system.controller;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zr
 * @description 系统消息控制器，包括websocket请求及http请求
 * websocket:消息广播和点对点发送，广播仅针对在线用户，信息消费过一次就删除，点对点是针对目标用户，用户不在线，则等用户在线后发送
 * http:消息列表查询，消息详情查询，消息已读/未读操作，消息删除，管理员具有消息重发的功能
 * @date 2021/8/19
 */
@RestController
public class SysMessageController {

	/**
	 * 消息发送模板
	 */
	@Resource
	private SimpMessagingTemplate messagingTemplate;

}
