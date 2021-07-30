package com.atom.common.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author zr
 * @description 有效的密码校验
 * @date 2020/7/24
 */
@Constraint(validatedBy = PasswordValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidPassword {

	boolean required() default true;

	String message() default "密码不符合要求";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
