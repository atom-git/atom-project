package com.atom.common.pojo.exception;

import com.atom.common.pojo.http.RestError;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zr
 * @description 服务层的业务异常
 * @date 2019/5/27
 */
@Getter
@Setter
public class BusException extends RuntimeException {
    /**
     * 转化异常信息
     */
    private RestError error;

    /**
     * 通过可预知的错误构建异常，业务系统中所有的问题均以异常形式返回，可以起到事务回滚的作用
     * @param error 可预知错误
     */
    public BusException(RestError error) {
        super(error.getErrorMsg());
        this.error = error;
    }

    /**
     * 通过可预知的错误构建异常，业务系统中所有的问题均以异常形式返回，可以起到事务回滚的作用
     * @param error 可预知错误
     * @param errorMsg 错误信息
     */
    public BusException(RestError error, String errorMsg) {
        super(errorMsg);
        this.error = error;
    }

    /**
     * 不可预知的错误构建异常，用于不可预知错误的全局处理，【实际情况下判断errorMsg是否可以覆盖】
     * @param e 不可预条错误
     */
    public BusException(String errorMsg, Exception e) {
        super(errorMsg, e);
    }
}
