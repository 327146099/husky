package com.neusiri.husky.core.boot.filter;

import com.neusiri.husky.core.common.context.BizContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * <p>请求业务id生成拦截器</p>
 * <p>创建日期：2019-08-16</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Slf4j
@Configuration
@WebFilter(urlPatterns = {"/**"})
@Order(Ordered.HIGHEST_PRECEDENCE)
public class BizContextFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 通过uuid构造bizId
        String bizId = UUID.randomUUID().toString().replace("-", "");
        // 设置bizId
        BizContext.set(bizId);
        // 设置操作时间
        BizContext.setOperateTime(new Date());
        try {
            // 执行业务方法
            chain.doFilter(request, response);
        } finally {
            // 清空bizId
            BizContext.clear();
            // 清空操作时间
            BizContext.clearOperateTime();
        }
    }

}
