package com.atom.common.security.handler;

import com.atom.common.pojo.http.RestResponse;
import com.atom.common.security.SessionUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zr
 * @description
 * @date 2019/5/24
 */
@Component
public class JsonAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        SessionUser sessionUser = (SessionUser) authentication;
        // 返回用户token
        RestResponse.success(sessionUser.getToken()).writeAsJson(response);
    }
}

