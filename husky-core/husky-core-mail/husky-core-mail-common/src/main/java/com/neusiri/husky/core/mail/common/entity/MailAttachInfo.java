package com.neusiri.husky.core.mail.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>邮件附件</p>
 * <p>创建日期：2019-10-10</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Data
@ApiModel("邮件附件")
@Accessors(chain = true)
public class MailAttachInfo implements Serializable {

    /**
     * 附件名称
     */
    @ApiModelProperty("附件名称")
    private String attachName;

    /**
     * 附件类型
     */
    @ApiModelProperty("附件类型")
    private String attachType;

    /**
     * 附件内容
     */
    @ApiModelProperty("附件内容")
    private byte[] content;
}
