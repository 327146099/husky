package com.neusiri.husky.core.mail.common.service;

import com.neusiri.husky.core.common.restful.response.AppResponse;
import com.neusiri.husky.core.mail.common.entity.MailInfo;

/**
 * <p></p>
 * <p>创建日期：2019-10-10</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
public interface MailSendService {

    /**
     * 发送邮件
     *
     * @param mailInfo 邮件信息
     * @return 发送结果
     */
    AppResponse sendMail(MailInfo mailInfo);


}
