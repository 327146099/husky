package com.neusiri.husky.core.export.excel.dict;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>字典信息</p>
 * <p>创建日期：2019-10-09</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Data
@Accessors(chain = true)
public class DictInfo<T> {

    /**
     * 字典名称
     */
    private String name;

    /**
     * 字典值
     */
    private T value;

}
