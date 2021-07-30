package com.atom.common.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.function.Function;

/**
 * @author zr
 * @description 用于转发逻辑的过滤器
 * @date 2019-08-22
 */
@RequiredArgsConstructor
@Slf4j
public class RedirectFilter implements Filter {

    /**
     * 配置url通配符
     */
    private final String urlPattern;

    /**
     * 转发逻辑
     */
    private final Function<HttpServletRequest, String> rewriteHandler;

    /**
     * 路径匹配器
     */
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String servletPath = request.getServletPath();
        String context = request.getContextPath();
        // 匹配的路径重写
        if (pathMatcher.match(urlPattern, servletPath)) {
            String rewritePath = rewriteHandler.apply(request);
            log.debug("Rewrite {} to {}", servletPath, rewritePath);
            String fullPath;
            if (rewritePath.startsWith("/")) {
                fullPath = context + rewritePath;
            } else {
                fullPath = context + "/" + rewritePath;
            }
            req.getRequestDispatcher(fullPath).forward(req, resp);
        } else {
            chain.doFilter(req, resp);
        }
    }
}
