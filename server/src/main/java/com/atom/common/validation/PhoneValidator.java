package com.atom.common.validation;

import cn.hutool.core.lang.Validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @author zr
 * @description 手机或者固话号码校验器
 * @date 2020/6/4
 */
public class PhoneValidator implements ConstraintValidator<IsPhone, String>{
	/**
	 * 正则匹配
	 */
	private final Pattern pattern = Pattern.compile("(^(0[0-9]{2,3}-)?([2-9][0-9]{6,7})+(-[0-9]{1,4})?$)|(^((\\d3)|(\\d{3}-))?(1[35678]\\d{9})$)");

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
