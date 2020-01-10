package com.neusiri.husky.core.mail.common.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.neusiri.husky.core.common.enums.BaseEnum;
import lombok.AllArgsConstructor;

/**
 * 发送类型
 */
@AllArgsConstructor
public enum SendType implements BaseEnum<Integer> {

    /**
     * 定时
     */
    SCHEDULE(1, "定时发送"),

    /**
     * 立即发送
     */
    IMMEDIATELY(0, "立即发送");

    @EnumValue
    private Integer value;

    private String name;

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getName() {
        return name;
    }
}
