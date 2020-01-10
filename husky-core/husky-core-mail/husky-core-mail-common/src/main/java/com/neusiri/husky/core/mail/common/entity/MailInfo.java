package com.neusiri.husky.core.mail.common.entity;

import com.neusiri.husky.core.mail.common.constant.SendType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>邮件信息</p>
 * <p>创建日期：2019-10-10</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Data
@ApiModel("邮件信息")
@ToString(exclude = {"attachInfo"})
public class MailInfo implements Serializable {

    /**
     * 发送邮件服务名称
     */
    @ApiModelProperty("发送邮件服务名称")
    private String mailServerName;

    /**
     * 是否使用默认邮件服务
     */
    @ApiModelProperty("是否使用默认邮件服务")
    private Boolean useDefaultMailServer;

    /**
     * 接收人
     */
    @ApiModelProperty("接收人")
    private MailAddress[] to;

    /**
     * 发送人,如果发件人为空,则使用邮件服务设置的默认发件人
     */
    @ApiModelProperty(value = "发送人", notes = "如果发件人为空,则使用邮件服务设置的默认发件人")
    private String from;

    /**
     * 抄送人
     */
    @ApiModelProperty("抄送人")
    private MailAddress[] cc;

    /**
     * 邮件主题
     */
    @ApiModelProperty("邮件主题")
    private String subject;

    /**
     * 邮件主题内容
     */
    @ApiModelProperty("邮件主题内容")
    private String text;

    /**
     * 发送类型
     */
    @ApiModelProperty("发送类型")
    private SendType sendType = SendType.IMMEDIATELY;

    /**
     * 发送时间
     */
    @ApiModelProperty("发送时间")
    private Date scheduleDate;

    /**
     * 附件信息
     */
    @ApiModelProperty(hidden = true)
    private List<MailAttachInfo> attachInfo;

}
