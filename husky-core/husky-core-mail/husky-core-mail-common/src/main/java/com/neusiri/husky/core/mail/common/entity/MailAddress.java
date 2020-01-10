package com.neusiri.husky.core.mail.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>邮件地址信息</p>
 * <p>创建日期：2019-10-17</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Data
@Accessors
@ApiModel("邮件地址信息")
public class MailAddress implements Serializable {


    /**
     * 邮件地址
     */
    @ApiModelProperty("邮件地址")
    protected String address;

    /**
     * 名称
     */
    @ApiModelProperty("接收人名称")
    protected String personal;

}
