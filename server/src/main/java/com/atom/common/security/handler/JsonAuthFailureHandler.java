package com.atom.common.security.handler;

import com.atom.common.pojo.http.RestError;
import com.atom.common.pojo.http.RestResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zr
 * @description 认证错误
 * @date 2019/5/24
 */
@Component
public class JsonAuthFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        e.printStackTrace();
        RestResponse.error(RestError.ERROR9004, e).writeAsJson(response);
    }
}
