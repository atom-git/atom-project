package com.atom.common.pojo.mapper;

import cn.hutool.core.lang.Validator;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zr
 * @description
 * @date 2020/7/22
 */
@Getter
@AllArgsConstructor
public enum PlatformType implements DbEnum {

	WEB(1, "Web Page"),
	APP(2, "Android Or Ios"),
	PAD(3, "Pad"),
	MP(4, "小程序");

	private final Integer code;
	private final String display;

	/**
	 * 获取匹配的枚举值
	 * @param code 编码
	 * @return 返回匹配的枚举值
	 */
	public static PlatformType match(Integer code) {
		if (code == null) {
			return null;
		}
		for (PlatformType platformType : values()) {
			if (platformType.getCode().equals(code)) {
				return platformType;
			}
		}
		return null;
	}

	/**
	 * 获取匹配的枚举值，默认就返回WEB应用
	 * @param name 枚举名称
	 * @return 返回匹配的枚举值
	 */
	public static PlatformType matchName(String name) {
		if (Validator.isEmpty(name)) {
			return PlatformType.WEB;
		}
		for (PlatformType platformType : values()) {
			if (platformType.name().equalsIgnoreCase(name)) {
				return platformType;
			}
		}
		return PlatformType.WEB;
	}
}
