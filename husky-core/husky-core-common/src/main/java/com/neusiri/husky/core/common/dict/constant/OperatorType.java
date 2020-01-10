package com.neusiri.husky.core.common.dict.constant;

import com.neusiri.husky.core.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>操作人类别</p>
 * <p>创建日期：2019-09-04</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@AllArgsConstructor
@Getter
public enum OperatorType implements BaseEnum<Integer> {

    /**
     * 其它
     */
    OTHER(99, "其它"),

    /**
     * 管理员
     */
    ADMIN(0, "管理员"),

    /**
     * 后台用户
     */
    MANAGE(1, "后台用户"),

    /**
     * 手机端用户
     */
    MOBILE(2, "手机端用户");


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
