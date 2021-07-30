package com.atom.common.pojo.http;

import lombok.Getter;

/**
 * @author zr
 * @description 可预知错误枚举类
 * @date 2020/3/10
 */
@Getter
public enum RestError {

	/**
	 * 系统登录注册
	 */
	ERROR1000("1000", "帐户或密码错误"),
	ERROR1001("1001", "用户已经注册，请直接登录"),
	ERROR1002("1002", "密码不符合要求"),
	ERROR1003("1003", "请输入正确的验证码"),
	ERROR1004("1004", "用户权限未分配"),
	ERROR1005("1005", "第三方登录授权异常"),
	ERROR1006("1006", "请检查帐户是否正确"),
	ERROR1007("1007", "原始密码输入错误"),
	ERROR1008("1008", "手机号或者验证码错误"),
	ERROR1009("1009", "手机号码已注册，请直接登录"),

	/**
	 * 系统管理相关
	 */
	ERROR2000("2000", "系统管理功能异常"),

	/**
	 * 短信相关
	 */
	ERROR3000("3000", "短信发送号码不正确"),
	ERROR3001("3001", "短信模板不存在"),
	ERROR3002("3002", "短信发送参数与模板不一致"),
	ERROR3003("3003", "短信发送Api调用异常"),

	/**
	 * 全局的异常
	 */
	ERROR9000("9000", "服务器响应异常"),
	ERROR9001("9001", "无效请求或参数不符合约定"),
	ERROR9002("9002", "非法请求，没有操作权限"),
	ERROR9004("9004", "登录已过期，请重新登录");

	/**
	 * 错误码
	 */
	private final String errorCode;
	/**
	 * 错误信息
	 */
	private final String errorMsg;

	RestError(String errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	@Override
	public String toString() {
		return "[" + this.name() + "] : {errorCode:[" + this.getErrorCode() + "],errorMsg:[" + this.getErrorMsg() + "]}";
	}
}
