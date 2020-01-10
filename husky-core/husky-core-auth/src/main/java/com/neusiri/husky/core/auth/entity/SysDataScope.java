package com.neusiri.husky.core.auth.entity;

import lombok.Data;

import java.util.List;

/**
 * <p>数据权限实体</p>
 * <p>创建日期：2019-09-24</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Data
public class SysDataScope {

    /**
     * 数据权限类型
     */
    private String type;

    /**
     * 部门id集合
     */
    private List<Long> deptIds;

}
