package com.atom.common.security.cache;

import cn.hutool.core.lang.Validator;
import com.atom.common.pojo.mapper.PlatformType;
import com.atom.common.security.SessionUser;
import com.atom.server.system.entity.SysUser;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zr
 * @description 基于内存的用户信息缓存
 * @date 2019-08-17
 */
//@Component
public class MemmoryUserCacheStore implements IUserCacheStore {

    /**
     * 令牌用户缓存
     */
    private static final Map<String, SessionUser> tokenUserCache = new ConcurrentHashMap<>();

    /**
     * 用户令牌缓存
     */
    private static final Map<String, String> userTokenCache = new ConcurrentHashMap<>();

    /**
     * 注册用户
     * @param platformType 平台类型
     * @param user 用户对象
     */
    @Override
    public void register(PlatformType platformType, SessionUser user) {
        String platform = platformType.name() + "_";
        String oldToken;
        if (existUserToken(platformType, user.getId())) {
            oldToken = getUserToken(platformType, user);
            if (Validator.isNotEmpty(oldToken)) {
                removeTokenUser(platform, oldToken);
            }
        }
        setTokenUser(platform, user.getToken(), user);
        setUserToken(platform, user.getId(), user.getToken());
    }

    /**
     * 取消注册
     * @param platformType 平台类型
     * @param token 访问令牌
     */
    @Override
    public void unRegister(PlatformType platformType, String token) {
        String platform = platformType.name() + "_";
        if (existTokenUser(platformType, token)) {
            SessionUser user = getTokenUser(platformType, token);
            if (user != null) {
                removeUserToken(platform, user.getId());
            }
            removeTokenUser(platform, token);
        }
    }

    /**
     * 移除缓存用户
     * @param platformType 平台类型
     * @param userId 用户序号
     */
    @Override
    public void unRegister(PlatformType platformType, Integer userId) {
        String platform = platformType.name() + "_";
        if (existUserToken(platformType, userId)) {
            SessionUser user = getUserSession(platformType, userId);
            if (user != null) {
                removeUserToken(platform, user.getId());
                removeTokenUser(platform, user.getToken());
            }
        }
    }

    /**
     * 移除全平台缓存用户
     * @param userId 用户序号
     */
    @Override
    public void unRegister(Integer userId) {
        PlatformType[] platformTypes = PlatformType.values();
        for (PlatformType platformType : platformTypes) {
            this.unRegister(platformType, userId);
        }
    }

    /**
     * 根据令牌获取用户
     * @param platformType 平台类型
     * @param token 令牌
     * @return sessionUser
     */
    @Override
    public SessionUser getTokenUser(PlatformType platformType, String token) {
        return tokenUserCache.get(platformType.name() + "_" + token);
    }

    /**
     * 获取用户令牌
     * @param platformType 平台类型
     * @param user 用户
     * @return 令牌信息
     */
    @Override
    public String getUserToken(PlatformType platformType, SessionUser user) {
        return userTokenCache.get(platformType.name() + "_" + user.getId());
    }

    /**
     * 读取用户token
     * @param platformType 平台类型
     * @param userId 用户帐号
     * @return 令牌
     */
    @Override
    public String getUserToken(PlatformType platformType, Integer userId) {
        return userTokenCache.get(platformType.name() + "_" + userId);
    }

    /**
     * 获取用户Session
     * @param platformType 平台类型
     * @param userId 用户帐号
     * @return 用户Session
     */
    @Override
    public SessionUser getUserSession(PlatformType platformType, Integer userId) {
        String token = this.getUserToken(platformType, userId);
        return Validator.isNotEmpty(token) ? this.getTokenUser(platformType, token) : null;
    }

    /**
     * 令牌用户是否存在
     * @param platformType 平台类型
     * @param token 令牌
     * @return 是否存在此用户
     */
    @Override
    public boolean existTokenUser(PlatformType platformType, String token) {
        return tokenUserCache.containsKey(platformType.name() + "_" + token);
    }

    /**
     * 用户是否存在令牌
     * @param platformType 平台类型
     * @param userId 用户帐号
     * @return 是否存在
     */
    @Override
    public boolean existUserToken(PlatformType platformType, Integer userId) {
        return userTokenCache.containsKey(platformType.name() + "_" + userId);
    }

    /**
     * 刷新用户信息
     * @param platformType 平台
     * @param user 用户
     */
    @Override
    public void flushSession(PlatformType platformType, SessionUser user) {
        this.register(platformType, user);
    }

    /**
     * 根据用户Id刷新全平台用户信息
     * @param sysUser 用户信息
     */
    @Override
    public void flushSession(SysUser sysUser) {
        PlatformType[] platformTypes = PlatformType.values();
        for (PlatformType platformType : platformTypes) {
            SessionUser sessionUser = this.getUserSession(platformType, sysUser.getId());
            if (sessionUser != null) {
                // 更橷SessionUser信息
                sessionUser.refresh(sysUser);
                this.flushSession(platformType, sessionUser);
            }
        }
    }

    /**
     * 移除令牌
     * @param platform 平台类型
     * @param token 令牌信息
     */
    private void removeTokenUser(String platform, String token) {
        tokenUserCache.remove(platform + token);
    }

    /**
     * 设置令牌用户
     * @param platform 平台类型
     * @param token 令牌
     * @param user  用户
     */
    private void setTokenUser(String platform, String token, SessionUser user) {
        tokenUserCache.put(platform + token, user);
    }

    /**
     * 移除用户令牌
     * @param platform 平台类型
     * @param userId 用户帐号
     */
    private void removeUserToken(String platform, Integer userId) {
        userTokenCache.remove(platform + userId);
    }

    /**
     * 设置用户令牌
     * @param platform 平台类型
     * @param userId 用户帐号
     * @param token  令牌信息
     */
    private void setUserToken(String platform, Integer userId, String token) {
        userTokenCache.put(platform + userId, token);
    }
}
