package com.neusiri.husky.core.mybatis.conditions;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

/**
 * <p>自定义QueryWrapper</p>
 * <p>创建日期：2019-08-14</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
public class CustomQueryWrapper<T> extends QueryWrapper<T> {

    @Override
    protected String columnToString(String column) {
        // 驼峰命名转换为下划线命名
        return StringUtils.camelToUnderline(column);
    }



}
