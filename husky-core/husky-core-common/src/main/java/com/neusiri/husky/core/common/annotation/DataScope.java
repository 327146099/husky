package com.neusiri.husky.core.common.annotation;

import java.lang.annotation.*;

/**
 * <p>数据权限过滤注解</p>
 * <p>创建日期：2019-09-23</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {

    /**
     * 部门表的别名
     */
    String deptAlias() default "";

    /**
     * 用户表的别名
     */
    String userAlias() default "";

}
