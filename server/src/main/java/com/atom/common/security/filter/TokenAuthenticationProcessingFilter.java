package com.atom.common.security.filter;

import com.atom.common.pojo.http.RestError;
import com.atom.common.pojo.http.RestResponse;
import com.atom.common.pojo.mapper.PlatformType;
import com.atom.common.security.SessionUser;
import com.atom.common.security.cache.IUserCacheStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zr
 * @description 使用 token 进行身份验证的过滤器。
 * 如果 request headers 中有 Access-Token，使用 Access-Token 的值查询对应的登陆用户，如果用户有效则放行访问，否则返回 401 错误。
 * @date 2019/1/24
 * @see org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
 */
@Slf4j
public class TokenAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
    /**
     * 是否允许当前请求创建 session
     */
    private static final ThreadLocal<Boolean> allowSessionCreation = new ThreadLocal<>();

    /**
     * 令牌名称
     */
    private final String ACCESS_TOKEN = "Access-Token";

    /**
     * 用户缓存器
     */
    private final IUserCacheStore userCacheStore;


    /**
     * 参考 UsernamePasswordAuthenticationFilter
     * @param userCacheStore 用户缓存器
     */
    public TokenAuthenticationProcessingFilter(IUserCacheStore userCacheStore) {
        super(new AntPathRequestMatcher("/login", "POST"));
        this.userCacheStore = userCacheStore;
    }

    /**
     * 尝试校验用户身份
     * @param request 请求
     * @param response 响应
     * @return 返回用户身份
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(ACCESS_TOKEN);
        PlatformType platformType = PlatformType.matchName(request.getHeader("Platform-Type"));
        // 查询缓存
        SessionUser sessionUser = userCacheStore.getTokenUser(platformType, token);
        if (sessionUser == null) {
            return null;
        }
        sessionUser.setAuthenticated(true);
        return sessionUser;
    }

    /**
     * 身份校验过滤器
     * @param req 请求
     * @param res 响应
     * @param chain 过滤器链
     * @throws IOException IO异常
     * @throws ServletException servlet异常
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        Authentication auth;

        // 默认创建 session
        allowSessionCreation.set(true);
        // 如果 params 里有 token 时，则使用 token 查询用户数据进行登陆验证
        if (request.getHeader(ACCESS_TOKEN) != null) {
            // 1. 尝试进行身份认证
            // 2. 如果用户无效，则返回 401
            // 3. 如果用户有效，则保存到 SecurityContext 中，供本次方式后续使用
            auth = attemptAuthentication(request, response);

            if (auth == null) {
                RestResponse.error(RestError.ERROR9004).writeAsJson(response);
                return;
            }
            // 保存认证信息到 SecurityContext，禁止 HttpSessionSecurityContextRepository 创建 session
            allowSessionCreation.set(false);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        // 调用下一个Filter
        chain.doFilter(req, res);
    }

    /**
     * 获取session是否允许创建的标识位
     * 在token模式下session是不需要创建的
     * @return session是否允许创建的标识位
     */
    public static boolean isAllowSessionCreation() {
        return allowSessionCreation.get();
    }
}
