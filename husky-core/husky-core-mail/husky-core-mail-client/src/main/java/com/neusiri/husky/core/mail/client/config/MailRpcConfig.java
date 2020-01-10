package com.neusiri.husky.core.mail.client.config;

import com.xxl.rpc.remoting.invoker.impl.XxlRpcSpringInvokerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>邮件服务远程调用配置</p>
 * <p>创建日期：2019-10-15</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Configuration
@Slf4j
@ConditionalOnProperty(name = "husky.mail.server-address")
public class MailRpcConfig {

    @Value("${husky.mail.server-address}")
    private String mailServerAddress;

    @Bean
    public XxlRpcSpringInvokerFactory xxlJobExecutor() {
        XxlRpcSpringInvokerFactory invokerFactory = new XxlRpcSpringInvokerFactory();
        invokerFactory.setServiceRegistryClass(HuskyLocalServiceRegistry.class);
        Map<String, String> params = new HashMap<>(16);
        params.put("serverAddress", mailServerAddress);
        invokerFactory.setServiceRegistryParam(params);

        log.info(">>>>>>>>>>> xxl-rpc invoker config init finish.");
        return invokerFactory;
    }

}
