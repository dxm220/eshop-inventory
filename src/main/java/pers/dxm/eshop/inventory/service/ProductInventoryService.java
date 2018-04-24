package pers.dxm.eshop.inventory.service;

import pers.dxm.eshop.inventory.model.ProductInventory;

/**
 * 库存操作service接口
 * Created by douxm on 2018\3\9 0009.
 */
public interface ProductInventoryService {

    //删除redis中的缓存数据
    void removeProductInventoryCache(ProductInventory productInventory);

    //更新数据库中的数据
    void updateProductInventory(ProductInventory productInventory);

    //寻找数据库中的信息
    ProductInventory findProductInventory(Integer productId);

    //更新redis缓存中的数据
    void setProductInventoryCache(ProductInventory productInventory);

    //从redis中获取缓存数据
    ProductInventory getProductInventoryCache(Integer productId);
}
