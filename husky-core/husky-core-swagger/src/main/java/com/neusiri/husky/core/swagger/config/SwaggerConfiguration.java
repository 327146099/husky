package com.neusiri.husky.core.swagger.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableSwaggerBootstrapUi;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <p>swagger配置</p>
 * <p>创建日期：2019-10-21</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@EnableSwagger2
@EnableSwaggerBootstrapUi
@Import(BeanValidatorPluginsConfiguration.class)
@Configuration
public class SwaggerConfiguration {

}
