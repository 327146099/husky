package com.neusiri.husky.core.extension.log.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neusiri.husky.core.common.restful.model.BaseEntity;
import com.neusiri.husky.core.util.date.DateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * <p>操作日志记录</p>
 * <p>创建日期：2019-09-04</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Data
@ApiModel("操作日志记录")
@TableName("t_sys_operation_log")
public class OperationLog extends BaseEntity {

    /**
     * 操作模块
     */
    @ApiModelProperty("操作模块")
    @Excel(name = "操作模块")
    private String title;

    /**
     * 业务类型
     */
    @ApiModelProperty("业务类型")
    @Excel(name = "业务类型", dict = "enum#businessType")
    private Integer businessType;

    /**
     * 业务类型数组
     */
    @ApiModelProperty("业务类型数组")
    @TableField(exist = false)
    @JsonIgnore
    private Integer[] businessTypes;

    /**
     * 请求方法
     */
    @ApiModelProperty("请求方法")
    @Excel(name = "请求方法", width = 70)
    private String method;

    /**
     * 请求方式
     */
    @ApiModelProperty("请求方式")
    @Excel(name = "请求方式")
    private String requestMethod;

    /**
     * 操作人类别
     */
    @ApiModelProperty("操作人类别")
    @Excel(name = "操作人类别", dict = "enum#operatorType")
    private Integer operatorType;

    /**
     * 操作人员
     */
    @ApiModelProperty("操作人员")
    @Excel(name = "操作人员")
    private String operatorName;

    /**
     * 部门名称
     */
    @ApiModelProperty("部门名称")
    @Excel(name = "部门名称")
    private String deptName;

    /**
     * 请求url
     */
    @ApiModelProperty("请求url")
    @Excel(name = "请求url", width = 50)
    private String operateUrl;

    /**
     * 操作地址
     */
    @ApiModelProperty("操作地址")
    @Excel(name = "操作地址")
    private String operateIp;

    /**
     * 操作地点
     */
    @ApiModelProperty("操作地点")
    @Excel(name = "操作地点")
    private String operateLocation;

    /**
     * 请求参数
     */
    @ApiModelProperty("请求参数")
    @Excel(name = "请求参数", width = 50)
    private String operateParam;

    /**
     * 状态 0正常 1异常
     */
    @ApiModelProperty(value = "状态", notes = "0正常 1异常")
    @Excel(name = "状态", dict = "enum#businessStatus")
    private Integer status;

    /**
     * 错误消息
     */
    @ApiModelProperty(value = "错误消息")
    @Excel(name = "错误消息")
    private String errorMsg;

    /**
     * 操作时间
     */
    @ApiModelProperty(value = "操作时间")
    @Excel(name = "操作时间", format = DateUtils.YYYY_MM_DD_HH_MM_SS, width = 20)
    private Date operateTime;

}
