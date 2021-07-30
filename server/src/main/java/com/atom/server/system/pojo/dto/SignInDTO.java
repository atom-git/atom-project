package com.atom.server.system.pojo.dto;

import com.atom.common.pojo.AbsEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zr
 * @description 系统登录DTO
 * @date 2021/4/22
 */
@Getter
@Setter
@ApiModel("系统登录DTO")
public class SignInDTO extends AbsEntity {

	@ApiModelProperty("登录方式类型account帐号密码登录 phone手机验证码登录")
	private String type;
	@ApiModelProperty("用户帐户")
	private String account;
	@ApiModelProperty("手机号码")
	private String phone;
	@ApiModelProperty("密码")
	private String password;
	@ApiModelProperty("短信验证码")
	private String verifyCode;
	@ApiModelProperty("第三方登录的方式qq,wechat...")
	private String thirdType;
	@ApiModelProperty("第三方临时授权code")
	private String code;
	@ApiModelProperty("回调状态保持state")
	private String state;
}
