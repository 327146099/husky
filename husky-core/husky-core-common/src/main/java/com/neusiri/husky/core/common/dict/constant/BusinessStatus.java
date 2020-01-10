package com.neusiri.husky.core.common.dict.constant;

import com.neusiri.husky.core.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>操作状态</p>
 * <p>创建日期：2019-09-04</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@AllArgsConstructor
@Getter
public enum BusinessStatus implements BaseEnum<Integer> {

    /**
     * 成功
     */
    SUCCESS(0, "成功"),

    /**
     * 失败
     */
    FAIL(1, "失败");

    private final Integer value;

    private final String name;

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getName() {
        return name;
    }
}
