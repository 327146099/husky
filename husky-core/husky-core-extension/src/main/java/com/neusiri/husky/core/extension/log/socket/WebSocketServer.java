package com.neusiri.husky.core.extension.log.socket;

import com.neusiri.husky.core.util.collection.RingBuffer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>日志输出SocketServer</p>
 * <p>创建日期：2019-09-12</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@ServerEndpoint("/websocket/log")
@Component
@Slf4j
public class WebSocketServer {

    /**
     * 日志信息缓存
     */
    private static RingBuffer<String> buffer = new RingBuffer<>(100);
    /**
     * 静态变量，用来记录当前在线连接数。
     */
    private static AtomicInteger onlineCount = new AtomicInteger(0);

    /**
     * 用来存放每个客户端对应的MyWebSocket对象。
     */
    private static ConcurrentHashMap<String, Session> webSocketMap = new ConcurrentHashMap<>(16);


    public static boolean hasSocketClient() {
        return !webSocketMap.isEmpty();
    }

    /**
     * 清空消息缓冲区
     */
    public static void clearBuffer() {
        buffer.clear();
    }

    /**
     * 获取消息
     *
     * @return 消息
     * @throws InterruptedException 线程终端异常
     */
    public static String getMessage() throws InterruptedException {
        return buffer.poll();
    }

    public static Collection<Session> getSessions() {
        return webSocketMap.values();
    }

    @OnOpen
    public void onOpen(Session session) {
        String id = session.getId();
        webSocketMap.put(id, session);
        log.info("有新连接加入,ip:{}！当前在线人数为:{}", session.getRequestURI(), onlineCount.addAndGet(1));

    }

    @OnClose
    public void onClose(Session session) {
        String id = session.getId();
        webSocketMap.remove(id);
        log.info("websocket关闭，IP：{},当前在线人数为:{}", session.getRequestURI(), onlineCount.addAndGet(-1));
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info(message);
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("socket发生错误", error);
    }

    public static void sendMessage(String msg) {
        if (webSocketMap.isEmpty()) {
            return;
        }
        buffer.add(msg);
    }

}
