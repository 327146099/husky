package com.neusiri.husky.core.auth.util;

import com.neusiri.husky.core.auth.entity.Dept;
import com.neusiri.husky.core.auth.entity.SysDataScope;
import com.neusiri.husky.core.auth.entity.UserInfo;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * <p>权限工具类</p>
 * <p>创建日期：2019-08-13</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
public class AuthUtils {

    /**
     * 获取当前用户信息
     *
     * @return 当前用户信息
     */
    public static UserInfo getCurrentUser() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1L);
        userInfo.setAccount("admin");
        userInfo.setAdmin(true);
        userInfo.setUsername("管理员");
        userInfo.setRoles(Arrays.asList("system", "admin", "user"));
        userInfo.setAuthorities(Arrays.asList("system:*", "admin:*", "user:*"));

        // mock数据权限数据

        // 自定义数据权限
        SysDataScope sysDataScope = new SysDataScope();
        sysDataScope.setType("2");
        // 组织id
        sysDataScope.setDeptIds(Arrays.asList(101L, 103L, 108L));

        // 部门权限
        SysDataScope sysDataScope3 = new SysDataScope();
        sysDataScope3.setType("3");

        // 部门及其子部门权限
        SysDataScope sysDataScope4 = new SysDataScope();
        sysDataScope4.setType("4");

        // 个人权限
        SysDataScope sysDataScope5 = new SysDataScope();
        // 自定义数据权限
        sysDataScope5.setType("5");


        userInfo.setDataScopes(Arrays.asList(sysDataScope, sysDataScope3, sysDataScope4, sysDataScope5));

        Dept dept = new Dept();
        dept.setId(100L);
        dept.setDeptName("开发");
        userInfo.setDept(dept);

        return userInfo;
    }

    /**
     * 获取当前用户名称
     *
     * @return 用户名称
     */
    public static String getCurrentUserName() {
        UserInfo user = getCurrentUser();
        if (!Objects.isNull(user)) {
            return user.getUsername();
        }
        return null;
    }

    /**
     * 根据部门id获取所有部门id集合,包括孩子节点
     *
     * @param id 部门id
     * @return 部门及子部门集合
     */
    public static List<Long> getDeptTreeById(Long id) {
        return Arrays.asList(id, 105L, 108L);
    }

}
