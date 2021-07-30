package com.atom.common.config;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author zr
 * @description Swagger2 api配置
 * @date 2019-10-14
 */
@Configuration
@EnableSwagger2
@Profile({"dev", "test"})
public class Swagger2Configuration {

    /**
     * swagger2是否启用
     */
    @Value("${swagger2.enable:true}")
    private boolean enable;

    /**
     * 系统api模块
     */
    @Bean("systemApis")
    public Docket systemApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("系统模块")
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.regex("/system.*"))
                .build()
                .apiInfo(apiInfo())
                .enable(enable);
    }

    /**
     * 业务api的模块
     */
    @Bean("businessApis")
    public Docket customApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("业务模块")
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.regex("/business.*"))
                .build()
                .apiInfo(apiInfo())
                .enable(enable);
    }

    /**
     * swagger2 api调用
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("XXXXX系统平台接口文档")
                .description("提供子模块1/子模块2/子模块3的文档")
                .termsOfServiceUrl("https://www.zjcds.com")
                .version("1.0")
                .build();
    }
}
