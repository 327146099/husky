package com.neusiri.husky.config;

import com.neusiri.husky.core.common.properties.HuskyProperties;
import com.neusiri.husky.core.common.properties.ScheduleProperties;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>xx-job配置</p>
 * <p>创建日期：2019-09-12</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Configuration
@Slf4j
public class XxlJobConfig {

    @Autowired
    private HuskyProperties huskyProperties;

    /**
     * 配置执行器
     *
     * @return xx-job执行器
     */
    @Bean(initMethod = "start", destroyMethod = "destroy")
    @ConditionalOnProperty(prefix = "husky.schedule", name = "admin-addresses")
    public XxlJobSpringExecutor xxlJobExecutor() {
        log.info(">>>>>>>>>>> xxl-job config init.");
        ScheduleProperties schedule = huskyProperties.getSchedule();
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(schedule.getAdminAddresses());
        xxlJobSpringExecutor.setAppName(schedule.getAppName());
        xxlJobSpringExecutor.setIp(schedule.getIp());
        xxlJobSpringExecutor.setPort(schedule.getPort());
        xxlJobSpringExecutor.setAccessToken(schedule.getAccessToken());
        xxlJobSpringExecutor.setLogPath(schedule.getLogPath());
        xxlJobSpringExecutor.setLogRetentionDays(schedule.getLogRetentionDays());
        log.info(">>>>>>>>>>> xxl-job config finished.");
        return xxlJobSpringExecutor;
    }

}
