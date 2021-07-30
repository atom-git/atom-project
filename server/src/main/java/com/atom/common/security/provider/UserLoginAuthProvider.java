package com.atom.common.security.provider;

import cn.hutool.core.util.RandomUtil;
import com.atom.common.pojo.http.RestRequest;
import com.atom.common.pojo.http.RestResponse;
import com.atom.common.pojo.mapper.ActionType;
import com.atom.common.pojo.mapper.LogType;
import com.atom.common.pojo.mapper.PlatformType;
import com.atom.common.security.SessionUser;
import com.atom.common.security.cache.IUserCacheStore;
import com.atom.server.system.pojo.dto.SignInDTO;
import com.atom.server.system.service.ISysLogService;
import com.atom.server.system.service.ISystemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zr
 * @description 用户登录授权提供者
 * @date 2021/4/22
 */
@Component
@Slf4j
public class UserLoginAuthProvider implements AuthenticationProvider {
    /**
     * 是否允许多用户登录
     */
    private static boolean MULTI_LOGIN;

    @Value("${atom.user.multi.login}")
    public void setMultiLogin(boolean multiLogin) {
        MULTI_LOGIN = multiLogin;
    }

    /**
     * 系统相关服务
     */
    @Resource
    private ISystemService systemService;

    /**
     * 系统日志相关服务
     */
    @Resource
    private ISysLogService sysLogService;

    /**
     * 用户缓存
     */
    @Resource
    private IUserCacheStore userCacheStore;

    /**
     * 用户名密码登录校验
     * @param authentication 认证信息
     * @return 返回认证的用户信息
     * @throws AuthenticationException 认证异常
     */
    @Override
    public Authentication authenticate(Authentication authentication) {
        AccountAuthentication authRequest = (AccountAuthentication) authentication;
        String account = authRequest.getName();
        SignInDTO signUser = authRequest.getSignUser();
        // 查询同平台是否有用户登录信息
        PlatformType platformType = authRequest.getPlatformType();
        log.info("平台：[{}],用户{}进行登录认证，参数为：{}", authRequest.getPlatform(), account, signUser);
        RestRequest restRequest = new RestRequest("/login", signUser, platformType, new ActionType[]{ActionType.Q});
        // 请求开始时间
        long startTime = System.currentTimeMillis();
        // 用户登录
        SessionUser sessionUser = new SessionUser(account);
        try {
            // 用户登录
            sessionUser = systemService.signIn(authRequest.getPlatform(), signUser);
            // 同平台用户是否能够同时登录根据配置信息来定
            String token;
            // 支持多人登录同一帐户时，直接取当前已登录的用户信息
            if (MULTI_LOGIN && userCacheStore.existUserToken(platformType, account)) {
                token = userCacheStore.getUserToken(platformType, sessionUser);
                sessionUser.setToken(token);
                // 刷新用户信息
                userCacheStore.flushTokenUser(platformType, sessionUser);
            } else {
                token = RandomUtil.randomStringUpper(32);
                sessionUser.setToken(token);
                // 写入缓存
                userCacheStore.register(platformType, sessionUser);
            }
            restRequest.setResult(RestResponse.success(sessionUser));
            return sessionUser;
        } catch (AuthenticationException e) {
            restRequest.setException(e.getMessage());
            throw e;
        } finally {
            // 执行时长
            restRequest.setExecutionTime(System.currentTimeMillis() - startTime);
            // 日志类型 1：认证登录日志 2：服务调用日志 3：数据同步日志
            sysLogService.save(sessionUser, LogType.AUTH, restRequest);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(AccountAuthentication.class);
    }
}
