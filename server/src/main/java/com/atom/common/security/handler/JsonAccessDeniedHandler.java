package com.atom.common.security.handler;

import com.atom.common.pojo.http.RestError;
import com.atom.common.pojo.http.RestResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zr
 * @description json拒绝请求拦截
 * @date 2019/5/24
 */
@Component
public class JsonAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(final HttpServletRequest request, final HttpServletResponse response, final AccessDeniedException e) {
        RestResponse.error(RestError.ERROR9004, e).writeAsJson(response);
    }

}
