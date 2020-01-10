package com.neusiri.husky.core.extension.monitor.entity;

import com.neusiri.husky.core.util.math.Arith;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>CPU相关信息</p>
 * <p>创建日期：2019-09-05</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@ToString
@Setter
@ApiModel("CPU相关信息")
public class Cpu implements Serializable {

    /**
     * 核心数
     */
    @ApiModelProperty("核心数")
    private int cpuNum;

    /**
     * CPU总的使用率
     */
    @ApiModelProperty("CPU总的使用率")
    private double total;

    /**
     * CPU系统使用率
     */
    @ApiModelProperty("CPU系统使用率")
    private double sys;

    /**
     * CPU用户使用率
     */
    @ApiModelProperty("CPU用户使用率")
    private double used;

    /**
     * CPU当前等待率
     */
    @ApiModelProperty("CPU当前等待率")
    private double wait;

    /**
     * CPU当前空闲率
     */
    @ApiModelProperty("CPU当前空闲率")
    private double free;

    public int getCpuNum() {
        return cpuNum;
    }


    public double getTotal() {
        return Arith.round(Arith.mul(total, 100), 2);
    }


    public double getSys() {
        return Arith.round(Arith.mul(sys / total, 100), 2);
    }


    public double getUsed() {
        return Arith.round(Arith.mul(used / total, 100), 2);
    }


    public double getWait() {
        return Arith.round(Arith.mul(wait / total, 100), 2);
    }


    public double getFree() {
        return Arith.round(Arith.mul(free / total, 100), 2);
    }


}
