package com.neusiri.husky.core.extension.log.socket;


import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Collection;

/**
 * <p>日志socket输出线程</p>
 * <p>创建日期：2019-09-12</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Slf4j
public class LogWebSocketThread implements Runnable {

    @Override
    public void run() {
        while (true) {
            String message = null;
            try {
                message = WebSocketServer.getMessage();
            } catch (InterruptedException e) {
                log.error("thread interrupted", e);
            }
            Collection<Session> sessions = WebSocketServer.getSessions();
            if (!StringUtils.isEmpty(message)) {
                for (Session session : sessions) {
                    try {
                        session.getBasicRemote().sendText(message);
                    } catch (IOException e) {
                        log.error("日志写入错误", e);
                    }
                }
            }
        }
    }
}
