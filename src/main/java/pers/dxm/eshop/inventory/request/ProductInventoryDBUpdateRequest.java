package pers.dxm.eshop.inventory.request;

import pers.dxm.eshop.inventory.model.ProductInventory;
import pers.dxm.eshop.inventory.service.ProductInventoryService;

/**
 * 库存发生变化时，先删除缓存，再更新数据库
 * Created by douxm on 2018\3\9 0009.
 */
public class ProductInventoryDBUpdateRequest implements Request {

    private ProductInventory productInventory;//商品库存对象

    private ProductInventoryService productInventoryService;//库存操作的service对象

    public ProductInventoryDBUpdateRequest(ProductInventory productInventory, ProductInventoryService productInventoryService) {
        this.productInventory = productInventory;
        this.productInventoryService = productInventoryService;
    }

    @Override
    public void process() {
        System.out.println("===========日志===========:数据库更新请求开始执行，" +
                "商品id=" + productInventory.getProductId() + ", 商品库存数量=" + productInventory.getInventoryCnt());
        //先调用删除缓存的方法
        productInventoryService.removeProductInventoryCache(productInventory);
//        为了模拟演示先删除了redis中的缓存，然后还没更新数据库的时候，读请求过来了，这里可以人工sleep一下
//        try {
//            Thread.sleep(20000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        //再调用更新数据库的方法
        productInventoryService.updateProductInventory(productInventory);
    }

    @Override
    public Integer getProductId() {
        return productInventory.getProductId();
    }
}
