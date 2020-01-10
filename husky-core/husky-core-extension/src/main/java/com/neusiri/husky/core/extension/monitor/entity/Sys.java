package com.neusiri.husky.core.extension.monitor.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>系统相关信息</p>
 * <p>创建日期：2019-09-05</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Data
@ApiModel("系统相关信息")
public class Sys  implements Serializable {

    /**
     * 服务器名称
     */
    @ApiModelProperty("服务器名称")
    private String computerName;

    /**
     * 服务器Ip
     */
    @ApiModelProperty("服务器Ip")
    private String computerIp;

    /**
     * 项目路径
     */
    @ApiModelProperty("项目路径")
    private String userDir;

    /**
     * 操作系统
     */
    @ApiModelProperty("操作系统")
    private String osName;

    /**
     * 系统架构
     */
    @ApiModelProperty("系统架构")
    private String osArch;


}
