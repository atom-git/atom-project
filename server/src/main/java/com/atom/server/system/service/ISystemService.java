package com.atom.server.system.service;

import com.atom.common.security.SessionUser;
import com.atom.server.system.pojo.dto.CaptchaDTO;
import com.atom.server.system.pojo.dto.ForgetDTO;
import com.atom.server.system.pojo.dto.SignInDTO;
import com.atom.server.system.pojo.dto.SignUpDTO;
import com.atom.server.system.pojo.vo.CaptchaVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zr
 * @description 系统登录、注册、获取验证码、校验验证码、忘记密码、登出公共模块服务接口
 * @date 2021/4/22
 */
public interface ISystemService {

	SessionUser signIn(String platform, SignInDTO signInDTO);

	void signUp(SignUpDTO signUpDTO);

	CaptchaVO captcha();

	Boolean judgeCaptcha(CaptchaDTO captchaDTO);

	void forgetPassword(ForgetDTO forgetDTO);

	void logout(HttpServletRequest request, HttpServletResponse response);

	SessionUser thirdSignIn(HttpServletRequest request, String platform, SignInDTO signInDTO);

}
