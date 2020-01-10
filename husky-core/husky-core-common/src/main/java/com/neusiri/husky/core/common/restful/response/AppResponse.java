package com.neusiri.husky.core.common.restful.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

/**
 * <p>通用返回实体</p>
 * <p>创建日期：2019-06-28</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Getter
@Builder
@ApiModel(description = "返回响应数据")
public class AppResponse<T> implements Serializable {

    @ApiModelProperty(value = "返回编码", notes = "0表示操作成功")
    private Integer code;

    @ApiModelProperty(value = "返回信息")
    private String msg;

    @ApiModelProperty(value = "返回对象")
    private T data;

    public static <T> AppResponse<T> ok() {
        return AppResponse.<T>builder().code(ResponseStatus.OK.getCode()).msg("success").build();
    }

    public static <T> AppResponse<T> ok(T data) {
        return AppResponse.<T>builder().code(ResponseStatus.OK.getCode()).msg("success").data(data).build();
    }

    public static <T> AppResponse<T> ok(String msg, T data) {
        return AppResponse.<T>builder().code(ResponseStatus.OK.getCode()).msg(msg).data(data).build();
    }

    public static <T> AppResponse<T> fail() {
        return AppResponse.<T>builder().code(ResponseStatus.ERROR.getCode()).msg("fail").build();
    }

    public static AppResponse invalidToken(String msg) {
        return AppResponse.builder().code(ResponseStatus.INVALID_TOKEN.getCode()).msg(msg).build();
    }

    public static AppResponse unauthorized(String msg) {
        return AppResponse.builder().code(ResponseStatus.UNAUTHORIZED.getCode()).msg(msg).build();
    }


    public static <T> AppResponse<T> fail(String msg) {
        return AppResponse.<T>builder().code(ResponseStatus.ERROR.getCode()).msg(msg).build();
    }

    public static <T> AppResponse<T> fail(String msg, T data) {
        return AppResponse.<T>builder().code(ResponseStatus.ERROR.getCode()).msg(msg).data(data).build();
    }

    public static <T> AppResponse<T> fail(Integer code, String msg) {
        return AppResponse.<T>builder().code(code).msg(msg).build();
    }


}
