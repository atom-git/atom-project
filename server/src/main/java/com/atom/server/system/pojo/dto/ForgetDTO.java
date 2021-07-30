package com.atom.server.system.pojo.dto;

import com.atom.common.pojo.AbsEntity;
import com.atom.common.validation.IsMobile;
import com.atom.common.validation.ValidPassword;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * @author zr
 * @description 忘记密码DTO
 * @date 2021/4/22
 */
@Getter
@Setter
@ApiModel("忘记密码DTO")
public class ForgetDTO extends AbsEntity {

	@NotEmpty
	@ApiModelProperty("帐户")
	private String account;
	@NotEmpty
	@IsMobile
	@ApiModelProperty("手机号")
	private String phone;
	@NotEmpty
	@Length(min = 6, max = 6)
	@ApiModelProperty("短信验证码")
	private String verifyCode;
	@NotEmpty
	@ValidPassword
	@ApiModelProperty("密码")
	private String password;
}
