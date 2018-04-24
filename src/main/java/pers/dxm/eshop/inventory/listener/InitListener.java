package pers.dxm.eshop.inventory.listener;

import pers.dxm.eshop.inventory.thread.RequestProcessorThreadPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 系统初始化监听器，系统初始化时初始化一个监听内存队列的线程池
 * Created by douxm on 2018\3\9 0009.
 */
public class InitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        RequestProcessorThreadPool.init();
        System.out.println("系统开启");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
