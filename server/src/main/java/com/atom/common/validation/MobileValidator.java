package com.atom.common.validation;

import cn.hutool.core.lang.Validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @author zr
 * @description 手机号码校验器
 * @date 2020/6/4
 */
public class MobileValidator implements ConstraintValidator<IsMobile, String>{
	/**
	 * 正则匹配
	 */
	private final Pattern pattern = Pattern.compile("^(\\+86)?[1][3456789][0-9]{9}$");

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value == null || pattern.matcher(value).matches();
	}

	/**
	 * 用作校验器
	 * @param value 值
	 * @return 是否合格
	 */
	public boolean isValid(String value) {
		return Validator.isNotEmpty(value) && pattern.matcher(value).matches();
	}
}
