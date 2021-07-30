package com.atom.common.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zr
 * @description 用于实现前后端分离的集成部署
 * @date 2019-08-22
 */
@Configuration
public class RedirectFilterConfiguration {

    /**
     * 匹配前端请求前缀
     * 所有 /api/* 请求映射至 /*
     * exp /api/app/users/me -> /app/users/me
     */
    @Bean
    public FilterRegistrationBean<RedirectFilter> apiFilterRegisteration() {
        FilterRegistrationBean<RedirectFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new RedirectFilter("/api/**",
                http -> http.getServletPath().replaceFirst("/api", "")));
        registration.addUrlPatterns("/api/*");
        registration.setName("apiFilter");
        return registration;
    }

    /**
     * 匹配域名前端路由
     * 所有 / 请求映射至 /portal
     * exp / -> /portal
     */
    @Bean
    public FilterRegistrationBean<RedirectFilter> domainFilterRegisteration() {
        int order = Integer.MAX_VALUE - 2;
        FilterRegistrationBean<RedirectFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new RedirectFilter("/", http -> "/portal/index.html"));
        registration.addUrlPatterns("/");
        registration.setName("domainFilter");
        registration.setOrder(order);
        return registration;
    }

    /**
     * 匹配portal前端路由
     * 所有 /portal/* 请求映射至 index.html
     * exp /portal/appCenter -> /index.html
     */
    @Bean
    public FilterRegistrationBean<RedirectFilter> portalFilterRegisteration() {
        int order = Integer.MAX_VALUE - 1;
        FilterRegistrationBean<RedirectFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new RedirectFilter("/portal/**", http -> "/portal/index.html"));
        registration.addUrlPatterns("/portal/*");
        registration.setName("portalFilter");
        registration.setOrder(order);
        return registration;
    }

    /**
     * 匹配dashboard前端路由
     * 所有 /dashboard/* 请求映射至 index.html
     * exp /dashboard/appCenter -> /index.html
     */
    @Bean
    public FilterRegistrationBean<RedirectFilter> dashboardFilterRegisteration() {
        int order = Integer.MAX_VALUE;
        FilterRegistrationBean<RedirectFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new RedirectFilter("/dashboard/**", http -> "/dashboard/index.html"));
        registration.addUrlPatterns("/dashboard/*");
        registration.setName("dashboardFilter");
        registration.setOrder(order);
        return registration;
    }
}
