package com.atom.common.pojo.annotation;

import com.atom.common.pojo.mapper.ActionType;
import com.atom.common.pojo.mapper.GrantType;

import java.lang.annotation.*;

/**
 * @author zr
 * @description 操作认证的操作类型和授权类型
 * @date 2020/6/15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@Documented
public @interface Permission {

	/**
	 * 操作类型
	 * @return ActionType
	 */
	ActionType[] actionType() default ActionType.Q;

	/**
	 * 授权类型
	 * @return GrantType
	 */
	GrantType grantType() default GrantType.AUTO;
}
