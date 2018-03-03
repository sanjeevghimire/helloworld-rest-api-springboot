package com.comcast.helloworld.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket restEndpointAPI() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()                 .apis(RequestHandlerSelectors.basePackage("com.comcast.helloworld.controller"))
                .paths(regex("/api.*"))
                .build()
                .apiInfo(metaData());

    }

    private ApiInfo metaData() {
            return new ApiInfo(
                    "Spring Boot REST API",
                    "Spring Boot REST API",
                    "1.0",
                    "Terms of service",
                    new Contact("Sanjeev Ghimire", "http://www.sanjeevghimire.com", "gsanjeev7@gmail.com"),
                    "MIT License", "https://opensource.org/licenses/MIT", Collections.emptyList());
        }

    }
