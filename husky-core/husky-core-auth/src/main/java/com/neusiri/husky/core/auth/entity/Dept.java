package com.neusiri.husky.core.auth.entity;

import com.neusiri.husky.core.common.restful.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>部门信息</p>
 * <p>创建日期：2019-09-04</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Data
@ApiModel("部门信息")
public class Dept extends BaseEntity {

    /**
     * 部门名称
     */
    @ApiModelProperty("部门名称")
    private String deptName;
}
