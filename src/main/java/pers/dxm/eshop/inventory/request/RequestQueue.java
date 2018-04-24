package pers.dxm.eshop.inventory.request;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 请求内存队列
 * Created by douxm on 2018\3\8 0008.
 */
public class RequestQueue {
    //存放内存队列的list列表,内存队列的类型是ArrayBlockingQueue<Request>类型
    private List<ArrayBlockingQueue<Request>> queues = new ArrayList<ArrayBlockingQueue<Request>>();

    /**
     * 向内存队列集合中添加内存队列
     */
    public void add(ArrayBlockingQueue<Request> queue) {
        this.queues.add(queue);
    }

    /**
     * 返回请求队列的数量
     *
     * @return
     */
    public Integer queueSize() {
        return queues.size();
    }

    /**
     * 传入索引，返回某个请求队列
     *
     * @param index
     * @return
     */
    public ArrayBlockingQueue<Request> getQueue(Integer index) {
        return queues.get(index);
    }

    /**
     * 返回RequestQueue实例对象
     */
    public static RequestQueue getInstance() {
        return Singleton.getInstance();
    }

    private static class Singleton {
        private static RequestQueue instance;

        static {
            instance = new RequestQueue();
        }

        public static RequestQueue getInstance() {
            return instance;
        }
    }
}
