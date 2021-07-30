package com.atom.server.system.pojo.dto;

import cn.hutool.core.date.DateUtil;
import com.atom.common.dao.Converter;
import com.atom.common.pojo.AbsEntity;
import com.atom.common.pojo.GlobalConstant;
import com.atom.common.validation.IsMobile;
import com.atom.common.validation.ValidPassword;
import com.atom.server.system.entity.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotEmpty;

/**
 * @author zr
 * @description 系统注册DTO
 * @date 2021/4/22
 */
@Getter
@Setter
@ApiModel("系统注册DTO")
public class SignUpDTO extends AbsEntity {

	@NotEmpty
	@ApiModelProperty("帐户")
	private String account;

	@NotEmpty
	@IsMobile
	@ApiModelProperty("手机号")
	private String phone;

	@NotEmpty
	@ValidPassword
	@ApiModelProperty("密码")
	private String password;

	@NotEmpty
	@Length(min = 6, max = 6)
	@ApiModelProperty("短信验证码")
	private String verifyCode;


	public static class DTOConverter extends Converter<SysUser, SignUpDTO> {
		@Override
		public SysUser doForward(SignUpDTO signUpDTO) {
			if (signUpDTO == null) {
				return null;
			}
			SysUser sysUser = new SysUser();
			BeanUtils.copyProperties(signUpDTO, sysUser);
			sysUser.setName(GlobalConstant.DEFAULT_NICKNAME);
			sysUser.setCreateTime(DateUtil.date());
			// 后台用户不允许注册
			sysUser.setPlatform(GlobalConstant.PLATFORM_PORTAL);
			return sysUser;
		}
	}
}
