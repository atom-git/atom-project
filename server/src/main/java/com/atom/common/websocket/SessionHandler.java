package com.atom.common.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;

/**
 * @author zr
 * @description 用户握手认证，获取并认证用户
 * @date 2021/8/22
 */
@Component
public class SessionHandler extends DefaultHandshakeHandler {

	/**
	 * 确认用户信息
	 * @param request 请求
	 * @param wsHandler 握手信息
	 * @param attributes 属性信息
	 * @return 返回用户认证信息
	 */
	@Override
	protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
		HttpSession httpSession = getSession(request);
		// 获取请求头信息

		return super.determineUser(request, wsHandler, attributes);
	}

	/**
	 * 根据请求获取HttpSession
	 * @param request 请求信息
	 * @return 返回HttpSession
	 */
	private HttpSession getSession(ServerHttpRequest request) {
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
			return serverRequest.getServletRequest().getSession(false);
		}
		return null;
	}
}
