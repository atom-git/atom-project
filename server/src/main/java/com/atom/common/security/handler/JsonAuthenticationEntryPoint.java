package com.atom.common.security.handler;

import com.atom.common.pojo.http.RestError;
import com.atom.common.pojo.http.RestResponse;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zr
 * @description 认证服务
 * @date 2019/5/24
 */
@Component
public class JsonAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        authException.printStackTrace();
        if (authException instanceof InsufficientAuthenticationException) {
            RestResponse.error(RestError.ERROR9004).writeAsJson(response);
        } else {
            RestResponse.error(RestError.ERROR9004, authException).writeAsJson(response);
        }
    }
}
