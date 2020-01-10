package com.neusiri.husky.core.util.biz;

import lombok.experimental.UtilityClass;

/**
 * <p>执行时间计算工具类</p>
 * <p>创建日期：2019-10-17</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@UtilityClass
public class ExecuteTimeUtils {

    private static ThreadLocal<Long> EXECUTE_TIME_CONTENT = new ThreadLocal<>();

    /**
     * 保存方法执行开始时间
     */
    public static void start() {
        EXECUTE_TIME_CONTENT.set(System.currentTimeMillis());
    }

    public static void clear() {
        EXECUTE_TIME_CONTENT.remove();
    }

    /**
     * 计算执行时间, 单位(ms)
     */
    public static long end() {
        Long start = EXECUTE_TIME_CONTENT.get();
        Long end = System.currentTimeMillis();
        ExecuteTimeUtils.clear();
        return end - start;
    }
}
