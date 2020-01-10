package com.neusiri.husky.core.extension.monitor.entity;

import com.neusiri.husky.core.util.date.DateUtils;
import com.neusiri.husky.core.util.math.Arith;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.lang.management.ManagementFactory;

/**
 * <p>JVM相关信息</p>
 * <p>创建日期：2019-09-05</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@ToString
@Setter
@ApiModel("JVM相关信息")
public class Jvm implements Serializable {

    /**
     * 当前JVM占用的内存总数(M)
     */
    @ApiModelProperty("当前JVM占用的内存总数(M)")
    private double total;

    /**
     * JVM最大可用内存总数(M)
     */
    @ApiModelProperty("JVM最大可用内存总数(M)")
    private double max;

    /**
     * JVM空闲内存(M)
     */
    @ApiModelProperty("JVM空闲内存(M)")
    private double free;

    /**
     * JDK版本
     */
    @ApiModelProperty("JDK版本")
    private String version;

    /**
     * JDK路径
     */
    @ApiModelProperty("JDK路径")
    private String home;

    public double getTotal() {
        return Arith.div(total, (1024 * 1024), 2);
    }


    public double getMax() {
        return Arith.div(max, (1024 * 1024), 2);
    }


    public double getFree() {
        return Arith.div(free, (1024 * 1024), 2);
    }


    @ApiModelProperty(value = "jvm内存占用", notes = "单位(M)")
    public double getUsed() {
        return Arith.div(total - free, (1024 * 1024), 2);
    }

    @ApiModelProperty(value = "jvm内存使用率", notes = "单位(%)")
    public double getUsage() {
        return Arith.mul(Arith.div(total - free, total, 4), 100);
    }

    /**
     * 获取JDK名称
     */
    @ApiModelProperty("获取JDK名称")
    public String getName() {
        return ManagementFactory.getRuntimeMXBean().getVmName();
    }

    public String getVersion() {
        return version;
    }


    public String getHome() {
        return home;
    }

    /**
     * JDK启动时间
     */
    @ApiModelProperty("JDK启动时间")
    public String getStartTime() {
        return DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, DateUtils.getServerStartDate());
    }

    /**
     * JDK运行时间
     */
    @ApiModelProperty("JDK运行时间")
    public String getRunTime() {
        return DateUtils.getDatePoor(DateUtils.getNowDate(), DateUtils.getServerStartDate());
    }

}
