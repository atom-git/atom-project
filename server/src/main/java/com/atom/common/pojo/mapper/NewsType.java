package com.atom.common.pojo.mapper;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zr
 * @description 系统信息类型1通知，2消息，3待办
 * @date 2021/8/23
 */
@Getter
@AllArgsConstructor
public enum NewsType implements DbEnum {

	NOTICE(1, "通知"),
	MESSAGE(2, "消息"),
	TODO(3, "待办");

	private final Integer code;
	private final String display;

	/**
	 * 获取匹配的枚举值
	 * @param code 编码
	 * @return 返回匹配的枚举值
	 */
	public static NewsType match(Integer code) {
		if (code == null) {
			return null;
		}
		for (NewsType newsType : values()) {
			if (newsType.getCode().equals(code)) {
				return newsType;
			}
		}
		return null;
	}
}
