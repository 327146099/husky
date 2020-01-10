package com.neusiri.husky.core.common.dict.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>系统数据字典</p>
 * <p>创建日期：2019-09-09</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Data
@ApiModel("系统数据字典")
public class Dict implements Serializable {

    /**
     * 数据字典类型
     */
    @ApiModelProperty("数据字典类型")
    private String dictType;

    /**
     * 字典名称
     */
    @ApiModelProperty("字典名称")
    private String label;

    /**
     * 字典值
     */
    @ApiModelProperty("字典值")
    private Object value;

}
