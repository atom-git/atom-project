package com.atom.common.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import javax.annotation.Resource;

/**
 * @author zr
 * @description websocket配置信息
 * 配置说明1：https://www.jianshu.com/p/4762494d42f1
 * 配置说明2：https://www.jianshu.com/p/9103c9c7e128
 * @date 2018/10/9
 */
@Configuration
// 开启使用STOMP协议来传输基于代理(message broker)的消息,这时控制器支持使用@MessageMapping,就像使用@RequestMapping一样
@EnableWebSocketMessageBroker
@Slf4j
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    /**
     * 消息拦截器
     */
    @Resource
    private MessageInterceptor messageInterceptor;

    /**
     * 注册STOMP协议的节点(endpoint),并映射指定的url
     * @param registry STOMP注册
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 注册STOMP协议的节点(endpoint)，指定用户拦截，指定使用SockJS
        registry.addEndpoint("/stomp")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    /**
     * 配置客户端入站的拦截器
     * @param registration 通道注册器
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(messageInterceptor);
    }

    /**
     * 配置消息代理，/topic广播消息代理 /user一对一消息代理
     * @param registry 消息代理
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // /topic广播消息代理 /user一对一消息代理
        long HEART_BEAT = 10000L;
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        // 线程池线程数，心跳连接开线程
        taskScheduler.setPoolSize(1);
        // 线程名前缀
        taskScheduler.setThreadNamePrefix("stomp-heart-beat-");
        // 初始化
        taskScheduler.initialize();
        registry.enableSimpleBroker("/stomp/topic", "/stomp/user")
                .setTaskScheduler(taskScheduler)
                .setHeartbeatValue(new long[]{HEART_BEAT, HEART_BEAT});
        // 点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user/
        registry.setUserDestinationPrefix("/stomp/user");
        // app目的地前缀
        registry.setApplicationDestinationPrefixes("/stomp");
    }
}
