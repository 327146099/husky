package com.neusiri.husky.core.mybatis.paging;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.neusiri.husky.core.mybatis.util.PageUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * <p>分页aop</p>
 * <p>创建日期：2019-08-14</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Aspect
@Component
public class PagingAspect implements Ordered {

    /**
     * 切点
     */
    @Pointcut("execution(public * com.neusiri.husky..*Mapper.*(..))")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object handle(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                Object param = args[i];
                if (PageUtils.isAutoPage(param)) {
                    IPage pageInfo = PageUtils.getPageInfo();
                    args[i] = pageInfo;
                }
            }
        }
        return joinPoint.proceed(args);
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
