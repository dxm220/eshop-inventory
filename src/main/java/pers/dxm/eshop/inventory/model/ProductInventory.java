package pers.dxm.eshop.inventory.model;

/**
 * 商品库存实体类
 * Created by douxm on 2018\3\9 0009.
 */
public class ProductInventory {

    private int productId;//商品ID
    private long inventoryCnt;//库存数量

    public ProductInventory() {

    }

    public ProductInventory(Integer productId, Long inventoryCnt) {
        this.productId = productId;
        this.inventoryCnt = inventoryCnt;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public long getInventoryCnt() {
        return inventoryCnt;
    }

    public void setInventoryCnt(long inventoryCnt) {
        this.inventoryCnt = inventoryCnt;
    }
}
