package com.atom.common.pojo.mapper;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zr
 * @description 系统日志类型
 * @date 2021/1/28
 */
@Getter
@AllArgsConstructor
public enum LogType implements DbEnum {

	AUTH(1, "认证登录"),
	SERVE(2, "服务调用"),
	SYNC(3, "数据同步");

	private final Integer code;
	private final String display;

	/**
	 * 获取匹配的枚举值
	 * @param code 编码
	 * @return 返回匹配的枚举值
	 */
	public static LogType match(Integer code) {
		if (code == null) {
			return null;
		}
		for (LogType logType : values()) {
			if (logType.getCode().equals(code)) {
				return logType;
			}
		}
		return null;
	}
}
