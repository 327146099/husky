package com.neusiri.husky.core.common.annotation;

import com.neusiri.husky.core.common.dict.constant.BusinessType;
import com.neusiri.husky.core.common.dict.constant.OperatorType;

import java.lang.annotation.*;

/**
 * <p>自定义操作日志记录注解</p>
 * <p>创建日期：2019-09-04</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 模块
     */
    String title() default "";

    /**
     * 功能
     */
    BusinessType businessType() default BusinessType.OTHER;

    /**
     * 操作人类别
     */
    OperatorType operatorType() default OperatorType.MANAGE;

    /**
     * 是否保存请求的参数
     */
    boolean isSaveRequestData() default true;

}
