package com.neusiri.husky.core.boot.filter;

import eu.bitwalker.useragentutils.Browser;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

/**
 * <p>请求日志信息</p>
 * <p>创建日期：2019-08-16</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Builder
@ToString
@Getter
public class LogRequest {

    /**
     * 请求url
     */
    private String url;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求头
     */
    private Map<String, Object> headers;

    /**
     * query参数
     */
    private Map<String, Object> params;

    /**
     * 请求体
     */
    private String body;

    /**
     * 浏览器信息
     */
    private Browser browser;

}
