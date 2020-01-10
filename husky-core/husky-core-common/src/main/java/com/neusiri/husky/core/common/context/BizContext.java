package com.neusiri.husky.core.common.context;

import java.util.Date;

/**
 * <p>业务数据环境上下文</p>
 * <p>创建日期：2019-08-16</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
public class BizContext {

    /**
     * 业务id容器
     */
    private static final ThreadLocal<String> BIZ_HOLDER = new ThreadLocal<>();

    /**
     * 操作时间容器
     */
    private static final ThreadLocal<Date> OPERATE_TIME_HOLDER = new ThreadLocal<>();

    public static String get() {
        return BIZ_HOLDER.get();
    }

    public static void set(String key) {
        BIZ_HOLDER.set(key);
    }

    public static void clear() {
        BIZ_HOLDER.remove();
    }

    public static Date getOperateTime() {
        return OPERATE_TIME_HOLDER.get();
    }

    public static void setOperateTime(Date operateTime) {
        OPERATE_TIME_HOLDER.set(operateTime);
    }

    public static void clearOperateTime() {
        OPERATE_TIME_HOLDER.remove();
    }

}
