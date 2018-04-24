package pers.dxm.eshop.inventory.service.impl;

import org.springframework.stereotype.Service;
import pers.dxm.eshop.inventory.dao.RedisDao;
import pers.dxm.eshop.inventory.mapper.ProductInventoryMapper;
import pers.dxm.eshop.inventory.model.ProductInventory;
import pers.dxm.eshop.inventory.service.ProductInventoryService;

import javax.annotation.Resource;

/**
 * 库存操作service实现类
 * Created by douxm on 2018\3\9 0009.
 */
@Service("productInventoryService")
public class ProductInventoryServiceImpl implements ProductInventoryService {
    @Resource
    private ProductInventoryMapper productInventoryMapper;
    @Resource
    private RedisDao redisDao;

    @Override
    public void removeProductInventoryCache(ProductInventory productInventory) {
        String key = "product:inventory:" + productInventory.getProductId();
        redisDao.delete(key);
        System.out.println("===========日志===========:已删除redis中的缓存，key=" + key);
    }

    @Override
    public void updateProductInventory(ProductInventory productInventory) {
        productInventoryMapper.updateProductInventory(productInventory);
        System.out.println("===========日志===========:已修改数据库中的库存，商品id=" + productInventory.getProductId() + ", 商品库存数量=" + productInventory.getInventoryCnt());
    }

    @Override
    public ProductInventory findProductInventory(Integer productId) {
        return productInventoryMapper.findProductInventory(productId);
    }

    @Override
    public void setProductInventoryCache(ProductInventory productInventory) {
        String key = "product:inventory:" + productInventory.getProductId();
        redisDao.set(key, String.valueOf(productInventory.getInventoryCnt()));
        System.out.println("===========日志===========:已更新商品库存的缓存，商品id=" + productInventory.getProductId() + ", 商品库存数量=" + productInventory.getInventoryCnt() + ", key=" + key);
    }

    @Override
    public ProductInventory getProductInventoryCache(Integer productId) {
        Long inventoryCnt = 0L;
        String key = "product:inventory:" + productId;
        String value = redisDao.get(key);
        if (value != null && !"".equals(value)) {
            inventoryCnt = Long.valueOf(value);
            return new ProductInventory(productId, inventoryCnt);
        }
        return null;
    }
}
