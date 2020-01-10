package com.neusiri.husky.core.common.dict.service;

import com.neusiri.husky.core.common.dict.entity.Dict;
import com.neusiri.husky.core.common.dict.entity.DictInfo;

import java.util.List;

/**
 * <p>字典数据接口</p>
 * <p>创建日期：2019-09-09</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
public interface IDictService<T> {

    /**
     * 根据字典类型获取字典数据集合
     *
     * @param dictType 字典类型
     * @return 字典数据集合
     */
    List<Dict> getDictList(String dictType);

    /**
     * 根据字典类型和字典value获取字典数据
     *
     * @param dictType  字典类型
     * @param dictValue 字典value
     * @return 字典数据
     */
    Dict getDictData(String dictType, T dictValue);

    /**
     * 查询所有的字典数据
     *
     * @return 字典数据集合
     */
    List<DictInfo> getDictInfoList();
}
