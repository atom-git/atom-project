package com.atom.common.pojo.mapper;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zr
 * @description 访问类型的枚举类
 * @date 2020/6/12
 */
@Getter
@AllArgsConstructor
public enum ActionType implements DbEnum {
	Q(1, "查询"),
	N(2, "新增"),
	E(3, "编辑"),
	D(4, "删除");

	private final Integer code;
	private final String display;

	/**
	 * 获取匹配的枚举值
	 * @param code 编码
	 * @return 返回匹配的枚举值
	 */
	public static ActionType match(Integer code) {
		if (code == null) {
			return null;
		}
		for (ActionType actionType : values()) {
			if (actionType.getCode().equals(code)) {
				return actionType;
			}
		}
		return null;
	}
}
