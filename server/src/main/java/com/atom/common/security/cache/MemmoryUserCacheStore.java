package com.atom.common.security.cache;

import cn.hutool.core.lang.Validator;
import com.atom.common.pojo.mapper.PlatformType;
import com.atom.common.security.SessionUser;

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
        if (existUserToken(platformType, user.getAccount())) {
            oldToken = getUserToken(platformType, user);
            if (Validator.isNotEmpty(oldToken)) {
                removeTokenUser(platform, oldToken);
            }
        }
        setTokenUser(platform, user.getToken(), user);
        setUserToken(platform, user.getAccount(), user.getToken());
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
                removeUserToken(platform, user.getAccount());
            }
            removeTokenUser(platform, token);
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
        return userTokenCache.get(platformType.name() + "_" + user.getAccount());
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
     * @param account 用户帐号
     * @return 是否存在
     */
    @Override
    public boolean existUserToken(PlatformType platformType, String account) {
        return userTokenCache.containsKey(platformType.name() + "_" + account);
    }

    /**
     * 刷新用户信息
     * @param platformType 平台
     * @param user 用户
     */
    @Override
    public void flushTokenUser(PlatformType platformType, SessionUser user) {
        this.register(platformType, user);
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
     * @param account 用户帐号
     */
    private void removeUserToken(String platform, String account) {
        userTokenCache.remove(platform + account);
    }

    /**
     * 设置用户令牌
     * @param platform 平台类型
     * @param account 用户帐号
     * @param token  令牌信息
     */
    private void setUserToken(String platform, String account, String token) {
        userTokenCache.put(platform + account, token);
    }
}
