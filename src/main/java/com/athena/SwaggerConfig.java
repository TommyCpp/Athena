package com.athena;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tommy on 2017/9/20.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket productApi() {
        List<SecurityScheme> securitySchemes = new ArrayList<>();
        securitySchemes.add(new ApiKey("admin/superadmin", "", ""));

        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .securitySchemes(securitySchemes)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.athena.controller"))
                .build();
    }
}
