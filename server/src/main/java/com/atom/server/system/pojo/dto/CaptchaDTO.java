package com.atom.server.system.pojo.dto;

import com.atom.common.pojo.AbsEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zr
 * @description 系统图片验证码DTO
 * @date 2021/4/22
 */
@Getter
@Setter
@ApiModel("系统图片验证码DTO")
public class CaptchaDTO extends AbsEntity {

	@ApiModelProperty("验证码key")
	private String key;

	@ApiModelProperty("图片验证码")
	private String captcha;
}
