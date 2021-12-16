package com.atom.common.security.cache;

import com.atom.common.pojo.mapper.PlatformType;
import com.atom.common.security.SessionUser;
import com.atom.server.system.entity.SysUser;

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
     * 移除缓存用户
     * @param platformType 平台类型
     * @param userId 用户序号
     */
    void unRegister(PlatformType platformType, Integer userId);

    /**
     * 移除全平台缓存用户
     * @param userId 用户序号
     */
    void unRegister(Integer userId);

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
     * 读取用户token
     * @param platformType 平台类型
     * @param userId 用户序号
     * @return 令牌
     */
    String getUserToken(PlatformType platformType, Integer userId);

    /**
     * 获取用户Session
     * @param platformType 平台类型
     * @param userId 用户序号
     * @return 用户Session
     */
    SessionUser getUserSession(PlatformType platformType, Integer userId);

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
     * @param userId 用户序号
     * @return 是否存在
     */
    boolean existUserToken(PlatformType platformType, Integer userId);

    /**
     * 刷新用户信息
     * @param platformType 平台
     * @param user 用户
     */
    void flushSession(PlatformType platformType, SessionUser user);

    /**
     * 根据用户Id刷新全平台用户信息
     * @param sysUser 用户信息
     */
    void flushSession(SysUser sysUser);
}
