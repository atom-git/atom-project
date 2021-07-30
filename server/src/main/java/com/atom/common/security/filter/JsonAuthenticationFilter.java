package com.atom.common.security.filter;

import cn.hutool.core.lang.Validator;
import com.alibaba.fastjson.JSONObject;
import com.atom.common.pojo.mapper.PlatformType;
import com.atom.common.security.provider.AccountAuthentication;
import com.atom.server.system.pojo.dto.SignInDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author zr
 * @description 以JSON的形式接收登录信息
 * @date 2021/4/22
 */
public class JsonAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String contentType = request.getHeader(HttpHeaders.CONTENT_TYPE);
        if (Validator.isNotEmpty(contentType) && contentType.startsWith(MediaType.APPLICATION_JSON_VALUE)) {

            AccountAuthentication authRequest;
            try (InputStream is = request.getInputStream()) {
                SignInDTO signInDTO = JSONObject.parseObject(is, SignInDTO.class);
                Object credentials;
                // credentials根据类型来判断
                if ("account".equals(signInDTO.getType())) {
                    credentials = signInDTO.getPassword();
                } else {
                    credentials = signInDTO.getVerifyCode();
                }
                authRequest = new AccountAuthentication(signInDTO.getAccount(), credentials, signInDTO);
                // 获取系统平台信息
                authRequest.setPlatform(request.getHeader("Platform"));
                authRequest.setPlatformType(PlatformType.matchName(request.getHeader("Platform-Type")));
            } catch (IOException e) {
                e.printStackTrace();
                throw new UsernameNotFoundException("登录行为不合法");
            }
            setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }

        throw new UsernameNotFoundException("登录行为不合法");
    }
}
