package com.zyg.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author .g
 * @date 2021/8/18
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
@Import({BeanValidatorPluginsConfiguration.class})
public class SwaggerConfiguration {
    @Value("${spring.application.name}")
    private String appName;
    @Value("${swagger.enable:true}")
    private boolean enableSwagger;

    public SwaggerConfiguration() {
    }

    @Bean({"defaultApi2"})
    public Docket defaultApi2() {
        Docket docket = (new Docket(DocumentationType.SWAGGER_2)).apiInfo(this.apiInfo()).enable(this.enableSwagger).groupName(this.appName).select().apis(RequestHandlerSelectors.basePackage("com.zyg")).paths(PathSelectors.any()).build();
        return docket;
    }


    private ApiInfo apiInfo() {
        return (new ApiInfoBuilder()).title("服务接口API").version("1.0").build();
    }
}
