package com.atom.common.security.cache;

import com.atom.common.pojo.mapper.PlatformType;
import com.atom.common.security.SessionUser;

/**
 * @author zr
 * @description 用户缓存仓库
 * @date 2019-08-12
 */
public interface IUserCacheStore {

    /**
     * 注册缓存用户
     * @param platformType 平台类型
     * @param user 用户对象
     */
    void register(PlatformType platformType, SessionUser user);

    /**
     * 移除缓存用户
     * @param platformType 平台类型
     * @param token 访问令牌
     */
    void unRegister(PlatformType platformType, String token);

    /**
     * 读取用户信息
     * @param platformType 平台类型
     * @param token 令牌
     * @return 用户信息
     */
    SessionUser getTokenUser(PlatformType platformType, String token);

    /**
     * 读取用户token
     * @param platformType 平台类型
     * @param user 用户
     * @return 令牌
     */
    String getUserToken(PlatformType platformType, SessionUser user);

    /**
     * 检测令牌是否有效
     * @param platformType 平台类型
     * @param token 令牌
     * @return 是否有效
     */
    boolean existTokenUser(PlatformType platformType, String token);

    /**
     * 是否存在用户的token信息
     * @param platformType 平台类型
     * @param account 用户帐户
     * @return 是否存在
     */
    boolean existUserToken(PlatformType platformType, String account);

    /**
     * 刷新用户信息
     * @param platformType 平台
     * @param user 用户
     */
    void flushTokenUser(PlatformType platformType, SessionUser user);
}
