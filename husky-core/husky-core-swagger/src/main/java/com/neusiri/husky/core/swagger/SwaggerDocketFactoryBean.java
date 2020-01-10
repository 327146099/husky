package com.neusiri.husky.core.swagger;

import com.neusiri.husky.core.common.properties.SwaggerItemProperty;
import lombok.Data;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.List;

/**
 * <p>SwaggerDocket工厂类</p>
 * <p>创建日期：2019-09-11</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Data
public class SwaggerDocketFactoryBean implements FactoryBean<Docket> {

    private SwaggerItemProperty swaggerItemProperty;

    @Override
    public Docket getObject() {
        return createRestApi(swaggerItemProperty);
    }

    @Override
    public Class<?> getObjectType() {
        return Docket.class;
    }

    private Docket createRestApi(SwaggerItemProperty swaggerProperty) {
        List<ResponseMessage> responseMessageList = Arrays.asList(
                new ResponseMessageBuilder().code(0).message("非HTTP状态码，返回值JSON code字段值，描述：成功").responseModel(new ModelRef("AppResponse")).build(),
                new ResponseMessageBuilder().code(-1).message("非HTTP状态码，返回值JSON code字段值，描述：系统未知错误").responseModel(new ModelRef("AppResponse")).build(),
                new ResponseMessageBuilder().code(-2).message("非HTTP状态码，返回值JSON code字段值，描述：参数校验失败").responseModel(new ModelRef("AppResponse")).build(),
                new ResponseMessageBuilder().code(-3).message("非HTTP状态码，返回值JSON code字段值，描述：系统服务异常").responseModel(new ModelRef("AppResponse")).build(),
                new ResponseMessageBuilder().code(-4).message("非HTTP状态码，返回值JSON code字段值，描述：系统业务异常").responseModel(new ModelRef("AppResponse")).build()
        );

        return new Docket(DocumentationType.SWAGGER_2)

                .globalResponseMessage(RequestMethod.GET, responseMessageList)
                .globalResponseMessage(RequestMethod.POST, responseMessageList)
                .globalResponseMessage(RequestMethod.PUT, responseMessageList)
                .globalResponseMessage(RequestMethod.DELETE, responseMessageList)

                .apiInfo(apiInfo(swaggerProperty))
                .groupName(swaggerProperty.getTitle())
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerProperty.getBasePackage()))
                .paths(PathSelectors.any())
                .build();

    }

    private ApiInfo apiInfo(SwaggerItemProperty swaggerProperty) {
        Contact contact = new Contact("husky", null, null);
        return new ApiInfoBuilder()
                .title(swaggerProperty.getTitle())
                .contact(contact)
                .description(swaggerProperty.getDescription())
                .termsOfServiceUrl(swaggerProperty.getTermsOfServiceUrl())
                .version(swaggerProperty.getVersion())
                .build();
    }
}
