package com.atom.common.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zr
 * @description 第三方登录相关配置信息
 * @date 2021/4/22
 */
@Component
public class ThirdLogin {

	/**
	 * 微信登录配置
	 */
	public static class WechatLogin {
		public static String APP_ID;
		public static String APP_SECRET;
		public static String ACCESS_TOKEN_URL;
		public static String USER_URL;
		public static String ACCESS_TOKEN_KEY = "access_token";
		public static String OPEN_ID_KEY = "openid";
		public static String NICK_NAME_KEY = "nickname";
		public static String HEAD_KEY = "headimgurl";

		@Value("${atom.third.login.tencent.wechat.app-id}")
		public void setAppId(String appId) {
			APP_ID = appId;
		}
		@Value("${atom.third.login.tencent.wechat.app-secret}")
		public void setAppSecret(String appSecret) {
			APP_SECRET = appSecret;
		}
		@Value("${atom.third.login.tencent.wechat.access-token-url}")
		public void setAccessTokenUrl(String accessTokenUrl) {
			ACCESS_TOKEN_URL = accessTokenUrl;
		}
		@Value("${atom.third.login.tencent.wechat.user-url}")
		public void setUserUrl(String userUrl) {
			USER_URL = userUrl;
		}
	}
}
