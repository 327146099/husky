package com.neusiri.husky.core.mybatis.config;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;

public class MybatisPlusCustomizers implements ConfigurationCustomizer {


    @Override
    public void customize(Configuration configuration) {
        configuration.setJdbcTypeForNull(JdbcType.NULL);
    }
}
