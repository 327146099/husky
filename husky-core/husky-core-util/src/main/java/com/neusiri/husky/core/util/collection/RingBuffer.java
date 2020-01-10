package com.neusiri.husky.core.util.collection;

import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>环形队列,如果队列慢则删除头元素,存放元素不阻塞,获取元素如果为空则阻塞</p>
 * <p>创建日期：2019-09-11</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
public class RingBuffer<T> {

    private CircularFifoQueue<T> queue;

    /**
     * 锁
     */
    private final ReentrantLock lock;

    /**
     * 是否为空信号量
     */
    private final Condition notEmpty;

    /**
     * 构造方法
     *
     * @param size 队列大小
     */
    public RingBuffer(int size) {
        queue = new CircularFifoQueue<>(size);
        lock = new ReentrantLock();
        notEmpty = lock.newCondition();
    }

    /**
     * 添加元素
     *
     * @param element 添加内容
     * @return 是否添加成功
     */
    public boolean add(T element) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            queue.add(element);
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
        return true;
    }

    /**
     * 弹出队首元素
     *
     * @return 队首元素
     * @throws InterruptedException 终端异常
     */
    public T poll() throws InterruptedException {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            while (queue.isEmpty()) {
                notEmpty.await();
            }
            return queue.poll();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 清空全部元素
     */
    public void clear() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            queue.clear();
        } finally {
            lock.unlock();
        }
    }

}
