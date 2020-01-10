package com.neusiri.husky.core.mybatis.interceptor;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.neusiri.husky.core.common.restful.model.AbstractBizEntity;
import com.neusiri.husky.core.mybatis.aspectj.DataScopeAspect;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>数据权限mybatis拦截器</p>
 * <p>创建日期：2019-09-25</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Component
@Slf4j
@Intercepts(@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}))
public class DataScopeInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        String sql = DataScopeAspect.dataScopeHolder.get();

        if (sql == null) {
            return invocation.proceed();
        }

        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        // 过滤select语句
        if (SqlCommandType.SELECT != ms.getSqlCommandType()) {
            return invocation.proceed();
        }

        // 如果为空串,说明不需要进行数据权限拦截
        if (StringUtils.isBlank(sql)) {
            sql = null;
        }

        Object param = args[1];
        if (param == null) {
            HashMap<Object, Object> hashMap = new HashMap<>(16);
            hashMap.put(AbstractBizEntity.Fields.dataPermission, sql);
            args[1] = hashMap;
            return invocation.proceed();
        }

        if (param instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) param;
            map.put(AbstractBizEntity.Fields.dataPermission.name(), sql);
            if (!StringUtils.isBlank(sql)) {
                AbstractWrapper<?, ?, ?> ew = (AbstractWrapper<?, ?, ?>) map.getOrDefault(Constants.WRAPPER, null);
                if (ew == null) {
                    ew = new QueryWrapper<>();
                    map.put(Constants.WRAPPER, ew);
                }
                ew.apply(sql);
            }
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }


}
