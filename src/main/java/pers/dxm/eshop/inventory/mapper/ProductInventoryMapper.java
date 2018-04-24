package pers.dxm.eshop.inventory.mapper;

import org.apache.ibatis.annotations.Param;
import pers.dxm.eshop.inventory.model.ProductInventory;

/**
 * 库存服务的mapper接口
 * Created by douxm on 2018\3\9 0009.
 */
public interface ProductInventoryMapper {

    //更新库存时更新数据库的操作
    public void updateProductInventory(ProductInventory productInventory);

    //查找库存时查询数据库的操作
    public ProductInventory findProductInventory(@Param("productId") Integer productId);
}
