package com.warehouse.manage.entity;

import java.sql.Timestamp;

public class Product {
    private String productId;//单号
    private String productName; //产品名称
    private String productIcon; //产品图片
    private String productClass; //1:成品，2：耗材，3：半成品
    private String supplier; //供应商
    private String productType;//产品类型型号
    private float unitPrice;//单价
    private String unit;//单位
    private String remarks;//备注
    private float stockCount;//库存数量

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductIcon() {
        return productIcon;
    }

    public void setProductIcon(String productIcon) {
        this.productIcon = productIcon;
    }

    public String getProductClass() {
        return productClass;
    }

    public void setProductClass(String productClass) {
        this.productClass = productClass;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public float getStockCount() {
        return stockCount;
    }

    public void setStockCount(float stockCount) {
        this.stockCount = stockCount;
    }
}
