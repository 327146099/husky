package com.neusiri.husky.core.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>自定义mapper接口</p>
 * <p>创建日期：2019-10-10</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
public interface BizBaseMapper<T> extends BaseMapper<T> {

    /**
     * 根据id删除方法,带填充效果
     *
     * @param entity 删除的参数
     * @return 操作行数
     */
    int deleteByIdWithFill(T entity);

}
