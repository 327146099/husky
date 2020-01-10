package com.neusiri.husky.core.extension.monitor.entity;

import com.neusiri.husky.core.util.math.Arith;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>內存相关信息</p>
 * <p>创建日期：2019-09-05</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@ToString
@Setter
@ApiModel("內存相关信息")
public class Mem implements Serializable {

    /**
     * 内存总量
     */
    @ApiModelProperty("内存总量")
    private double total;

    /**
     * 已用内存
     */
    @ApiModelProperty("已用内存")
    private double used;

    /**
     * 剩余内存
     */
    @ApiModelProperty("剩余内存")
    private double free;

    public double getTotal() {
        return Arith.div(total, (1024 * 1024 * 1024), 2);
    }

    public double getUsed() {
        return Arith.div(used, (1024 * 1024 * 1024), 2);
    }

    public double getFree() {
        return Arith.div(free, (1024 * 1024 * 1024), 2);
    }

    /**
     * 内存使用率
     *
     * @return 内存使用率
     */
    @ApiModelProperty("内存使用率")
    public double getUsage() {
        return Arith.mul(Arith.div(used, total, 4), 100);
    }


}
