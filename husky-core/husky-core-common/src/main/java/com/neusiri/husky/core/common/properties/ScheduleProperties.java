package com.neusiri.husky.core.common.properties;

import lombok.Data;

/**
 * <p>定时任务属性</p>
 * <p>创建日期：2019-09-12</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Data
public class ScheduleProperties {

    private String adminAddresses;

    private String appName;

    private String ip;

    private int port;

    private String accessToken;

    private String logPath;

    private int logRetentionDays;


}
