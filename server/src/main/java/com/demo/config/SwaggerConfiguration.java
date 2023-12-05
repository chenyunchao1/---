package com.demo.config;

import com.demo.constant.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@CrossOrigin
@EnableSwagger2
public class SwaggerConfiguration extends WebMvcConfigurationSupport {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/img/songPic/**")
                .addResourceLocations("file:" + Constants.RESOURCE_WIN_PATH + "\\img\\songPic\\");
        registry.addResourceHandler("/img/productPic/**")
                .addResourceLocations("file:" + Constants.RESOURCE_WIN_PATH + "\\img\\productPic\\");
        registry.addResourceHandler("/video/**")
                .addResourceLocations("file:" + Constants.RESOURCE_WIN_PATH + "\\video\\");
        registry.addResourceHandler("/document/**")
                .addResourceLocations("file:" + Constants.RESOURCE_WIN_PATH + "/document/");
    }

        private ApiInfo apiInfo(){
            return new ApiInfoBuilder ().title ("ApI接口文档")
                    .description ("bsTest")
                    .build ();
        }

        @Bean
        public Docket createRestApi(){
            return new Docket (DocumentationType.SWAGGER_2)
                    .apiInfo (apiInfo ())
                    .select ()
                    .apis (RequestHandlerSelectors.basePackage ("com.demo.controller"))
                    .paths (PathSelectors.any ())
                    .build ();
        }
}
