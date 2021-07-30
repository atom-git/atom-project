package com.atom.common.pojo.mapper;

/**
 * @author zr
 * @description 数据库值映身对应
 * @date 2020/4/19
 */
public interface DbEnum {

	/**
	 * 取展现值
	 * @return 返回展示值
	 */
	String getDisplay();

	/**
	 * 获取编码值
	 * @return 返回编码
	 */
	Integer getCode();
}
