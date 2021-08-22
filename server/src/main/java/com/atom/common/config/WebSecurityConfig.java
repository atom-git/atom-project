package com.atom.common.config;

import com.atom.common.security.cache.IUserCacheStore;
import com.atom.common.security.filter.JsonAuthenticationFilter;
import com.atom.common.security.filter.TokenAuthenticationProcessingFilter;
import com.atom.common.security.handler.JsonAccessDeniedHandler;
import com.atom.common.security.handler.JsonAuthFailureHandler;
import com.atom.common.security.handler.JsonAuthSuccessHandler;
import com.atom.common.security.handler.JsonAuthenticationEntryPoint;
import com.atom.common.security.provider.UserLoginAuthProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @author zr
 * @description web 安全配置
 * @date 2019/5/24
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 访问拒绝handler
     */
    @Resource
    private JsonAccessDeniedHandler accessDeniedHandler;

    /**
     * 认证切点
     */
    @Resource
    private JsonAuthenticationEntryPoint authenticationEntryPoint;

    /**
     * 认证成功的handler
     */
    @Resource
    private JsonAuthSuccessHandler successHandler;

    /**
     * 认证失败的handler
     */
    @Resource
    private JsonAuthFailureHandler failureHandler;

    /**
     * 用户认证登录器
     */
    @Resource
    private UserLoginAuthProvider userLoginAuthProvider;

    /**
     * 用户缓存
     */
    @Resource
    private IUserCacheStore userCacheStore;

    /**
     * 白名单页面
     */
    private static final String[] AUTH_WHITELIST = {
            "/",
            // websocket stomp消息
            "/stomp/**",
            // 系统登录前相关
            "/api/system/sign/up",
            "/api/system/captcha",
            "/api/system/judge/captcha",
            "/api/system/forget/password",
            "/api/system/send/*/verifyCode",
            "/api/system/third/state",
            "/api/system/third/sign/in",
            "/system/sign/up",
            "/system/captcha",
            "/system/judge/captcha",
            "/system/forget/password",
            "/system/send/*/verifyCode",
            "/system/third/state",
            "/system/third/sign/in"
    };

    /**
     * HttpSecurity拦截配置
     * @param http HttpSecurity自身
     * @throws Exception AuthManager认证器创建异常
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                // 白名单放行
                .antMatchers(AUTH_WHITELIST)
                .permitAll()
                // 请求必须认证登录
                .anyRequest()
                // 这里匹配除白名单外所有的前端请求，判断/api/**开头的请求是否有权限
                .access("@permissionService.hasPermission()")
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .formLogin()
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .and()
                .httpBasic()
                .and()
                .logout()
                .and()
                .addFilterBefore(new TokenAuthenticationProcessingFilter(userCacheStore), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(jsonAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 静态资源列表
     */
    private static final String[] STATIC_LIST = {
            // portal dashboard page file、temp 临时文件路径，/html静态资源目录
            "/portal/**",
            "/dashboard/**",
            "/html/portal/**",
            "/html/dashboard/**",
            "/temp/**",
            // -- swagger ui
            "/swagger-ui.html",
            "/webjars/**",
            "/v2/**",
            "/swagger-resources/**"
    };

    /**
     * 忽略静态资源
     * @param web WebSecurity
     */
    @Override
    public void configure(WebSecurity web) {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers(STATIC_LIST);
    }

    /**
     * 配置认证器
     * @param auth 认证器管理
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(userLoginAuthProvider);
    }

    /**
     * 密码加密器
     * @return 返回BCryptPasswordEncoder编码器
     */
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 注册自定义的UsernamePasswordAuthenticationFilter
     * @return JSON登录参数接收
     */
    @Bean
    public JsonAuthenticationFilter jsonAuthenticationFilter() throws Exception {
        JsonAuthenticationFilter filter = new JsonAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationFailureHandler(failureHandler);
        filter.setFilterProcessesUrl("/login");
        // 这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }
}
