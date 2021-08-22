package com.atom.common.websocket;

import cn.hutool.core.lang.Validator;
import com.atom.common.pojo.http.RestError;
import com.atom.common.pojo.mapper.PlatformType;
import com.atom.common.security.SessionUser;
import com.atom.common.security.cache.IUserCacheStore;
import com.atom.common.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zr
 * @description websocket消息发送通道拦截器，用于对消息的各个发送阶段进行拦截
 * @date 2021/8/19
 */
@Component
@Slf4j
public class MessageInterceptor implements ChannelInterceptor {

	/**
	 * 用户缓存器
	 */
	@Resource
	private IUserCacheStore userCacheStore;

	/**
	 * 在消息发送之前调用，方法中可以对消息进行修改，如果此方法返回值为空，则不会发生实际的消息发送调用
	 * @param message 消息
	 * @param channel 通道
	 * @return 返回消息体
	 */
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor =  MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
		if (accessor != null && !accessor.isHeartbeat()) {
			String token;
			SessionUser sessionUser;
			PlatformType platformType;
			String stompTokenKey;
			// 首次连接写入用户认证信息
			if (StompCommand.CONNECT.equals(accessor.getCommand())) {
				// 获取用户token信息，由于连接前台要求必须发生在登录后，因此全局统计
				token = accessor.getFirstNativeHeader("Access-Token");
				if (Validator.isNotEmpty(token)) {
					platformType = PlatformType.matchName(accessor.getFirstNativeHeader("Platform-Type"));
					sessionUser = userCacheStore.getTokenUser(platformType, token);
					stompTokenKey = platformType.name() + "_STOMP_TOKEN";
					// 设置用户信息
					accessor.setUser(sessionUser);
					// 存入缓存可用于统计在线用户等信息 TODO 分布式系统中需要考虑多节点认证问题
					// 记录用户连接信息
					RedisUtil.setMap(stompTokenKey, token, sessionUser);
					log.info("用户【{}】已上线", sessionUser.getAccount());
					return message;
				} else {
					return MessageBuilder.withPayload(RestError.ERROR9004).build();
				}
			} else if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
				// 获取accessor中的simpUser
				sessionUser = (SessionUser) accessor.getUser();
				if (sessionUser != null) {
					token = sessionUser.getToken();
					platformType = sessionUser.getPlatformType();
					stompTokenKey = platformType.name() + "_STOMP_TOKEN";
					// 断开连接时用户下线
					RedisUtil.remove(stompTokenKey, token);
					log.info("用户【{}】已下线", sessionUser.getAccount());
				}
				return message;
			} else {
				return message;
			}
		} else {
			return message;
		}
	}

	/**
	 * 在消息发送后立刻调用，boolean值参数表示该调用的返回值
	 * @param message 消息
	 * @param channel 通道
	 * @param sent 是否发送
	 */
	@Override
	public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
		ChannelInterceptor.super.postSend(message, channel, sent);
	}
}
