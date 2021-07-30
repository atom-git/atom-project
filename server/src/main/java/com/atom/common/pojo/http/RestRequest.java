package com.atom.common.pojo.http;

import cn.hutool.core.bean.BeanUtil;
import com.atom.common.pojo.mapper.ActionType;
import com.atom.common.pojo.mapper.PlatformType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author zr
 * @description rest请求信息
 * @date 2021/1/28
 */
@Getter
@Setter
@AllArgsConstructor
public class RestRequest {
	/**
	 * 请求url
	 */
	private String url;
	/**
	 * 请求参数
	 */
	private Map<String, Object> params;
	/**
	 * 平台类型
	 */
	private PlatformType platformType;
	/**
	 * 请求的操作类型
	 */
	private ActionType[] actionTypes;
	/**
	 * 请求执行状态
	 */
	private Integer status;
	/**
	 * 请求执行时长
	 */
	private Long executionTime;
	/**
	 * 请求异常
	 */
	private String exception;
	/**
	 * 请求结果
	 */
	private RestResponse<?> result;

	/**
	 * request的构造方法
	 * @param url 请求url
	 * @param params 请求参数
	 * @param platformType 平台类型
	 * @param actionTypes 请求的操作类型
	 */
	public RestRequest(String url, Map<String, Object> params, PlatformType platformType, ActionType[] actionTypes) {
		this.url = url;
		this.params = params;
		this.platformType = platformType;
		this.actionTypes = actionTypes;
	}

	/**
	 * request的构造方法
	 * @param url 请求url
	 * @param params 请求参数
	 * @param platformType 平台类型
	 * @param actionTypes 请求的操作类型
	 */
	public RestRequest(String url, Object params, PlatformType platformType, ActionType[] actionTypes) {
		this.url = url;
		this.params = BeanUtil.beanToMap(params);
		this.platformType = platformType;
		this.actionTypes = actionTypes;
	}

	/**
	 * 设置返回结果时同时设置状态码
	 * @param result 返回结果
	 */
	public void setResult(RestResponse<?> result) {
		this.result = result;
		this.status = result.getStatus();
	}

	/**
	 * 设置返回结果时同时设置状态码
	 * @param exception 处理异常
	 */
	public void setException(String exception) {
		this.exception = exception;
		this.status = 400;
	}
}
