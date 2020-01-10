package com.neusiri.husky.core.extension.log.event;

import org.springframework.context.ApplicationEvent;

/**
 * <p>日志持久化事件</p>
 * <p>创建日期：2019-09-05</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
public class LogPersistEvent extends ApplicationEvent {

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public LogPersistEvent(Object source) {
        super(source);
    }
}
