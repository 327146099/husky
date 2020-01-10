package com.neusiri.husky.core.mail.client.service;

import com.neusiri.husky.core.common.restful.response.AppResponse;
import com.neusiri.husky.core.mail.common.entity.MailInfo;
import com.neusiri.husky.core.mail.common.service.MailSendService;
import com.xxl.rpc.remoting.invoker.annotation.XxlRpcReference;
import org.springframework.stereotype.Service;

/**
 * <p>邮件服务</p>
 * <p>创建日期：2019-10-15</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Service
public class MailService {

    @XxlRpcReference(timeout = 1000L * 30)
    private MailSendService mailSendService;

    /**
     * 同步发送邮件
     *
     * @param mailInfo 邮件信息
     * @return 发送结果
     */
    public AppResponse sendMail(final MailInfo mailInfo) {
//        if (mailSendService == null) {
//            throw new ServiceException("请配置邮件服务").setViewErrMsg("请配置邮件服务");
//        }

        return mailSendService.sendMail(mailInfo);
    }

}
