package com.neusiri.husky.core.swagger.config;


import com.neusiri.husky.core.common.properties.SwaggerProperty;
import com.neusiri.husky.core.swagger.SwaggerDocketFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>swagger配置</p>
 * <p>创建日期：2019-08-13</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Component
public class SwaggerRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(@NonNull Environment environment) {
        this.environment = environment;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(@NonNull BeanDefinitionRegistry registry) throws BeansException {
        BindResult<SwaggerProperty> result = Binder.get(environment).bind("husky.swagger", SwaggerProperty.class);
        SwaggerProperty swagger = result.get();
        if (swagger != null) {
            if (!CollectionUtils.isEmpty(swagger.getProject())) {
                swagger.getProject().forEach((project) -> {
                    if (StringUtils.isEmpty(project.getTitle())) {
                        project.setTitle(swagger.getTitle());
                    }
                    if (StringUtils.isEmpty(project.getDescription())) {
                        project.setDescription(swagger.getDescription());
                    }
                    if (StringUtils.isEmpty(project.getTermsOfServiceUrl())) {
                        project.setTermsOfServiceUrl(swagger.getTermsOfServiceUrl());
                    }
                    if (StringUtils.isEmpty(project.getVersion())) {
                        project.setVersion(swagger.getVersion());
                    }
                });

                AtomicInteger index = new AtomicInteger(0);
                swagger.getProject().forEach((project) -> {
                    BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(SwaggerDocketFactoryBean.class);
                    beanDefinitionBuilder.addPropertyValue("swaggerItemProperty", project);
                    registry.registerBeanDefinition("swaggerDocket" + index, beanDefinitionBuilder.getBeanDefinition());
                    index.getAndIncrement();
                });
            }
        }
    }

    @Override
    public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
