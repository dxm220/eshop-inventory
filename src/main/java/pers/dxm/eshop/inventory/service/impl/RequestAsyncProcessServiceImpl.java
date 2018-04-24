package pers.dxm.eshop.inventory.service.impl;

import org.springframework.stereotype.Service;
import pers.dxm.eshop.inventory.request.Request;
import pers.dxm.eshop.inventory.request.RequestQueue;
import pers.dxm.eshop.inventory.service.RequestAsyncProcessService;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by douxm on 2018\3\12 0012.
 */
@Service("requestAsyncProcessService")
public class RequestAsyncProcessServiceImpl implements RequestAsyncProcessService {
    @Override
    public void process(Request request) {
        try {
            //将请求进行路由计算后返回接收该请求的队列
            ArrayBlockingQueue<Request> queue = getRoutingQueue(request.getProductId());
            //将请求加入队列中
            queue.put(request);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取路由计算后的消息队列
     *
     * @param productId
     * @return
     */
    public ArrayBlockingQueue<Request> getRoutingQueue(Integer productId) {
        RequestQueue requestQueue = RequestQueue.getInstance();
        // 先获取productId的hash值
        String key = String.valueOf(productId);
        int h;
        int hash = (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
        // 对hash值取模，将hash值路由到指定的内存队列中，比如内存队列大小8
        // 用内存队列的数量对hash值取模之后，结果一定是在0~7之间
        // 所以任何一个商品id都会被固定路由到同样的一个内存队列中去的
        int index = (requestQueue.queueSize() - 1) & hash;
        System.out.println("===========日志===========:路由内存队列，商品id=" + productId + ", 队列索引=" + index);
        return requestQueue.getQueue(index);
    }
}
