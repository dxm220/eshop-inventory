package pers.dxm.eshop.inventory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.dxm.eshop.inventory.model.ProductInventory;
import pers.dxm.eshop.inventory.request.ProductInventoryCacheRefreshRequest;
import pers.dxm.eshop.inventory.request.ProductInventoryDBUpdateRequest;
import pers.dxm.eshop.inventory.request.Request;
import pers.dxm.eshop.inventory.service.ProductInventoryService;
import pers.dxm.eshop.inventory.service.RequestAsyncProcessService;
import pers.dxm.eshop.inventory.vo.Response;

import javax.annotation.Resource;

/**
 * 商品库存的controller,包含更新商品库存和获取商品库存的操作
 * Created by douxm on 2018\3\12 0012.
 */
@Controller
public class ProductInventoryController {

    @Resource
    private RequestAsyncProcessService requestAsyncProcessService;//请求分发的service对象

    @Resource
    private ProductInventoryService productInventoryService;//库存操作执行的service

    /**
     * 更新商品库存，将request放入对应消息队列，具体先删缓存后更新数据库的操作封装在request对象中
     */
    @RequestMapping("/updateProductInventory")
    @ResponseBody
    public Response updateProductInventory(ProductInventory productInventory) {
        System.out.println("===========日志===========:接收到更新商品库存的请求，商品id=" + productInventory.getProductId() +
                "商品库存数量=" + productInventory.getInventoryCnt());
        Response response = null;
        try {
            //初始化请求对象
            Request request = new ProductInventoryDBUpdateRequest(productInventory, productInventoryService);
            //将请求执行队列方法操作
            requestAsyncProcessService.process(request);
            //操作成功后返回成功的response
            response = new Response(Response.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            //操作失败后返回失败的response
            response = new Response(Response.FAILURE);
        }
        return response;
    }

    /**
     * 获取商品库存，先在一段时间内读缓存，如果一定时间内读不到，再从数据库读然后更新缓存（这些方法封装在request中）
     */
    @RequestMapping("/getProductInventory")
    @ResponseBody
    public ProductInventory getProductInventory(Integer productId) {
        System.out.println("===========日志===========:接收到读取商品库存的请求，商品id=" + productId);
        ProductInventory productInventory = null;
        try {
            //初始化请求对象
            Request request = new ProductInventoryCacheRefreshRequest(productId, productInventoryService);
            //将request对象放入消息队列中
            requestAsyncProcessService.process(request);
            //将请求扔给service异步去处理以后，就需要while(true)一会儿，在这里hang住
            //去尝试等待前面有商品库存更新的操作，同时不停地进行缓存刷新的操作，将最新的数据刷新到缓存中
            //如果200毫秒都没有刷到最新的缓存，就直接手动模拟执行request的操作，先去db找然后再刷新缓存，最后返回
            //先从缓存中读数据，如果缓存中读不到数据，去数据库中找数据
            long starTime = System.currentTimeMillis();
            long endTime = 0L;
            long waitTime = 0L;
            while (true) {
                if (waitTime > 200) {
                    break;//如果等待时间超过200毫秒，就认为缓存中没有数据，直接去数据库中找
                }
                //尝试去redis中读取缓存数据
                productInventory = productInventoryService.getProductInventoryCache(productId);
                //如果缓存中读到了数据，返回
                if (productInventory != null) {
                    System.out.println("===========日志===========:在200毫秒内读取到了redis中的库存缓存，" +
                            "商品id=" + productInventory.getProductId() + ", 商品库存数量=" + productInventory.getInventoryCnt());
                    return productInventory;
                } else {//如果缓存中没读到，线程等待20毫秒，如果不超过等待时间的上限，继续读缓存
                    Thread.sleep(20);
                    endTime = System.currentTimeMillis();
                    waitTime = endTime - starTime;
                }
            }
            //缓存中没有读到，直接读取数据库
            productInventory = productInventoryService.findProductInventory(productId);
            if (productInventory != null) {
                // 将缓存更新一下
                productInventoryService.setProductInventoryCache(productInventory);
                return productInventory;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //如果上述操作都查不到商品的库存，返回一个库存为-1的商品（实际上返回该对象是一个错误的标志）
        return new ProductInventory(productId, -1L);
    }
}
