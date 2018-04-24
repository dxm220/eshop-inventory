package pers.dxm.eshop.inventory.request;

import pers.dxm.eshop.inventory.model.ProductInventory;
import pers.dxm.eshop.inventory.service.ProductInventoryService;

/**
 * 请求缓存时，如果缓存不存在，先请求数据库，再更新缓存
 * Created by douxm on 2018\3\9 0009.
 */
public class ProductInventoryCacheRefreshRequest implements Request {
    private Integer productId;//商品库存对象Id

    private ProductInventoryService productInventoryService;//库存操作的service对象

    public ProductInventoryCacheRefreshRequest(Integer productId, ProductInventoryService productInventoryService) {
        this.productId = productId;
        this.productInventoryService = productInventoryService;
    }

    @Override
    public void process() {
        //先调用查找数据库的方法
        ProductInventory newProductInventory = productInventoryService.findProductInventory(productId);
        System.out.println("===========日志===========:已查询到商品最新的库存数量，商品id=" + productId + ", " +
                "商品库存数量=" + newProductInventory.getInventoryCnt());
        //再调用更新缓存的方法
        productInventoryService.setProductInventoryCache(newProductInventory);
    }

    @Override
    public Integer getProductId() {
        return productId;
    }
}
