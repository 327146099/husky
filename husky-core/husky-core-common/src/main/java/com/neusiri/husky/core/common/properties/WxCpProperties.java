package com.neusiri.husky.core.common.properties;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * WxCpProperties 微信配置
 *
 * @author raintea
 * @date 2019/8/28
 * <p>
 * 版本        修改时间        作者        修改内容
 * V1.0        2019/8/28      raintea    初始版本
 */
@Data
public class WxCpProperties {

    /**
     * 代理
     */
    private Agent agent;

    /**
     * 企业号
     */
    private Cp cp;

    @Data
    public static class AppConfig {

        /**
         * 应用是否启用
         */
        private String enable;

        /**
         * 设置微信企业应用的AgentId
         */
        private Integer agentId;

        /**
         * 设置微信企业应用的Secret
         */
        private String secret;

    }

    @Data
    public static class Agent {
        /**
         * 是否启用
         */
        private String enable;
        /**
         * 代理host
         */
        private String host;

        /**
         * 代理端口
         */
        private String port;

        /**
         * 代理用户名
         */
        private String username;

        /**
         * 代理密码
         */
        private String password;

    }

    @Data
    public static class Cp {

        /**
         * 设置微信企业号的corpId
         */
        private String corpId;

        /**
         * app配置
         */
//        private List<AppConfig> appConfigs;
        private Map<String, AppConfig> appConfigs = new LinkedHashMap<>();

    }

}