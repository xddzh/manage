package com.warehouse.manage.entity;

import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.sql.Date;
import java.sql.Timestamp;

public class GodownEntry {
    private String orderId;//库存单号
    private String productId;//产品单号
    private String productName; //产品名称
    private String productClass; //1:成品，2：耗材，3：半成品
    private String supplier; //供应商
    private String personName; //入库人员名称
    private String receiver;//领用人
    private Timestamp time;//入库时间
    private Timestamp eidtTime;//编辑时间
    private String productType;//产品类型型号
    private float unitPrice;//单价
    private float count;//数量
    private String unit;//单位
    private String wareRemarks;//备注
    private int orderType;//0:入库，1:出库
    private String code;//客户编号
    private String name;//客户名称

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Timestamp getEidtTime() {
        return eidtTime;
    }

    public void setEidtTime(Timestamp eidtTime) {
        this.eidtTime = eidtTime;
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

    public float getCount() {
        return count;
    }

    public void setCount(float count) {
        this.count = count;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getWareRemarks() {
        return wareRemarks;
    }

    public void setWareRemarks(String wareRemarks) {
        this.wareRemarks = wareRemarks;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
