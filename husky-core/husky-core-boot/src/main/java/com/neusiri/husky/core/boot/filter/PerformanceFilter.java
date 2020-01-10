package com.neusiri.husky.core.boot.filter;

import com.neusiri.husky.core.common.context.BizContext;
import com.neusiri.husky.core.util.network.IpUtils;
import com.neusiri.husky.core.util.network.UrlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <p>请求执行效率拦截器</p>
 * <p>创建日期：2019-08-16</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Slf4j
@WebFilter(urlPatterns = {"/**"})
@Configuration
public class PerformanceFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("------请求执行效率拦截器加载------");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestUri = httpRequest.getRequestURI();
        // 如果是静态地址，放行
        if (UrlUtils.isStaticPath(requestUri)) {
            chain.doFilter(request, response);
            return;
        }
        // 设置开始时间
        long start = System.currentTimeMillis();
        //调用过滤器链
        chain.doFilter(request, response);
        // 设置结束时间
        long end = System.currentTimeMillis();
        // 计算差值
        long executionTime = end - start;
        // 获取调用的远程地址
        String requestIp = IpUtils.getIpAddr(httpRequest);

        log.info("[{}], [{}]----->[{}]------执行时间[{}ms]", BizContext.get(), requestIp, requestUri, executionTime);
    }


    @Override
    public void destroy() {

    }

}
