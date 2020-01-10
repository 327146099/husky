package com.neusiri.husky.core.extension.log.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Layout;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.neusiri.husky.core.extension.log.socket.LogWebSocketThread;
import com.neusiri.husky.core.extension.log.socket.WebSocketServer;
import lombok.Setter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>socket日志输出器</p>
 * <p>创建日期：2019-09-11</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Setter
public class SocketAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    private Layout<ILoggingEvent> layout;

    private ExecutorService executorService = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(), r -> {
        Thread thread = new Thread(r);
        thread.setName("LOG-SOCKET-THREAD");
        thread.setDaemon(true);
        return thread;
    });

    @Override
    protected void append(ILoggingEvent event) {
        if (layout == null || event == null || !isStarted()) {
            return;
        }
        String message = layout.doLayout(event);

        WebSocketServer.sendMessage(message);

    }

    @Override
    public void start() {
        super.start();
        // 启动日志socket输出线程
        executorService.execute(new LogWebSocketThread());
    }


    @Override
    public void stop() {
        super.stop();
        executorService.shutdown();
    }
}
