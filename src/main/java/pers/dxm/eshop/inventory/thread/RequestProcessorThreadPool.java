package pers.dxm.eshop.inventory.thread;

import pers.dxm.eshop.inventory.request.Request;
import pers.dxm.eshop.inventory.request.RequestQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 监听内存队列线程的线程池
 * Created by douxm on 2018\3\9 0009.
 */
public class RequestProcessorThreadPool {

    /**
     * 向线程池中添加线程，并初始化每个线程监听的内存队列
     * 正规情况下，线程池的大小以及内存队列的大小都应该写在配置文件里，这里我们为了方便直接在初始化时指定大小
     */
    private ExecutorService threadPool = Executors.newFixedThreadPool(10);//线程池的大小

    /**
     * 构造方法中将线程池，线程，内存队列三者的关系梳理清楚
     */
    public RequestProcessorThreadPool() {
        RequestQueue requestQueue = RequestQueue.getInstance();//初始化内存队列
        for (int i = 0; i < 10; i++) {//同样将每个内存队列中存放的请求数写死为10
            ArrayBlockingQueue<Request> queue = new ArrayBlockingQueue<Request>(100);
            requestQueue.add(queue);
            threadPool.submit(new RequestProcessorThread(queue));
        }
    }


    public static void init() {
        getInstance();
    }

    /**
     * 获取线程池对象
     */
    public static RequestProcessorThreadPool getInstance() {
        return Singleton.getInstance();
    }

    /**
     * 使用一种绝对线程安全的方式：private静态内部类；内部类内部的静态代码块初始化线程池(最安全的单例模式)
     * 保证系统中只有一个线程池被初始化
     */
    private static class Singleton {
        private static RequestProcessorThreadPool instance;

        static {
            instance = new RequestProcessorThreadPool();
        }

        public static RequestProcessorThreadPool getInstance() {
            return instance;
        }
    }
}
