package com.neusiri.husky.core.util.network;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>request工具类</p>
 * <p>创建日期：2019-09-04</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
public class RequestUtils {

    /**
     * 获取request
     *
     * @return request
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        return request;
    }

}
