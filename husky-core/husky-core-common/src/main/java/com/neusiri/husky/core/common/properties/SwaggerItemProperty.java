package com.neusiri.husky.core.common.properties;

import lombok.Data;

@Data
public class SwaggerItemProperty {

    /**
     * 拦截请求根路径
     */
    private String basePackage;

    /**
     * swagger 标题
     */
    private String title;

    /**
     * swagger 描述
     */
    private String description;

    /**
     * 服务条款url
     */
    private String termsOfServiceUrl;

    /**
     * 版本号
     */
    private String version;

}
