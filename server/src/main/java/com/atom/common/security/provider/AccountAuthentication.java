package com.atom.common.security.provider;

import com.atom.common.pojo.mapper.PlatformType;
import com.atom.server.system.pojo.dto.SignInDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * @author zr
 * @description 用户登录信息认证
 * @date 2021/4/22
 */
@Getter
@Setter
public class AccountAuthentication extends UsernamePasswordAuthenticationToken {

	/**
	 * 平台，登录的平台，这里的平台用来区别门户还是管理后台 dashboard,portal
	 */
	private String platform;

	/**
	 * 平台类型
	 */
	private PlatformType platformType;

	/**
	 * 登录的用户信息
	 */
	private SignInDTO signUser;

	/**
	 * 复写构造函数
	 * @param principal 用户
	 * @param credentials 证书
	 */
	public AccountAuthentication(Object principal, Object credentials, SignInDTO signUser) {
		super(principal, credentials);
		this.signUser = signUser;
	}
}
