package com.neusiri.husky.core.common.constant;

/**
 * <p>校验分组参数</p>
 * <p>创建日期：2019-09-09</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
public interface ValidGroup {

    /**
     * 添加校验分组
     */
    interface Add extends ValidGroup {
    }

    /**
     * 修改校验分组
     */
    interface Edit extends ValidGroup {
    }

    /**
     * 查询校验分组
     */
    class Query implements ValidGroup {
    }

    /**
     * 删除校验分组
     */
    class Remove implements ValidGroup {
    }


}

