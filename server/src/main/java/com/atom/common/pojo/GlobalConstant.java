package com.atom.common.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zr
 * @description 全局常量
 * @date 2020/6/4
 */
@Component
@SuppressWarnings("unused")
public class GlobalConstant {
	/**
	 * 生成密码的基础字符串
	 */
	public static final String PASSWORD_BASE_STR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@#!%&+";

	/**
	 * 默认用户名称
	 */
	public static String DEFAULT_NICKNAME;

	@Value("${atom.default.nick-name}")
	public void setDefaultNickname(String defaultNickname) {
		DEFAULT_NICKNAME = defaultNickname;
	}

	/**
	 * 默认文件路径
	 */
	public static String DEFAULT_FILE_PATH;

	@Value("${atom.default.file.path}")
	public void setDefaultFilePath(String defaultFilePath) {
		DEFAULT_FILE_PATH = defaultFilePath;
	}

	/**
	 * 图片验证码缓存KEY
	 */
	public static final String CAPTCHA_CACHE = "CAPTCHA_CACHE";

	/**
	 * platform:门户平台
	 */
	public static final String PLATFORM_PORTAL = "portal";
	/**
	 * platform:管理平台
	 */
	public static final String PLATFORM_DASHBOARD = "dashboard";

	/**
	 * 第三方登录方式:qq
	 */
	public static final String THIRD_LOGIN_QQ = "qq";
	/**
	 * 第三方登录方式:wechat
	 */
	public static final String THIRD_LOGIN_WECHAT = "wechat";

}
