package com.neusiri.husky.core.mybatis.paging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>分页参数拦截器</p>
 * <p>创建日期：2019-08-14</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Component
@Slf4j
public class PagingHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String pageNum = WebUtils.findParameterValue(request, "pageNum");
        String pageSize = WebUtils.findParameterValue(request, "pageSize");

        if (StringUtils.isEmpty(pageNum) || StringUtils.isEmpty(pageSize)) {
            log.debug("不存在分页参数");
            PagingHolder.setPagingFlag(false);
        } else {
            PagingHolder.setPageNum(pageNum);
            PagingHolder.setPageSize(pageSize);
            PagingHolder.setPagingFlag(true);
        }

        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
        //清空pageHolder信息
        PagingHolder.clear();
    }


}
