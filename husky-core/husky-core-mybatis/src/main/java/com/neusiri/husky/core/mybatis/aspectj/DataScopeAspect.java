package com.neusiri.husky.core.mybatis.aspectj;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.common.base.Joiner;
import com.neusiri.husky.core.auth.entity.Dept;
import com.neusiri.husky.core.auth.entity.SysDataScope;
import com.neusiri.husky.core.auth.entity.UserInfo;
import com.neusiri.husky.core.auth.util.AuthUtils;
import com.neusiri.husky.core.common.annotation.DataScope;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class DataScopeAspect {

    public final static ThreadLocal<String> dataScopeHolder = new ThreadLocal<>();

    /**
     * 全部数据权限
     */
    public static final String DATA_SCOPE_ALL = "1";

    /**
     * 自定数据权限
     */
    public static final String DATA_SCOPE_CUSTOM = "2";

    /**
     * 部门数据权限
     */
    public static final String DATA_SCOPE_DEPT = "3";

    /**
     * 部门及以下数据权限
     */
    public static final String DATA_SCOPE_DEPT_AND_CHILD = "4";

    /**
     * 仅本人数据权限
     */
    public static final String DATA_SCOPE_SELF = "5";

    private static final String EMPTY_SQL = " 0 = 1";

    /**
     * 数据权限过滤关键字
     */
    public static final String DATA_SCOPE = "data_permission";

    private static Joiner joiner = Joiner.on(",").skipNulls();

    /**
     * 配置织入点
     */
    @Pointcut("@annotation(com.neusiri.husky.core.common.annotation.DataScope)")
    public void dataScopePointCut() {
    }

    @Before("dataScopePointCut()")
    public void doBefore(JoinPoint point) throws Throwable {
        handleDataScope(point);
    }

    @After("dataScopePointCut()")
    public void doAfter(JoinPoint point) throws Throwable {
        dataScopeHolder.remove();
    }

    protected void handleDataScope(final JoinPoint joinPoint) {
        // 获得注解
        DataScope dataScope = getAnnotationLog(joinPoint);
        if (Objects.isNull(dataScope)) {
            return;
        }

        // 获取当前的用户
        UserInfo currentUser = AuthUtils.getCurrentUser();
        if (!Objects.isNull(currentUser)) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                String sql = dataScopeFilter(joinPoint, currentUser, dataScope.deptAlias(), dataScope.userAlias());
                log.debug("数据权限过滤的sql为 [{}]", sql);
                dataScopeHolder.set(sql);
            }else {
                dataScopeHolder.set("");
            }
        }
    }

    /**
     * 数据范围过滤
     *
     * @param joinPoint 切点
     * @param user      用户
     * @param deptAlias 部门别名
     * @param userAlias 用户别名
     */
    private static String dataScopeFilter(JoinPoint joinPoint, UserInfo user, String deptAlias, String userAlias) {
        Set<String> sqlList = new HashSet<>();

        List<SysDataScope> dataScopes = user.getDataScopes();
        Dept dept = user.getDept();
        Long deptId = Objects.isNull(dept) ? null : dept.getId();

        if (CollectionUtils.isNotEmpty(dataScopes)) {
            // 遍历设置的数据权限设置
            for (SysDataScope dataScope : dataScopes) {
                // 获取数据权限的类型
                String type = dataScope.getType();
                // 获取部门表别名
                String alias = StringUtils.isEmpty(deptAlias) ? "" : deptAlias + ".";
                // 如果数据权限为全部
                if (DATA_SCOPE_ALL.equals(type)) {
                    sqlList.clear();
                    break;
                } else if (DATA_SCOPE_CUSTOM.equals(type)) {
                    if (!CollectionUtils.isEmpty(dataScope.getDeptIds())) {
                        String joinSql = joinLongAsString(dataScope.getDeptIds());
                        sqlList.add(StringUtils.format(" %s%s IN ( %s ) ", alias, DATA_SCOPE, joinSql));
                    } else {
                        sqlList.add(EMPTY_SQL);
                    }
                } else if (DATA_SCOPE_DEPT.equals(type)) {

                    if (!Objects.isNull(deptId)) {
                        sqlList.add(StringUtils.format(" %s%s = '%s' ", alias, DATA_SCOPE, dept.getId()));
                    } else {
                        sqlList.add(EMPTY_SQL);
                    }

                } else if (DATA_SCOPE_DEPT_AND_CHILD.equals(type)) {

                    if (Objects.isNull(deptId)) {
                        sqlList.add(EMPTY_SQL);
                        continue;
                    }
                    // 获取用户部门id及其孩子部门id集合
                    List<Long> deptIds = AuthUtils.getDeptTreeById(deptId);

                    if (CollectionUtils.isEmpty(deptIds)) {
                        sqlList.add(EMPTY_SQL);
                        continue;
                    }
                    String joinSql = joinLongAsString(deptIds);
                    sqlList.add(StringUtils.format(" %s%s IN ( %s ) ", alias, DATA_SCOPE, joinSql));

                } else if (DATA_SCOPE_SELF.equals(type)) {
                    userAlias = StringUtils.isEmpty(userAlias) ? "" : userAlias + ".";

                    sqlList.add(StringUtils.format(" %screate_by = '%s' ", userAlias, user.getId()));
                }
            }

            // 如果数据权限过滤集合为空,说明可以查询所有的数据
            if (CollectionUtils.isEmpty(sqlList)) {
                return "";
            }
            // 如果获取的权限过滤集合大于1,则删除空查询条件
            if (sqlList.contains(EMPTY_SQL) && sqlList.size() > 1) {
                sqlList.remove(EMPTY_SQL);
            }

            return StringUtils.format("( %s )", Joiner.on(" or ").join(sqlList));
        }
        // 如果该人员没有设置数据权限,那么应该不能查询出数据
        return StringUtils.format("( %s )", EMPTY_SQL);
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private DataScope getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(DataScope.class);
        }
        return null;
    }

    /**
     * long类型集合连接成string字符串
     * 1,2,3  ->   '1','2','3'
     *
     * @param ids long数据集合
     * @return 连接成的字符串
     */
    private static String joinLongAsString(List<Long> ids) {
        List<String> idStrList = ids.stream().map((id) -> {
            return StringUtils.format("'%s'", id);
        }).collect(Collectors.toList());

        return joiner.join(idStrList);
    }

}
