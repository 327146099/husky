package com.neusiri.husky.core.config;

import com.google.common.collect.Maps;
import com.neusiri.husky.core.common.properties.WxCpProperties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;

/**
 * WxCpConfiguration 微信配置
 *
 * @author raintea
 * @date 2019/8/28
 * <p>
 * 版本        修改时间        作者        修改内容
 * V1.0        2019/8/28      raintea    初始版本
 */
@Configuration
@Slf4j
@ConditionalOnProperty(prefix = "wechat", name = "enable", havingValue = "true")
public class WxCpConfiguration implements BeanDefinitionRegistryPostProcessor, EnvironmentAware {

    /**
     * 环境变量
     */
    private Environment environment;

    /**
     * 启用标识
     */
    private final static String TRUE = "true";

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // 获取配置信息
        BindResult<WxCpProperties> result = Binder.get(environment).bind("wechat", WxCpProperties.class);
        // 获取配置实体类
        WxCpProperties wxCpProperties = result.get();
        // 获取微信应用
        Map<String, WxCpProperties.AppConfig> appConfigs = wxCpProperties.getCp().getAppConfigs();
        for (String name : appConfigs.keySet()) {
            // 构造bean定义
            BeanDefinitionBuilder beanDefinitionBuilder =
                    BeanDefinitionBuilder.genericBeanDefinition(WxCpServiceImpl.class);
            WxCpProperties.AppConfig appConfig = appConfigs.get(name);
            // 判断是否启用
            if (appConfig.getEnable().equalsIgnoreCase(TRUE)) {
                WxCpDefaultConfigImpl configStorage = new WxCpDefaultConfigImpl();
                // 设置CorpId
                configStorage.setCorpId(wxCpProperties.getCp().getCorpId());
                // 设置AgentId
                configStorage.setAgentId(appConfig.getAgentId());
                // 设置Secret
                configStorage.setCorpSecret(appConfig.getSecret());
                // 设置代理host
                WxCpProperties.Agent agent = wxCpProperties.getAgent();
                if (agent != null && TRUE.equalsIgnoreCase(agent.getEnable())) {
                    // 代理host
                    configStorage.setHttpProxyHost(agent.getHost());
                    // 代理port
                    configStorage.setHttpProxyPort(Integer.parseInt(agent.getPort()));
                    // 代理username
                    configStorage.setHttpProxyUsername(agent.getUsername());
                    // 代理password
                    configStorage.setHttpProxyPassword(agent.getPassword());
                }
                beanDefinitionBuilder.addPropertyValue("WxCpConfigStorage",configStorage);
                //注册bean定义
                registry.registerBeanDefinition("cp" + name + "Service", beanDefinitionBuilder.getBeanDefinition());
                log.info("==============微信bean:{}配置成功=================", "cp" + name + "Service");
            }
        }

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

}