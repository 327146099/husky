package com.neusiri.husky.core.swagger.annotations;

import java.lang.annotation.*;

/**
 * <p>swagger增强注解</p>
 * <p>创建日期：2019-08-14</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiOperationEnhance {

    /**
     * 是否隐藏业务字段
     *
     * @return true, false
     */
    boolean ignoreBiz() default false;

    /**
     * 如果隐藏业务字段,是否显示实体的id
     *
     * @return true, false
     */
    boolean showId() default false;

    /**
     * 是否隐藏分页参数
     */
    boolean ignorePaging() default false;

}
