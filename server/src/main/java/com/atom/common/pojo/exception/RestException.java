package com.atom.common.pojo.exception;

import com.atom.common.pojo.http.RestError;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zr
 * @description 定义服务异常，业务逻辑错误以异常抛出，在aspect中进行异常公共处理
 * @date 2020/3/10
 */
@Getter
@Setter
public class RestException extends RuntimeException {

	/**
	 * 可预知异常
	 */
	private RestError error;

	/**
	 * 通过可预知的错误构建异常，业务系统中所有的问题均以异常形式返回，可以起到事务回滚的作用
	 * @param error 可预知错误
	 */
	public RestException(RestError error) {
		super();
		this.error = error;
	}

	/**
	 * 不可预知的错误构建异常，用于不可预知错误的全局处理，【实际情况下判断errorMsg是否可以覆盖】
	 * @param e 不可预条错误
	 */
	public RestException(String errorMsg, Exception e) {
		super(errorMsg, e);
	}
}
