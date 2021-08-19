package com.atom.common.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author zr
 * @description websocket配置信息
 * @date 2018/10/9
 */
@Aspect
@Configuration
@EnableWebSocketMessageBroker
@Slf4j
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
    }

    /**
     * 定义消息推送切面
     */
    @Pointcut("target(org.springframework.messaging.simp.SimpMessagingTemplate)")
    public void broadcastPointcut() {}

    /**
     * controller前的动作打印相关日志
     * @param point 切点
     */
    @Before("broadcastPointcut()")
    public void doBeforeController(JoinPoint point) {
        String methodName = point.getSignature().getName();
        Object[] args = point.getArgs();
        if ("convertAndSend".equals(methodName) && args.length >= 2) {
            log.debug("websocket广播推送 事件{} 消息{}", args[0], args[1]);
        }
        if ("convertAndSendToUser".equals(methodName) && args.length >= 3) {
            log.debug("websocket精准推送 用户{} 事件{} 消息{}", args[0], args[1], args[2]);
        }
    }
}
