package com.neusiri.husky.core.common.dict.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>标识一个枚举类为数据源</p>
 * <p>创建日期：2019-09-09</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DictEnum {
}
