package com.atom.server.system.controller;

import cn.hutool.core.util.RandomUtil;
import com.atom.common.pojo.annotation.Permission;
import com.atom.common.pojo.http.RestResponse;
import com.atom.common.pojo.mapper.ActionType;
import com.atom.common.pojo.mapper.GrantType;
import com.atom.common.security.SessionUser;
import com.atom.common.util.RedisUtil;
import com.atom.server.system.pojo.dto.CaptchaDTO;
import com.atom.server.system.pojo.dto.ForgetDTO;
import com.atom.server.system.pojo.dto.SignInDTO;
import com.atom.server.system.pojo.dto.SignUpDTO;
import com.atom.server.system.pojo.vo.CaptchaVO;
import com.atom.server.system.service.ISystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zr
 * @description 第三方登录随机串、第三方回调、系统注册、获取验证码、校验验证码、忘记密码、登出、短信验证码公共模块控制器
 * 登录功能由SpringSecurity拦截，仅实现Service
 * @date 2021/4/22
 */
@RestController
@RequestMapping("/system")
@Api("系统管理")
@Permission
@Slf4j
public class SystemController {

	/**
	 * 系统服务
	 */
	@Resource
	private ISystemService systemService;

	/**
	 * 系统注册
	 * @param signUpDTO 系统注册DTO
	 * @return 返回是否注册成功
	 */
	@PostMapping("sign/up")
	@ApiOperation("系统注册")
	@Permission(actionType = ActionType.N, grantType = GrantType.AUTO)
	public RestResponse<?> signUp(@RequestBody SignUpDTO signUpDTO) {
		systemService.signUp(signUpDTO);
		return RestResponse.success();
	}

	/**
	 * 获取图片验证码
	 * @return 返回图片验证码
	 */
	@GetMapping("captcha")
	@ApiOperation("生成验证码")
	@Permission(actionType = ActionType.Q, grantType = GrantType.AUTO)
	public RestResponse<CaptchaVO> captcha() {
		CaptchaVO captchaVO = systemService.captcha();
		return RestResponse.success(captchaVO);
	}

	/**
	 * 校验图片验证码
	 * @param captchaDTO 系统图片验证码DTO
	 * @return 返回验证码
	 */
	@PostMapping("judge/captcha")
	@ApiOperation("校验图片验证码")
	@Permission(actionType = ActionType.Q, grantType = GrantType.AUTO)
	public RestResponse<Boolean> judgeCaptcha(@RequestBody CaptchaDTO captchaDTO) {
		return RestResponse.success(systemService.judgeCaptcha(captchaDTO));
	}

	/**
	 * 忘记密码，仅支持portal的密码修改，dashboard需联系管理员重置，然后登录后进行修改
	 * @param forgetDTO 忘记密码DTO
	 * @return 返回密码是否修改成功
	 */
	@PostMapping("forget/password")
	@ApiOperation("忘记密码")
	@Permission(actionType = ActionType.E, grantType = GrantType.AUTO)
	public RestResponse<?> forgetPassword(@RequestBody ForgetDTO forgetDTO) {
		systemService.forgetPassword(forgetDTO);
		return RestResponse.success();
	}

	/**
	 * 系统用户登出
	 * @param request 请求
	 * @param response 响应
	 * @return 操作结果
	 */
	@PostMapping("logout")
	@ApiOperation("系统用户登出")
	@Permission(actionType = ActionType.E, grantType = GrantType.AUTO)
	public RestResponse<?> logout(HttpServletRequest request, HttpServletResponse response) {
		systemService.logout(request, response);
		return RestResponse.success();
	}

	/**
	 * 发送验证码 TODO 往邮箱发送验证码
	 * @param phone 用户手机号
	 * @return 返回短信验证码是否发送成功
	 */
	@PostMapping("send/{phone}/verifyCode")
	@ApiOperation("发送短信验证码")
	@Permission(actionType = ActionType.N, grantType = GrantType.AUTO)
	public RestResponse<?> verifyCode(@PathVariable String phone) {
		// 生成6位短信验证码
		String verifyCode = RandomUtil.randomNumbers(6);
		// TODO 发送短信验证码，发送成功时设置缓存
		// 验证码过期时间5分钟
		RedisUtil.set(phone, verifyCode, 300L);
		return RestResponse.success();
	}

	/**
	 * 生成第三方登录随机串
	 * @return 返回第三方登录随机串
	 */
	@GetMapping("third/state")
	@ApiOperation("第三方登录随机串")
	@Permission(actionType = ActionType.Q, grantType = GrantType.AUTO)
	public RestResponse<String> thirdState() {
		String state = RandomUtil.randomStringUpper(32);
		RedisUtil.set(state, 1, 300L);
		return RestResponse.success(state);
	}

	/**
	 * 第三方登录回调方法
	 * @param request 请求
	 * @param platform 平台参数 portal/dashboard
	 * @param signInDTO 第三方登录授权临时票据,crf校验串
	 * @return 第三方登录回调方法,返回token令牌
	 */
	@PostMapping("third/sign/in")
	@ApiOperation("第三方登录回调方法")
	@Permission(actionType = ActionType.E, grantType = GrantType.AUTO)
	public RestResponse<SessionUser> thirdSignIn(HttpServletRequest request, @RequestHeader String platform, @RequestBody SignInDTO signInDTO) {
		return RestResponse.success(systemService.thirdSignIn(request, platform, signInDTO));
	}
}
