package com.neusiri.husky.core.common.restful.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>返回代码枚举类</p>
 * <p>创建日期：2017-04-25</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@AllArgsConstructor
@Getter
public enum ResponseStatus {

    /**
     * 正常
     */
    OK(0, "OK"),
    /**
     * 系统错误
     */
    ERROR(-1, "ERROR"),

    /**
     * 参数异常
     */
    PARAM_ERROR(-2, "PARAM_ERROR"),

    /**
     * 系统异常
     */
    SERVICE_ERROR(-3, "SERVICE_ERROR"),

    /**
     * 业务异常
     */
    BIZ_ERROR(-4, "BIZ_ERROR"),

    /**
     * session超期
     */
    TIMEOUT(-501, "TIMEOUT"),

    /**
     * token非法
     */
    INVALID_TOKEN(-502, "INVALID_TOKEN"),

    /**
     * 没有权限
     */
    UNAUTHORIZED(-503, "UNAUTHORIZED");

    private int code;

    private String reasonPhrase;

}
