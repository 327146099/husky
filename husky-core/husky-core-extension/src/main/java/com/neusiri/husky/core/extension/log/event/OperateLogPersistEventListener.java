package com.neusiri.husky.core.extension.log.event;

import com.neusiri.husky.core.extension.log.entity.OperationLog;
import com.neusiri.husky.core.extension.log.service.IOperationLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * <p>操作日志持久化事件</p>
 * <p>创建日期：2019-09-05</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Component
@Slf4j
public class OperateLogPersistEventListener implements ApplicationListener<LogPersistEvent> {

    @Autowired
    private IOperationLogService operationLogService;

    @Async
    @Override
    public void onApplicationEvent(LogPersistEvent event) {
        Object source = event.getSource();
        if (source instanceof OperationLog) {
            OperationLog operationLog = (OperationLog) source;
            // 保存操作日志
            operationLogService.saveOperateLog(operationLog);
        }
    }


}
