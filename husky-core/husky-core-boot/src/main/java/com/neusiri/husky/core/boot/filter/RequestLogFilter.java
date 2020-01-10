package com.neusiri.husky.core.boot.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neusiri.husky.core.common.context.BizContext;
import com.neusiri.husky.core.util.network.UrlUtils;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>接口调用日志拦截器</p>
 * <p>创建日期：2019-08-13</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Slf4j
//@WebFilter(urlPatterns = {"/**"})
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
//@Configuration
public class RequestLogFilter implements Filter {

    private static final String POST = "post";

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("------请求信息记录拦截器加载------");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        // 获取请求方式
        String method = httpRequest.getMethod();
        // 是否是静态方法
        if (UrlUtils.isStaticPath(requestURI)) {
            // 放行
            chain.doFilter(request, response);
            return;
        }

        String body = null;

        // 如果为post方法并且上传类型为x-www-form-urlencoded或json,打印body
        boolean notFileUpload = isHoldUp(httpRequest);

        if (notFileUpload) {
            // 可重复度 自定义request,body
            RequestWrapper requestWrapper = new RequestWrapper(httpRequest);
            // 获取body内容
            body = requestWrapper.getBody();
            request = requestWrapper;
        } else {
            body = "body不拦截";
        }

        // 获取请求地址上的参数
        Map<String, Object> params = getRequestParams(httpRequest);
        // 获取请求头的信息
        Map<String, Object> headers = getHeaders(httpRequest);

        // 获取浏览器信息
        String userAgent = httpRequest.getHeader("User-Agent");
        // 获取浏览器信息的工具类
        Browser browser = UserAgent.parseUserAgentString(userAgent).getBrowser();

        // 请求日志包装
        LogRequest logRequest = LogRequest.builder()
                .url(requestURI)
                .method(method)
                .headers(headers)
                .body(body)
                .params(params)
                .browser(browser)
                .build();
        log.info("[{}], 请求详细信息 \n请求地址 -> [{}  {}] 浏览器为[{}]\nQuery参数 -> [{}]\n请求头 -> [{}]\n请求体 -> [{}]", BizContext.get(), logRequest.getUrl(), logRequest.getMethod(),
                logRequest.getBrowser().getName(), logRequest.getParams(), logRequest.getHeaders(), logRequest.getBody());

        // 调用过滤器链
        chain.doFilter(request, response);

    }

    @Override
    public void destroy() {
    }

    /**
     * 判断是否需要打印body
     *
     * @param request 请求
     * @return 结果
     */
    private boolean isHoldUp(HttpServletRequest request) {

        if (!POST.equalsIgnoreCase(request.getMethod())) {
            return false;
        }

        String contentType = request.getContentType();

        return StringUtils.startsWithIgnoreCase(contentType, MediaType.APPLICATION_FORM_URLENCODED_VALUE) ||
                StringUtils.startsWithIgnoreCase(contentType, MediaType.APPLICATION_JSON_VALUE);
    }

    /**
     * 获取请求地址上的参数
     *
     * @param request 请求
     * @return 请求头信息
     */
    private static Map<String, Object> getRequestParams(HttpServletRequest request) {
        Enumeration<String> enu = request.getParameterNames();
        Map<String, Object> params = new HashMap<>(16);
        // 获取请求参数
        while (enu.hasMoreElements()) {
            String name = enu.nextElement();
            params.put(name, request.getParameter(name));
        }
        return params;
    }

    /**
     * 获取请求头的信息
     *
     * @param request 请求
     * @return 请求头信息
     */
    private static Map<String, Object> getHeaders(HttpServletRequest request) {
        Enumeration<String> enu = request.getHeaderNames();
        Map<String, Object> params = new HashMap<>(16);
        // 获取请求参数
        while (enu.hasMoreElements()) {
            String name = enu.nextElement();
            params.put(name, request.getHeader(name));
        }
        return params;
    }

}
