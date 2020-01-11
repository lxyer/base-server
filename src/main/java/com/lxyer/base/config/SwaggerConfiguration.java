
package com.lxyer.base.config;

import java.util.List;

import com.lxyer.base.modules.extend.DeveloperApiInfo;
import com.lxyer.base.modules.extend.DeveloperApiInfoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import com.github.xiaoymin.knife4j.spring.annotations.EnableSwaggerBootstrapUi;
import com.google.common.collect.Lists;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUi
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {


    private final TypeResolver typeResolver;

    @Autowired
    public SwaggerConfiguration(TypeResolver typeResolver) {
        this.typeResolver = typeResolver;
    }

    /*@Bean
    public UiConfiguration uiConfiguration(){
        return UiConfigurationBuilder.builder().supportedSubmitMethods(new String[]{})
                .displayOperationId(true)
                .build();
    }*/


    @Bean(value = "defaultApi")
    public Docket defaultApi() {
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        List<Parameter> parameters = Lists.newArrayList();
        parameterBuilder.name("token").description("token令牌").modelRef(new ModelRef("String"))
                .parameterType("header")
                .required(true).build();
        parameters.add(parameterBuilder.build());

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("调试接口")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.deepsec.notarization.modules.*.controller"))
                //.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(parameters)
                .securityContexts(Lists.newArrayList(securityContext())).securitySchemes(Lists.<SecurityScheme>newArrayList(apiKey()));
        return docket;
    }

    @Bean(value = "notarizationapplyRestApi")
    public Docket notarizationapplyRestApi() {
        List<ResolvedType> list = Lists.newArrayList();

        //SpringAddtionalModel springAddtionalModel= springAddtionalModelService.scan("com.swagger.bootstrap.ui.demo.extend");
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(groupApiInfo())
                .groupName("公证申请")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.deepsec.notarization.modules.notarizationapply"))
                .paths(PathSelectors.any())
                .build()
                .additionalModels(typeResolver.resolve(DeveloperApiInfo.class)).securityContexts(Lists.newArrayList(securityContext(), securityContext1())).securitySchemes(Lists.<SecurityScheme>newArrayList(apiKey(), apiKey1()));
    }

    @Bean(value = "attestationmanageRestApi")
    public Docket attestationmanageRestApi() {
        List<ResolvedType> list = Lists.newArrayList();

        //SpringAddtionalModel springAddtionalModel= springAddtionalModelService.scan("com.swagger.bootstrap.ui.demo.extend");
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(groupApiInfo())
                .groupName("公证申请-认证管理")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.deepsec.notarization.modules.attestationmanage"))
                .paths(PathSelectors.any())
                .build()
                .additionalModels(typeResolver.resolve(DeveloperApiInfo.class)).securityContexts(Lists.newArrayList(securityContext(), securityContext1())).securitySchemes(Lists.<SecurityScheme>newArrayList(apiKey(), apiKey1()));
    }
    @Bean(value = "fileRestApi")
    public Docket fileRestApi() {
        List<ResolvedType> list = Lists.newArrayList();

        //SpringAddtionalModel springAddtionalModel= springAddtionalModelService.scan("com.swagger.bootstrap.ui.demo.extend");
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(groupApiInfo())
                .groupName("材料管理")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.deepsec.notarization.modules.file"))
                .paths(PathSelectors.any())
                .build()
                .additionalModels(typeResolver.resolve(DeveloperApiInfo.class)).securityContexts(Lists.newArrayList(securityContext(), securityContext1())).securitySchemes(Lists.<SecurityScheme>newArrayList(apiKey(), apiKey1()));
    }
    @Bean(value = "emailApi")
    public Docket emailApi() {
        List<ResolvedType> list = Lists.newArrayList();

        //SpringAddtionalModel springAddtionalModel= springAddtionalModelService.scan("com.swagger.bootstrap.ui.demo.extend");
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(groupApiInfo())
                .groupName("邮件管理")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.deepsec.notarization.modules.email.controller"))
                .paths(PathSelectors.any())
                .build()
                .additionalModels(typeResolver.resolve(DeveloperApiInfo.class)).securityContexts(Lists.newArrayList(securityContext(), securityContext1())).securitySchemes(Lists.<SecurityScheme>newArrayList(apiKey(), apiKey1()));
    }

    @Bean(value = "merchantmanageRestApi")
    public Docket merchantmanageRestApi() {
        List<ResolvedType> list = Lists.newArrayList();

        //SpringAddtionalModel springAddtionalModel= springAddtionalModelService.scan("com.swagger.bootstrap.ui.demo.extend");
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(groupApiInfo())
                .groupName("商户管理")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.deepsec.notarization.modules.merchantmanage"))
                .paths(PathSelectors.any())
                .build()
                .additionalModels(typeResolver.resolve(DeveloperApiInfo.class)).securityContexts(Lists.newArrayList(securityContext(), securityContext1())).securitySchemes(Lists.<SecurityScheme>newArrayList(apiKey(), apiKey1()));
    }
    @Bean(value = "userRestApi")
    public Docket userRestApi() {
        List<ResolvedType> list = Lists.newArrayList();

        //SpringAddtionalModel springAddtionalModel= springAddtionalModelService.scan("com.swagger.bootstrap.ui.demo.extend");
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(groupApiInfo())
                .groupName("用户管理")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.deepsec.notarization.modules.myauthentication"))
                .paths(PathSelectors.any())
                .build()
                .additionalModels(typeResolver.resolve(DeveloperApiInfo.class)).securityContexts(Lists.newArrayList(securityContext(), securityContext1())).securitySchemes(Lists.<SecurityScheme>newArrayList(apiKey(), apiKey1()));
    }

    @Bean(value = "businessLogRestApi")
    public Docket businessLogRestApi() {
    	List<ResolvedType> list = Lists.newArrayList();

    	//SpringAddtionalModel springAddtionalModel= springAddtionalModelService.scan("com.swagger.bootstrap.ui.demo.extend");
    	return new Docket(DocumentationType.SWAGGER_2)
    			.apiInfo(groupApiInfo())
    			.groupName("日志管理")
    			.select()
    			.apis(RequestHandlerSelectors.basePackage("com.deepsec.notarization.modules.businesslog"))
    			.paths(PathSelectors.any())
    			.build()
    			.additionalModels(typeResolver.resolve(DeveloperApiInfo.class)).securityContexts(Lists.newArrayList(securityContext(), securityContext1())).securitySchemes(Lists.<SecurityScheme>newArrayList(apiKey(), apiKey1()));
    }

    @Bean(value = "executionControllerRestApi")
    public Docket executionControllerRestApi() {
    	return new Docket(DocumentationType.SWAGGER_2)
    			.apiInfo(groupApiInfo())
    			.groupName("执行期接口")
    			.select()
    			.apis(RequestHandlerSelectors.basePackage("com.deepsec.notarization.modules.execution"))
    			.paths(PathSelectors.any())
    			.build()
    			.additionalModels(typeResolver.resolve(DeveloperApiInfo.class)).securityContexts(Lists.newArrayList(securityContext(), securityContext1())).securitySchemes(Lists.<SecurityScheme>newArrayList(apiKey(), apiKey1()));
    }

    private ApiInfo groupApiInfo() {
        DeveloperApiInfoExtension apiInfoExtension = new DeveloperApiInfoExtension();

        apiInfoExtension.addDeveloper(new DeveloperApiInfo("张三", "zhangsan@163.com", "Java"))
                .addDeveloper(new DeveloperApiInfo("李四", "lisi@163.com", "Java"));


        return new ApiInfoBuilder()
                .title("银行服务在线接口文档")
                .description("<div style='font-size:14px;color:red;'>swagger-bootstrap-ui-demo RESTful APIs</div>")
                .termsOfServiceUrl("http://www.group.com/")
                .contact("489322130@qq.com")
                .version("1.0")
                .extensions(Lists.newArrayList(apiInfoExtension))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("s银行服务 RESTful APIs")
                .description("# 银行服务 RESTful APIs")
                .termsOfServiceUrl("http://www.xx.com/")
                .contact("489322130@qq.com")
                .version("1.0")
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("BearerToken", "Authorization", "header");
    }

    private ApiKey apiKey1() {
        return new ApiKey("BearerToken1", "Authorization-x", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/.*"))
                .build();
    }

    private SecurityContext securityContext1() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth1())
                .forPaths(PathSelectors.regex("/.*"))
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference("BearerToken", authorizationScopes));
    }

    List<SecurityReference> defaultAuth1() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference("BearerToken1", authorizationScopes));
    }

}
