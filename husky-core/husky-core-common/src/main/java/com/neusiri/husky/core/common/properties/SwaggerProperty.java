package com.neusiri.husky.core.common.properties;

import lombok.Data;

import java.util.List;

/**
 * <p>swagger配置属性</p>
 * <p>创建日期：2019-08-13</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Data
public class SwaggerProperty {

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

    /**
     * 项目配置
     */
    private List<SwaggerItemProperty> project;

}
