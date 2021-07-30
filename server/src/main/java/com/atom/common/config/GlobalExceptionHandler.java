package com.atom.common.config;

import com.alibaba.fastjson.JSONObject;
import com.atom.common.pojo.exception.BusException;
import com.atom.common.pojo.exception.RestException;
import com.atom.common.pojo.http.RestError;
import com.atom.common.pojo.http.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zr
 * @description 全局异常响应拦截
 * @date 2020/3/10
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 拦截权限不足的异常
     * @param request 请求
     * @param e 权限不足异常
     * @return 响应
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(AccessDeniedException.class)
    public RestResponse<RestError> handleAccessDeniedException(HttpServletRequest request, AccessDeniedException e) {
        log.error("【无权访问】请求：{}, params:{}, 原因：{}", request.getRequestURI(), JSONObject.toJSONString(request.getParameterMap()), e);
        return RestResponse.error(RestError.ERROR9002);
    }

    /**
     * 拦截服务异常
     * @param request 请求
     * @param e 服务异常
     * @return 响应
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(RestException.class)
    public RestResponse<?> handleRestException(HttpServletRequest request, RestException e) {
        log.error("【业务异常】请求：{}, params:{}, 原因：{}", request.getRequestURI(), JSONObject.toJSONString(request.getParameterMap()), e);
        return RestResponse.error(e);
    }

    /**
     * 拦截服务异常
     * @param request 请求
     * @param e 服务异常
     * @return 响应
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BusException.class)
    public RestResponse<?> handleBusException(HttpServletRequest request, BusException e) {
        log.error("【业务异常】请求：{}, params:{}, 原因：{}", request.getRequestURI(), JSONObject.toJSONString(request.getParameterMap()), e);
        return RestResponse.error(e.getError(), e.getMessage());
    }

    /**
     * 拦截无效请求异常
     * @param request 请求
     * @param e 无响应请求异常
     * @return 响应
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public RestResponse<RestError> handleNotFoundException(HttpServletRequest request, NoHandlerFoundException e) {
        log.error("请求：{}, params:{}, 无效请求错误：{}", request.getRequestURI(), JSONObject.toJSONString(request.getParameterMap()), e);
        return RestResponse.error(RestError.ERROR9000);
    }


    /**
     * 拦截请求异常-方法绑定异常-屏蔽后台错误
     * @param request 请求
     * @param e 方法绑定异常
     * @return 响应
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BindException.class)
    public RestResponse<RestError> handleBindException(HttpServletRequest request, BindException e) {
        Map<String, String> errorMap = this.getBindingResult(e.getBindingResult());
        log.error("【方法绑定异常】请求：{}, params:{}, 原因：{}", request.getRequestURI(), errorMap, e);
        return RestResponse.error(RestError.ERROR9000);
    }

    /**
     * 拦截请求异常-请求参数校验异常-屏蔽后台错误
     * @param request 请求
     * @param e 参数校验异常
     * @return 响应
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestResponse<RestError> handleBindException(HttpServletRequest request, MethodArgumentNotValidException e) {
        Map<String, String> errorMap = this.getBindingResult(e.getBindingResult());
        log.error("【请求绑定参数异常】请求：{}, params:{}, 原因：{}", request.getRequestURI(), errorMap, e);
        return RestResponse.error(RestError.ERROR9001);
    }

    /**
     * 拦截请求异常-JPA校验异常-屏蔽后台错误
     * @param request 请求
     * @param e 参数校验异常
     * @return 响应
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ConstraintViolationException.class)
    public RestResponse<RestError> handleBindException(HttpServletRequest request, ConstraintViolationException e) {
        log.error("【JPA校验异常】请求：{}, params:{}, 原因：{}", request.getRequestURI(), JSONObject.toJSONString(request.getParameterMap()), e);
        return RestResponse.error(RestError.ERROR9001);
    }

    /**
     * 拦截无效请求异常-Method不支持
     * @param request 请求
     * @param e Method不支持异常
     * @return 响应
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public RestResponse<RestError> handleUnsupportedMethodException(HttpServletRequest request, HttpRequestMethodNotSupportedException e) {
        log.error("【Method不支持】请求：{}, params:{}, 原因：{}", request.getRequestURI(), JSONObject.toJSONString(request.getParameterMap()), e);
        return RestResponse.error(RestError.ERROR9001);
    }

    /**
     * 拦截服务器异常-运行时异常
     * @param request 请求
     * @param e 运行时异常
     * @return 响应
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public RestResponse<RestError> handleRuntimeException(HttpServletRequest request, RuntimeException e) {
        log.error("请求：{}, params:{}, 数据库响应错误：{}", request.getRequestURI(), JSONObject.toJSONString(request.getParameterMap()), e);
        return RestResponse.error(RestError.ERROR9000);
    }

    /**
     * 拦截服务器异常-异常
     * @param request 请求
     * @param e 异常
     * @return 响应
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public RestResponse<RestError> handleException(HttpServletRequest request, Exception e) {
        log.error("请求：{}, params:{}, 服务器响应错误：{}", request.getRequestURI(), JSONObject.toJSONString(request.getParameterMap()), e);
        return RestResponse.error(RestError.ERROR9000);
    }

    /**
     * 获取绑定的结果
     * @param bindingResult 异常返回的绑定结果
     * @return 返回绑定结果的map
     */
    private Map<String, String> getBindingResult(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .collect(Collectors
                        .toMap(FieldError::getField, fieldError -> fieldError.getRejectedValue() == null ? "" : fieldError.getRejectedValue().toString()));
    }
}
