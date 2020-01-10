package com.neusiri.husky.core.common.dict.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>数据字典类型信息</p>
 * <p>创建日期：2019-10-23</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Data
@ApiModel("字典数据")
public class DictInfo {

    /**
     * 字典名称
     */
    @ApiModelProperty("字典类型名称")
    private String name;

    /**
     * 字典类型
     */
    @ApiModelProperty("字典类型")
    private String type;

    /**
     * 字典数据
     */
    @ApiModelProperty("字典数据")
    private List<Dict> dictData;


}
