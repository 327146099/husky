package com.neusiri.husky.core.common.enums;

import java.io.Serializable;

/**
 * <p>自定义枚举接口</p>
 * <p>创建日期：2019-09-09</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
public interface BaseEnum<T extends Serializable> {

    /**
     * 枚举数据库存储值
     *
     * @return 枚举值
     */
    T getValue();

    /**
     * 获取枚举名称
     *
     * @return 枚举名称
     */
    String getName();


}
