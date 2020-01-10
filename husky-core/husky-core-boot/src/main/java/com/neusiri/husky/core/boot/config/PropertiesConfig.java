package com.neusiri.husky.core.boot.config;

import com.neusiri.husky.core.common.properties.HuskyProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>husky自定义配置属性</p>
 * <p>创建日期：2019-08-23</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Configuration
public class PropertiesConfig {

    @Bean
    @ConfigurationProperties(prefix = "husky")
    public HuskyProperties huskyProperties() {
        return new HuskyProperties();
    }
}
