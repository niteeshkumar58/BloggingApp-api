package com.codewithniteesh.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getInfo()).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();


    }

    private ApiInfo getInfo() {
        return new ApiInfo(
                "Blogging App Backend",
                "app developed for leaning purpose",
                "1.0",
                "Term of services",
                new Contact("Niteesh", "Website not available", "niteeshkumar58@gmail.com"),
                "Licence of App",
                "Url of Licence",
                Collections.emptyList()
        );
    }
}
