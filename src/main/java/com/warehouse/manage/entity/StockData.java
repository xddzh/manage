package com.warehouse.manage.entity;

public class StockData {
    private String productId;//产品单号
    private float stockCount;//库存数量

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public float getStockCount() {
        return stockCount;
    }

    public void setStockCount(float stockCount) {
        this.stockCount = stockCount;
    }

}
