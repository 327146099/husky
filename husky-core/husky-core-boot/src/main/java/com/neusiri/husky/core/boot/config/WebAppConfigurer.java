package com.neusiri.husky.core.boot.config;

import com.neusiri.husky.core.boot.converter.DateConverter;
import com.neusiri.husky.core.common.properties.HuskyProperties;
import com.neusiri.husky.core.common.properties.ResourceProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.format.FormatterRegistry;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import java.util.List;
import java.util.Map;

/**
 * <p>web配置类</p>
 * <p>创建日期：2019-08-13</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

    /**
     * 注入拦截器
     */
    @Autowired(required = false)
    private Map<String, HandlerInterceptor> handlerInterceptors;

    /**
     * husky配置类
     */
    @Autowired
    private HuskyProperties huskyProperties;

    /**
     * 支持websocket配置
     *
     * @return ServerEndpoint扫描器
     */
    @Bean
    @Profile({"dev","prod"})
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 添加静态资源
        List<ResourceProperty> resources = huskyProperties.getResources();
        if (!CollectionUtils.isEmpty(resources)) {
            for (ResourceProperty resource : resources) {
                registry.addResourceHandler(resource.getPath())
                        .addResourceLocations(resource.getLocation().split(","));
            }
        }
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 跨域配置
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins("*")
                .allowedHeaders("*");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // 添加日期转换器
        registry.addConverter(new DateConverter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加拦截器
        if (!CollectionUtils.isEmpty(handlerInterceptors)) {
            handlerInterceptors.forEach((key, value) -> {
                registry.addInterceptor(value).addPathPatterns("/**");
            });
        }

    }

}
