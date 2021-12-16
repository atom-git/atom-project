package com.atom.common.security.cache;

import cn.hutool.core.lang.Validator;
import com.alibaba.fastjson.JSONObject;
import com.atom.common.pojo.mapper.PlatformType;
import com.atom.common.security.SessionUser;
import com.atom.common.util.RedisUtil;
import com.atom.server.system.entity.SysUser;
import org.springframework.stereotype.Component;

/**
 * @author zr
 * @description 用于缓存用户的登录信息
 * @date 11/01/2018
 */
@Component
public class RedisUserCacheStore implements IUserCacheStore {

    /**
     * token查询用户缓存key
     */
    private static final String TOKEN_USER_KEY = "TOKEN_CACHE";

    /**
     * 用户查询token缓存key
     */
    private static final String USER_TOKEN_KEY = "USER_CACHE";

    /**
     * 注册用户登录信息，如果用户登录过则把之前的登录信息失效掉，如果没有登录则插入登录信息
     * @param platformType 平台类型
     * @param user 用户信息
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
            setTokenUser(platform, user.getToken(), user);
            setUserToken(platform, user.getId(), user.getToken());
        } else {
            setTokenUser(platform, user.getToken(), user);
            setUserToken(platform, user.getId(), user.getToken());
        }
    }

    /**
     * 根据token清理用户的登录信息
     * @param platformType 平台类型
     * @param token 用户token
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
     * 获取token对应的用户信息
     * @param platformType 平台类型
     * @param token 用户token
     * @return sessionUser
     */
    @Override
    public SessionUser getTokenUser(PlatformType platformType, String token) {
        return JSONObject.parseObject(JSONObject.toJSONString(RedisUtil.get(platformType.name() + "_" + TOKEN_USER_KEY, token)), SessionUser.class);
    }

    /**
     * 读取用户token
     * @param platformType 平台类型
     * @param user 用户
     * @return 令牌
     */
    @Override
    public String getUserToken(PlatformType platformType, SessionUser user) {
        return RedisUtil.get(platformType.name() + "_" + USER_TOKEN_KEY, user.getId() + "").toString();
    }

    /**
     * 读取用户token
     * @param platformType 平台类型
     * @param userId 用户帐号
     * @return 令牌
     */
    @Override
    public String getUserToken(PlatformType platformType, Integer userId) {
        Object token = RedisUtil.get(platformType.name() + "_" + USER_TOKEN_KEY, userId + "");
        return token != null ? token.toString() : "";
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
     * 是否存在用户的token信息
     * @param platformType 平台类型
     * @param token token
     * @return 是否存在
     */
    @Override
    public boolean existTokenUser(PlatformType platformType, String token) {
        return RedisUtil.exists(platformType.name() + "_" + TOKEN_USER_KEY, token);
    }

    /**
     * 是否存在用户的token信息
     * @param platformType 平台类型
     * @param userId 用户帐号
     * @return 是否存在
     */
    @Override
    public boolean existUserToken(PlatformType platformType, Integer userId) {
        return RedisUtil.exists(platformType.name() + "_" + USER_TOKEN_KEY, userId + "");
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
     * 验证码写入到缓存中
     * @param phoneNum   手机号码
     * @param verifyCode 验证码
     */
    public void setVerifyCode(String phoneNum, String verifyCode) {
        RedisUtil.set(phoneNum, verifyCode, 300L);
    }

    /**
     * 从缓存中读出验证码
     * @param phoneNum 手机号码
     * @return 获取验证码
     */
    public String getVerifyCode(String phoneNum) {
        return RedisUtil.getString(phoneNum);
    }

    /**
     * 设置用户token信息
     * @param platform 平台
     * @param userId 用户帐号
     * @param token 用户token
     */
    private void setUserToken(String platform, Integer userId, String token) {
        RedisUtil.setMap(platform + USER_TOKEN_KEY, userId + "", token);
    }

    /**
     * 清除用户的token信息
     * @param platform 平台
     * @param userId 用户帐号
     */
    private void removeUserToken(String platform, Integer userId) {
        RedisUtil.remove(platform + USER_TOKEN_KEY, userId + "");
    }

    /**
     * 设置用户缓存信息
     * @param platform 平台
     * @param token 用户token
     * @param user  sessionuser
     */
    private void setTokenUser(String platform, String token, SessionUser user) {
        RedisUtil.setMap(platform + TOKEN_USER_KEY, token, user);
    }

    /**
     * 清除用户的缓存信息
     * @param platform 平台
     * @param token 用户token
     */
    private void removeTokenUser(String platform, String token) {
        RedisUtil.remove(platform + TOKEN_USER_KEY, token);
    }
}
