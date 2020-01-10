package com.neusiri.husky.core.extension.monitor.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>系统文件相关信息</p>
 * <p>创建日期：2019-09-05</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Data
@ApiModel("系统文件相关信息")
public class SysFile  implements Serializable {

    /**
     * 盘符路径
     */
    @ApiModelProperty("系统文件相关信息")
    private String dirName;

    /**
     * 盘符类型
     */
    @ApiModelProperty("盘符类型")
    private String sysTypeName;

    /**
     * 文件类型
     */
    @ApiModelProperty("文件类型")
    private String typeName;

    /**
     * 总大小
     */
    @ApiModelProperty("总大小")
    private String total;

    /**
     * 剩余大小
     */
    @ApiModelProperty("剩余大小")
    private String free;

    /**
     * 已经使用量
     */
    @ApiModelProperty("已经使用量")
    private String used;

    /**
     * 资源的使用率
     */
    @ApiModelProperty("资源的使用率")
    private double usage;

}
