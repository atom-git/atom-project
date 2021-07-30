package com.atom.common.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author zr
 * @description 手机号码校验注解
 * @date 2020/6/4
 */
@Constraint(validatedBy = MobileValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IsMobile {

	boolean required() default true;

	String message() default "手机号码式不合法";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
