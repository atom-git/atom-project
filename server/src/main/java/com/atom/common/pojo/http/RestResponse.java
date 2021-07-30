package com.atom.common.pojo.http;

import com.atom.common.pojo.AbsEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author zr
 * @description rest返回响应
 * @date 2020/3/10
 */
@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "Rest接口响应对象")
@Slf4j
public class RestResponse<T> extends AbsEntity {

	/**
	 * 服务请求成功
	 */
	private static final Integer SUCCESS = 200;
	/**
	 * 服务请求无法识别
	 */
	private static final Integer ERROR = 400;
	/**
	 * 服务请求异常
	 */
	private static final Integer EXCEPTION = 500;

	/**
	 * 请求状态码
	 */
	@ApiModelProperty("请求响应状态：200(成功), 400无法识别，500请求异常")
	private Integer status = 200;

	/**
	 * 错误编码
	 */
	@ApiModelProperty("错误结果码")
	private String errorCode;

	/**
	 * 错误信息
	 */
	@ApiModelProperty("错误详细信息")
	private String errorMsg;

	/**
	 * 数据
	 */
	@ApiModelProperty("响应具体数据")
	private T data;

	/**
	 * 正常响应
	 * status为200
	 * @param data 响应数据
	 */
	public RestResponse(T data) {
		this.status = RestResponse.SUCCESS;
		this.data = data;
	}

	/**
	 * 错误响应
	 * RestError一定是500
	 * @param error 可预知的业务逻辑错误
	 */
	public RestResponse(RestError error) {
		this.status = RestResponse.EXCEPTION;
		this.errorCode = error.getErrorCode();
		this.errorMsg = error.getErrorMsg();
	}

	/**
	 * 错误响应 主要针对500类错误请求的情况，用于全局的异常处理，统一为9000类错误
	 * @param errorMsg 错误信息
	 */
	public RestResponse(String errorMsg) {
		this.status = RestResponse.EXCEPTION;
		this.errorCode = RestError.ERROR9000.getErrorCode();
		this.errorMsg = errorMsg;
	}

	/**
	 * 错误响应 主要针对500类错误请求的情况，用于全局的异常处理，统计为9000类错误
	 * @param error 可预知的业务逻辑错误
	 * @param errorMsg 错误信息
	 */
	public RestResponse(RestError error, String errorMsg) {
		this.status = RestResponse.EXCEPTION;
		this.errorCode = error.getErrorCode();
		this.errorMsg = errorMsg;
	}

	/**
	 * 错误响应 主要针对400类错误的情况
	 * @param e 系统异常
	 */
	public RestResponse(Exception e) {
		this.status = RestResponse.ERROR;
		this.errorCode = RestError.ERROR9000.getErrorCode();
		this.errorMsg = e.getMessage();
	}

	/**
	 * 无数据的成功响应
	 * @return 返回响应结果
	 */
	public static RestResponse<?> success() {
		return new RestResponse<>();
	}

	/**
	 * 成功的响应
	 * @param data 类型为T的数据
	 * @param <T> 数据泛型
	 * @return 返回成功的响应
	 */
	public static <T> RestResponse<T> success(T data) {
		return new RestResponse<>(data);
	}

	/**
	 * 服务异常的响应
	 * @param error 服务异常
	 * @return 返回异常的响应
	 */
	public static RestResponse<RestError> error(RestError error) {
		return new RestResponse<>(error);
	}

	/**
	 * 服务异常的响应
	 * @param error 服务异常
	 * @param e 服务业务异常
	 * @return 返回异常的响应
	 */
	public static RestResponse<RestError> error(RestError error, Exception e) {
		return new RestResponse<>(error, e.getMessage());
	}

	/**
	 * 服务异常的响应
	 * @param error 服务异常
	 * @param errorMsg 错误信息
	 * @return 返回异常的响应
	 */
	public static RestResponse<RestError> error(RestError error, String errorMsg) {
		return new RestResponse<>(error, errorMsg);
	}


	/**
	 * 服务器的响应 主要针对400类错误请求的情况，用于全局的异常处理，统计为9000类错误
	 * @param e 服务业务异常
	 * @return 返回异常的响应
	 */
	public static RestResponse<?> error(Exception e) {
		return new RestResponse<>(e);
	}

	/**
	 * 将自身当成json串写出去
	 * @param response 响应
	 */
	public void writeAsJson(HttpServletResponse response) {
		String jsonStr = this.toString();
		response.setHeader("Pragma", "No-app");
		response.setHeader("Cache-Control", "no-app");
		response.setHeader("Content-Type", "application/json; charset=UTF-8");
		//允许ajax跨域的参数设置
		response.setHeader("Access-Control-Allow-Origin", "*");
		try (PrintWriter outter = response.getWriter()) {
			outter.write(jsonStr);
			outter.flush();
		} catch (IOException e) {
			log.error("Rest响应【writeAsJson】写出失败：", e);
		}
	}
}
