package com.atom.common.pojo.mapper;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zr
 * @description 授权方式的枚举类
 * @date 2020/6/12
 */
@Getter
@AllArgsConstructor
public enum GrantType implements DbEnum {

	MANUAL(0, "手动"),
	AUTO(1, "默认");

	private final Integer code;
	private final String display;

	/**
	 * 获取匹配的枚举值
	 * @param code 编码
	 * @return 返回匹配的枚举值
	 */
	public static GrantType match(Integer code) {
		if (code == null) {
			return null;
		}
		for (GrantType grantType : values()) {
			if (grantType.getCode().equals(code)) {
				return grantType;
			}
		}
		return null;
	}
}
