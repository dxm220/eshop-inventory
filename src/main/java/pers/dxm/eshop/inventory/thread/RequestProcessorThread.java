package pers.dxm.eshop.inventory.thread;

import pers.dxm.eshop.inventory.request.Request;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

/**
 * 执行请求的工作线程
 * Created by douxm on 2018\3\9 0009.
 */
public class RequestProcessorThread implements Callable<Boolean> {

    //线程监控的内存队列
    private ArrayBlockingQueue<Request> queue;

    /**
     * 线程初始化时传入线程监听的消息队列
     * @param queue
     */
    public RequestProcessorThread(ArrayBlockingQueue<Request> queue) {
        this.queue = queue;
    }

    @Override
    public Boolean call() throws Exception {
        try {
            while(true) {
                // ArrayBlockingQueueBlocking就是说明，如果队列满了或者是空的，都会在执行操作的时候阻塞住
                Request request = queue.take();
                System.out.println("===========日志===========:工作线程处理请求，商品id=" + request.getProductId());
                // 执行这个request操作
                request.process();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
