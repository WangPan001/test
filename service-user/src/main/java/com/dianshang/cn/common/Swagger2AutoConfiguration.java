package com.dianshang.cn.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
@EnableConfigurationProperties({SwaggerProperties.class})
public class Swagger2AutoConfiguration {

    @Autowired
    private SwaggerProperties swaggerProperties;

    @Bean
    public Docket createRestApi() {
        String basePackage = swaggerProperties.getBasePackage();
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        parameterBuilder.name("uid").description("uid").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        List<Parameter> aParameters = new ArrayList<>();
//        aParameters.add(parameterBuilder.build());//配置默认的入参
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build().useDefaultResponseMessages(false).globalOperationParameters(aParameters);
    }

    private ApiInfo getApiInfo() {
        String title = swaggerProperties.getTitle();
        String description = swaggerProperties.getDescription();
        String termsOfServiceUtl = swaggerProperties.getTermsOfServiceUrl();
        String version = swaggerProperties.getVersion();
        String license= swaggerProperties.getLicense();
        String licenseUrl = swaggerProperties.getLicenseUrl();
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .termsOfServiceUrl(termsOfServiceUtl)
                .version(version)
                .license(license)
                .licenseUrl(licenseUrl)
                .build();
    }
}
