package pers.dxm.eshop.inventory.service;

import pers.dxm.eshop.inventory.request.Request;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by douxm on 2018\3\12 0012.
 */
public interface RequestAsyncProcessService {

    //将请求放入指定消息队列
    void process(Request request);

}
