package com.ld.elasticsearch.config;

import io.swagger.models.Swagger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.ArrayList;
import java.util.List;

@ConditionalOnClass(value = {Swagger.class})
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    //通过占位符获取当前文档的名称，动态修改，避免硬编码
    @Value("${spring.application.name}")
    private String applicationName;
    /**
     * 创建API应用
     * apiInfo() 增加api相关信息
     * 通过select()函数返回一个ApiSelectorBuilder实例，用来控制哪些接口暴露给Swagger来显示
     * @return
     */
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //一般微服务的包名前缀都是一样的
                .apis(RequestHandlerSelectors.basePackage("com.ld"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(parameters());
    }
    /**
     * 创建该API的基本信息，在文档页面展示
     * 访问地址：http://ip:端口/swagger-ui.html
     * @return
     */
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title(applicationName + "接口文档")
                .description(applicationName + "服务(对外进行接口暴露)")
                .termsOfServiceUrl("")
                .contact(new Contact("dudj","","13020078873@163.com"))
                .version("1.0.0")
                .build();
    }
    /**
     * 统一添加header参数
     * @return
     */
    private List<Parameter> parameters(){
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        List<Parameter> parameters = new ArrayList();
        parameterBuilder.name("Authorization")
                .description("Authorization")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false);
        parameters.add(parameterBuilder.build());
        return parameters;
    }
}