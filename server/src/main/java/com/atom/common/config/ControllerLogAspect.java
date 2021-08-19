package com.atom.common.config;

import com.alibaba.fastjson.JSONObject;
import com.atom.common.pojo.annotation.Permission;
import com.atom.common.pojo.http.RestRequest;
import com.atom.common.pojo.http.RestResponse;
import com.atom.common.pojo.mapper.ActionType;
import com.atom.common.pojo.mapper.LogType;
import com.atom.common.pojo.mapper.PlatformType;
import com.atom.common.security.SessionUser;
import com.atom.server.system.service.ISysLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.*;

/**
 * @author zr
 * @description controller 返回对象的log切面
 * @date 2020/3/10
 */
@Aspect
@Configuration
@Slf4j
public class ControllerLogAspect {
    /**
     * 日志服务
     */
    @Resource
    private ISysLogService sysLogService;

    /**
     * 定义切点：第一个*表示返回类型
     * 切点不能切入dashboard的controller
     */
    @Pointcut("execution(* com.atom.server..controller.*.*(..)) )")
    public void excudePointcut() { }

    /**
     * 在控制器层出入口定义日志记录
     * @param pdj 处理切点
     * @return 返回结果
     * @throws Throwable 处理异常
     */
    @Around("excudePointcut()")
    public Object aroundController(ProceedingJoinPoint pdj) throws Throwable {
        RestResponse<?> result;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;
        if (request == null) {
            // 非HTTP请求日志不录入
            return pdj.proceed();
        }
        // 请求地址
        String requestUrl = request.getRequestURI();
        // 请求参数
        Map<String, Object> requestParams = this.getParams(pdj);
        PlatformType platformType = PlatformType.matchName(request.getHeader("Platform-Type"));
        // 响应Controller类
        String className = pdj.getSignature().getDeclaringType().getSimpleName();
        // 响应方法名
        String methodName = pdj.getSignature().getName();
        log.info("http请求：{}，{}", requestUrl, String.format("响应controller:%s，方法:%s，参数:%s", className, methodName, JSONObject.toJSONString(requestParams)));
        // 请求信息ActionType
        ActionType[] actionTypes = this.getActionType(pdj);
        // 请求开始时间
        long startTime = System.currentTimeMillis();
        // 构建请求信息
        RestRequest restRequest = new RestRequest(requestUrl, requestParams, platformType, actionTypes);
        // 获取用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SessionUser sessionUser = null;
        if (authentication instanceof SessionUser) {
            sessionUser = (SessionUser) authentication;
        }
        try {
            // 执行请求
            result = (RestResponse<?>) pdj.proceed();
            /*
              日志查询请求url
              写入请求结果，如果是日志查询，则不记录，结果，防止结果集太大，导致请求失败
             */
            String LOG_REQUEST_URL = "/system/log/list";
            if (!requestUrl.equals(LOG_REQUEST_URL)) {
                restRequest.setResult(result);
                log.info("http请求：{},【controller:{}, method: {}】, 响应结果：{}", requestUrl, className, methodName, result);
            } else {
                restRequest.setResult(RestResponse.success());
                log.info("http请求：{},【controller:{}, method: {}】, 响应结果：{}", requestUrl, className, methodName, RestResponse.success());
            }
            return result;
        } catch (Exception e) {
            // 写入异常信息
            restRequest.setException(e.getMessage());
            restRequest.setResult(RestResponse.error(e));
            // 异常放到全局异常处理中进行打印
            throw e;
        } finally {
            // 执行时长
            restRequest.setExecutionTime(System.currentTimeMillis() - startTime);
            // 日志类型 1：认证登录日志 2：服务调用日志 3：数据同步日志
            sysLogService.save(sessionUser, LogType.SERVE, restRequest);
        }
    }

    /**
     * 获取参数信息
     * @param point 切入点
     * @return 参数信息
     */
    private Map<String, Object> getParams(JoinPoint point) {
        try {
            Map<String, Object> params = new HashMap<>();
            String[] parameterNames = ((MethodSignature) point.getSignature()).getParameterNames();
            if (Objects.nonNull(parameterNames)) {
                for (int i = 0; i < parameterNames.length; i++) {
                    String parameterName = parameterNames[i];
                    Object value = point.getArgs()[i];
                    if (value == null) {
                        continue;
                    }
                    if (value instanceof ServletRequest || value instanceof Principal || value instanceof ServletResponse) {
                        // 排除特殊对象
                        continue;
                    }
                    if (value instanceof Collection) {
                        params.put(parameterName, value);
                        continue;
                    }
                    Class<?> clazz = value.getClass();
                    if (clazz.isPrimitive()) {
                        // 判断是否为原始类型
                        params.put(parameterName, value);
                    } else if (clazz.getName().contains("java.lang")) {
                        // 判断是否为基本类型的包装类型
                        params.put(parameterName, value);
                    } else {
                        // 复杂类型读取其属性
                        Field[] declaredFields = clazz.getDeclaredFields();
                        for (Field field : declaredFields) {
                            String methodName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                            try {
                                Method method = clazz.getMethod(methodName);
                                Object getterValue = method.invoke(value);
                                params.put(field.getName(), getterValue);
                            } catch (Exception ignored) {
                            }
                        }
                    }
                }
            }
            return params;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("构造日志参数异常", e);
            return getExceptionMsg(e);
        }
    }

    /**
     * 获取方法操作类型
     * @param point 切入点
     * @return ActionType[]
     */
    private ActionType[] getActionType(JoinPoint point) {
        Method method = this.getMethod(point);
        Permission permission = method.getAnnotation(Permission.class);
        if (permission != null) {
            return permission.actionType();
        } else {
            return null;
        }
    }

    /**
     * 获取方法名
     * @param point 切入点
     * @return Method
     */
    private Method getMethod(JoinPoint point) {
        MethodSignature methodSignature = ((MethodSignature) point.getSignature());
        return methodSignature.getMethod();
    }

    /**
     * 获取异常的详细信息
     * @param e 异常
     * @return 详细信息
     */
    private Map<String, Object> getExceptionMsg(Throwable e) {
        Map<String, Object> exceptionMsg = new LinkedHashMap<>();
        return getExceptionMsg(e, exceptionMsg);
    }

    /**
     * 获取异常的详细信息
     *
     * @param e            异常
     * @param exceptionMsg 存储异常详细信息
     * @return 详细信息
     */
    private Map<String, Object> getExceptionMsg(Throwable e, Map<String, Object> exceptionMsg) {
        exceptionMsg.put(e.getClass().getSimpleName(), e.getMessage() == null ? "" : e.getMessage());
        if (e.getCause() == null || e.getClass().equals(e.getCause().getClass())) {
            // 如果子异常为空 或者 子异常和父异常类型一致则返回
            return exceptionMsg;
        }
        return getExceptionMsg(e.getCause(), exceptionMsg);
    }
}
