package com.neusiri.husky.core.common.properties;

import lombok.Data;

import java.util.List;

/**
 * <p>系统自定义配置属性</p>
 * <p>创建日期：2019-08-13</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */

@Data
public class HuskyProperties {

    /**
     * 静态资源映射
     */
    private List<ResourceProperty> resources;

    /**
     * swagger配置
     */
    private SwaggerProperty swagger;

    /**
     * 导出配置
     */
    private ExportProperties export;

    /**
     * 邮件服务配置
     */
    private MailProperties mail;

    /**
     * 定时任务配置
     */
    private ScheduleProperties schedule;


}
