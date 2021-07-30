package com.atom.server.system.pojo.vo;

import com.atom.common.pojo.AbsEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zr
 * @description 系统图片验证码VO
 * @date 2021/4/22
 */
@Getter
@Setter
@ApiModel("系统图片验证码VO")
@AllArgsConstructor
public class CaptchaVO extends AbsEntity {

	@ApiModelProperty("验证码key")
	private String key;

	@ApiModelProperty("验证码base64图片")
	private String img;
}
