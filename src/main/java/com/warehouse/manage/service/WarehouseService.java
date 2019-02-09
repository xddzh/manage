package com.warehouse.manage.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.warehouse.manage.dao.WarehouseMapper;
import com.warehouse.manage.entity.AccountInfo;
import com.warehouse.manage.entity.GodownEntry;
import com.warehouse.manage.entity.Product;
import com.warehouse.manage.entity.StockData;
import com.warehouse.manage.tools.ResponseData;
import com.warehouse.manage.tools.ResponseList;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class WarehouseService {
    @Autowired
    WarehouseMapper warehouseMapper;


    /**
     * 添加物品种类
     * @param productId 物品单号
     * @param productName 产品名称
     * @param productIcon 产品图片
     * @param productClass 1:成品，2：耗材，3：半成品
     * @param supplier 供应商
     * @param productType 产品类型型号
     * @param unitPrice 单价
     * @param unit 单位
     * @param remarks 备注
     */
    public void addPrduct(String productId ,String orderId, String productName, String productIcon, String productClass, String supplier, String personName, Timestamp time, Timestamp eidtTime, String productType, float unitPrice, float count, String unit, String remarks, int orderType, String code, String name){
        warehouseMapper.addProduct(productId,productName,productIcon,productClass,supplier,productType,unitPrice,unit,remarks);
        addWarehouseinge(productId,orderId,productName,productClass,supplier,personName,time,eidtTime,productType,unitPrice,count,unit,"第一次添加",orderType,"",code, name);
    }

    /**
     * 编辑物品种类
     * @param productId 物品单号
     * @param productName 产品名称
     * @param productIcon 产品图片
     * @param productClass 1:成品，2：耗材，3：半成品
     * @param supplier 供应商
     * @param productType 产品类型型号
     * @param unitPrice 单价
     * @param unit 单位
     * @param remarks 备注
     */
    public void eidtProduct(String productId, String productName, String productIcon, String productClass, String supplier,String productType,float unitPrice,String unit,String remarks) {
        warehouseMapper.batchUpdateWarehouseing(productId, productName, productClass, supplier, productType, unitPrice, unit);
        warehouseMapper.updateProduct(productId, productName, productIcon, productClass, supplier, productType, unitPrice, unit, remarks);
    }


    /**
     * 删除物品
     * @param productId 物品号
     */
    public void deletProduct(String productId){
        warehouseMapper.deleteWarehouseingByProductId(productId);
        warehouseMapper.deleteStock(productId);
        warehouseMapper.deleteProduct(productId);
    }


        /**
         * 获取物品报表
         * @param page 页数
         * @param rows 每页容量
         * @param productClass 类型
         * @param productName 物品名称
         * @return 入库单报表
         */
    public ResponseList<List<Product>>getProductList(int page, int rows, String productClass, String productName){
        Page<List<Product>> pager = PageHelper.startPage(page, rows);
        List<Product> bookInfoList = warehouseMapper.getProductList(productClass,productName);
        PageInfo<List<Product>> info = new PageInfo<List<Product>>(pager.getResult());
        return new ResponseList<List<Product>>(info.getTotal(),info.getPages()>=page?bookInfoList:new ArrayList<>());
    }

    /**
     * 添加入库单
     * @param productId 产品单号
     * @param orderId 单号
     * @param productName 产品名称
     * @param productClass 1:成品，2：耗材，3：半成品
     * @param supplier 供应商
     * @param personName 入库人员名称
     * @param time 入库时间
     * @param eidtTime 编辑时间
     * @param productType 产品类型型号
     * @param unitPrice 单价
     * @param count 数量
     * @param unit 单位
     * @param wareRemarks 备注
     * @param orderType 0:入库，1:出库
     * @param receiver 领用人
     */
    public ResponseData addWarehouseinge(String productId ,String orderId, String productName, String productClass, String supplier, String personName, Timestamp time, Timestamp eidtTime, String productType, float unitPrice, float count, String unit, String wareRemarks, int orderType,String receiver, String code, String name){
        ResponseData responseData;
        warehouseMapper.addWarehouseinge(productId,orderId, productName, productClass, supplier, personName, time, eidtTime, productType, unitPrice, count, unit, wareRemarks,orderType,receiver,code,name);
        StockData stockData = warehouseMapper.queryStock(productId);
        if (stockData == null){
            warehouseMapper.addStock(productId,count);
            responseData = new ResponseData(1,"操作成功");
        }else {
            if (orderType==1 ){
                if (stockData.getStockCount()< count){
                    responseData = new ResponseData(1,"库存不够");
                }else {
                    warehouseMapper.updateStock(productId,stockData.getStockCount()-count);
                    responseData = new ResponseData(1,"操作成功");
                }

            }else {
                warehouseMapper.updateStock(productId,stockData.getStockCount()+count);
                responseData = new ResponseData(1,"操作成功");
            }

        }
        return responseData;
    }



    /**
     * 获取出入库单的数量总和
     * @param productId  库存ID
     * @param orderType 0:入库，1:出库
     * @return 数量总和
     */
    public float queryGodownEntrySum(String productId, int orderType){
        return warehouseMapper.queryGodownEntrySum(productId, orderType);
    }

    /**
     * 通过单号获取入库单报表
     * @param page 页数
     * @param rows 每页容量
     * @param orderId 单号
     * @return 入库单报表
     */
    public ResponseList<List<GodownEntry>>getGodownEntryListByOrderId(int page, int rows,String orderId, String productId,int orderType){
        Page<List<GodownEntry>> pager = PageHelper.startPage(page, rows);
        List<GodownEntry> bookInfoList = warehouseMapper.getGodownEntryListByOrderId(orderId,productId,orderType);
        PageInfo<List<GodownEntry>> info = new PageInfo<List<GodownEntry>>(pager.getResult());
        return new ResponseList<List<GodownEntry>>(info.getTotal(),info.getPages()>=page?bookInfoList:new ArrayList<>());
    }

    /**
     * 通过物品名查找出入库单
     * @param page 页数
     * @param rows 没有容量
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param orderType 0:入库，1:出库
     * @param productName 产品名
     * @param code 客户编号
     * @return 出入库单列表
     */
    public ResponseList<List<GodownEntry>>getGodownEntryListByProductName(int page, int rows, Timestamp startTime, Timestamp endTime, int orderType, String productName, String code){
        Page<List<GodownEntry>> pager = PageHelper.startPage(page, rows);
        List<GodownEntry> bookInfoList = warehouseMapper.getGodownEntryListByProductName(startTime, endTime, orderType, productName,code);
        PageInfo<List<GodownEntry>> info = new PageInfo<List<GodownEntry>>(pager.getResult());
        return new ResponseList<List<GodownEntry>>(info.getTotal(),info.getPages()>=page?bookInfoList:new ArrayList<>());
    }

    /**
     * 出入库单修改
     * @param orderId 出入库单ID
     * @param personName 操作人
     * @param time 时间
     * @param eidtTime 编辑时间
     * @param receiver 领用人
     * @param count 数量
     * @param wareRemarks 备注
     */
    public ResponseData updateWarehouseing(String productId, String orderId, String personName, Timestamp time, Timestamp eidtTime, String receiver, float count, String wareRemarks, String code, String name){
        ResponseData responseData;
        GodownEntry godownEntry = warehouseMapper.queryGodownEntry(orderId);
        if (godownEntry == null){
            responseData = new ResponseData(0,"没有改库存单");
        } else {
            StockData stockData = warehouseMapper.queryStock(productId);
            float differenceValue = godownEntry.getCount()-count;
            if (godownEntry.getOrderType() == 0){//入库单
                if (stockData.getStockCount() >= differenceValue){
                    warehouseMapper.updateWarehouseing(orderId, personName, time, eidtTime, receiver, count, wareRemarks, code, name);
                    warehouseMapper.updateStock(productId,stockData.getStockCount()-differenceValue);
                    responseData = new ResponseData(1,"操作成功");
                } else {
                    responseData = new ResponseData(0,"库存不够了");
                }

            } else {//出库单
                if (stockData.getStockCount()+differenceValue >= 0){
                    warehouseMapper.updateWarehouseing(orderId, personName, time, eidtTime, receiver, count, wareRemarks, code, name);
                    warehouseMapper.updateStock(productId, stockData.getStockCount()+differenceValue);
                    responseData = new ResponseData(1,"操作成功");
                }else {
                    responseData = new ResponseData(0,"库存不够了");
                }
            }
        }
        return responseData;
    }


    /**
     * 删除出入库单
     * @param productId 产品ID
     * @param orderId 订单ID
     * @return 结果
     */
    public ResponseData deleteWarehouseing(String productId, String orderId){
        ResponseData responseData;
        GodownEntry godownEntry = warehouseMapper.queryGodownEntry(orderId);
        StockData stockData = warehouseMapper.queryStock(productId);
        if (godownEntry.getOrderType() == 0){//入库单
            if (stockData.getStockCount() >= godownEntry.getCount()){//有库存
                warehouseMapper.updateStock(productId,stockData.getStockCount()-godownEntry.getCount());
                warehouseMapper.deleteWarehouseing(orderId);
                responseData = new ResponseData(1,"操作成功");
            }else {//没有
                responseData = new ResponseData(0,"库存不够了");
            }
        } else {
            warehouseMapper.updateStock(productId,stockData.getStockCount()+godownEntry.getCount());
            warehouseMapper.deleteWarehouseing(orderId);
            responseData = new ResponseData(1,"操作成功");
        }
        return responseData;
    }


    /**
     * 删除入库单
     * @param orderId 库单号
     */
    public void deleteWarehouseing(String orderId){
        warehouseMapper.deleteWarehouseing(orderId);
        warehouseMapper.deleteStock(orderId);
    }

    /**
     * 通过入库单号获取库存量
     * @param productId 物品单号
     * @return 库存量
     */
    public StockData queryStock(String productId){
        return warehouseMapper.queryStock(productId);
    }

    /**
     * 通过库存ID获取入库单
     * @param orderId 库存ID
     * @return 入库单
     */
    public GodownEntry queryGodownEntry(String orderId){
        return warehouseMapper.queryGodownEntry(orderId);
    }
}
