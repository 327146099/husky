package com.neusiri.husky.core.common.restful.request.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>分页查询参数</p>
 * <p>创建日期：2019-08-14</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@ApiModel("分页查询条件")
@Data
public class PagingQueryParam extends QueryParam {

    /**
     * 查询的页数
     */
    @ApiModelProperty(value = "查询的页码", example = "1", required = true)
    private Integer pageNum;

    /**
     * 每页条数
     */
    @ApiModelProperty(value = "每页条数", example = "10", required = true)
    private Integer pageSize;

}
