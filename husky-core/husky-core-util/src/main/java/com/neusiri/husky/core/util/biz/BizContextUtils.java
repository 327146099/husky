package com.neusiri.husky.core.util.biz;

import com.neusiri.husky.core.common.context.BizContext;

import java.util.Date;

/**
 * <p>biz上下文工具类</p>
 * <p>创建日期：2019-08-16</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
public class BizContextUtils {

    /**
     * 获取bizId
     *
     * @return bizId
     */
    public static String getBizId() {
        return BizContext.get();
    }

    /**
     * 获取操作时间
     *
     * @return 操作时间
     */
    public static Date getOperateTime() {
        return BizContext.getOperateTime() != null ? BizContext.getOperateTime() : new Date();
    }

}
