package com.atom.common.validation;

import cn.hutool.core.lang.Validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @author zr
 * @description 密码校验器8-16位，包括大小写字母、数字、特殊符号
 * @date 2020/7/24
 */
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

	/**
	 * 正则匹配
	 */
	private final Pattern pattern = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$");

	/**
	 * 校验器
	 * @param value 值
	 * @param context 上下文
	 * @return 是否合格
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return Validator.isNotEmpty(value) && pattern.matcher(value).matches();
	}
}
