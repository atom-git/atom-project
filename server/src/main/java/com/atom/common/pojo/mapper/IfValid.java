package com.atom.common.pojo.mapper;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zr
 * @description 是否有效的枚举类
 * @date 2020/4/19
 */
@Getter
@AllArgsConstructor
public enum IfValid implements DbEnum {

	VALID(1, "有效"),
	INVALID(0, "无效");

	private final Integer code;
	private final String display;

	/**
	 * 获取匹配的枚举值
	 * @param code 编码
	 * @return 返回匹配的枚举值
	 */
	public static IfValid match(Integer code) {
		if (code == null) {
			return null;
		}
		for (IfValid ifValid : values()) {
			if (ifValid.getCode().equals(code)) {
				return ifValid;
			}
		}
		return null;
	}
}
