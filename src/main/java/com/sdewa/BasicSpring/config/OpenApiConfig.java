package com.sdewa.BasicSpring.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("My App REST API")
                        .version("1.0.0")
                        .description("This is the API documentation for my Spring Boot app.")
                        .license(new License().name("Apache 2.0").url("https://springdoc.org")));
    }
}
