package com.atom.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zr
 * @description mvc配置
 * @date 2019-10-09
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    /**
     * 自定义静态资源映射目录
     * addResourceHandler：访问映射路径
     * addResourceLocations：资源绝对路径
     *
     * @param registry 资源注册
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 注册portal的资源映射 /html开头的是css.js文件部分
        registry.addResourceHandler("/portal/**", "/html/portal/**")
                .addResourceLocations("classpath:/portal/");
        // 注册dashboard的资源映射 /html开头的是css.js文件部分
        registry.addResourceHandler("/dashboard/**", "/html/dashboard/**")
                .addResourceLocations("classpath:/dashboard/");
    }

}
