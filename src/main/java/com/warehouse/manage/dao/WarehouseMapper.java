package com.warehouse.manage.dao;

import com.warehouse.manage.entity.GodownEntry;
import com.warehouse.manage.entity.Product;
import com.warehouse.manage.entity.StockData;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface WarehouseMapper {

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
    @Insert("INSERT INTO product(productId,productName,productIcon,productClass,supplier,productType,unitPrice,unit,remarks) VALUE(#{productId},#{productName},#{productIcon},#{productClass},#{supplier},#{productType},#{unitPrice},#{unit},#{remarks})")
    void addProduct(@Param("productId") String productId, @Param("productName") String productName, @Param("productIcon") String productIcon, @Param("productClass") String productClass, @Param("supplier") String supplier, @Param("productType") String productType, @Param("unitPrice") float unitPrice, @Param("unit") String unit, @Param("remarks") String remarks);

    /**
     * 删除物品
     * @param productId 物品单号
     */
    @Delete("DELETE FROM product WHERE productId=#{productId}")
    void deleteProduct(@Param("productId") String productId);

    @Update("UPDATE product SET productName=#{productName},productIcon=#{productIcon},productClass=#{productClass},supplier=#{supplier},productType=#{productType},unitPrice=#{unitPrice},unit=#{unit},remarks=#{remarks} WHERE productId=#{productId}")
    void updateProduct(@Param("productId") String productId, @Param("productName") String productName, @Param("productIcon") String productIcon, @Param("productClass") String productClass, @Param("supplier") String supplier, @Param("productType") String productType, @Param("unitPrice") float unitPrice, @Param("unit") String unit, @Param("remarks") String remarks);




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
     */
    @Insert("INSERT INTO warehousing(productId,orderId,productName,productClass,supplier,personName,time,eidtTime,productType,unitPrice,count,unit,wareRemarks,orderType,receiver,code,name) VALUE(#{productId},#{orderId},#{productName},#{productClass},#{supplier},#{personName},#{time},#{eidtTime},#{productType},#{unitPrice},#{count},#{unit},#{wareRemarks},#{orderType},#{receiver},#{code},#{name})")
    void addWarehouseinge(@Param("productId") String productId,@Param("orderId") String orderId, @Param("productName") String productName, @Param("productClass") String productClass, @Param("supplier") String supplier, @Param("personName") String personName, @Param("time") Timestamp time, @Param("eidtTime") Timestamp eidtTime, @Param("productType") String productType, @Param("unitPrice") float unitPrice, @Param("count") float count, @Param("unit") String unit, @Param("wareRemarks") String wareRemarks, @Param("orderType") int orderType,@Param("receiver") String receiver, @Param("code") String code, @Param("name") String name);

    /**
     * 批量更新出入库单
     * @param productId 产品单号
     * @param productName 产品名称
     * @param productClass 1:成品，2：耗材，3：半成品
     * @param supplier 供应商
     * @param productType 产品类型型号
     * @param unitPrice 单价
     * @param unit 单位
     */
    @Update("UPDATE warehousing SET productName=#{productName},productClass=#{productClass},supplier=#{supplier},productType=#{productType},unitPrice=#{unitPrice},unit=#{unit} WHERE productId in (#{productId})")
    void batchUpdateWarehouseing(@Param("productId") String productId,@Param("productName") String productName, @Param("productClass") String productClass, @Param("supplier") String supplier,@Param("productType") String productType, @Param("unitPrice") float unitPrice,@Param("unit") String unit);


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
    @Update("UPDATE warehousing SET personName=#{personName},time=#{time},eidtTime=#{eidtTime},receiver=#{receiver},count=#{count},wareRemarks=#{wareRemarks},code=#{code},name=#{name} WHERE orderId=#{orderId}")
    void updateWarehouseing(@Param("orderId") String orderId, @Param("personName") String personName,@Param("time") Timestamp time, @Param("eidtTime") Timestamp eidtTime,@Param("receiver") String receiver,@Param("count") float count, @Param("wareRemarks") String wareRemarks, @Param("code") String code, @Param("name") String name);

    /**
     * 通过库存ID获取入库单
     * @param orderId 库存ID
     * @return 入库单
     */
    @Select("SELECT * FROM warehousing WHERE orderId = #{orderId}")
    GodownEntry queryGodownEntry(@Param("orderId") String orderId);


    @SelectProvider(type = WarehouseSQL.class, method = "getGodownEntryListByOrderId")
    List<GodownEntry>getGodownEntryListByOrderId(@Param("orderId") String orderId,@Param("productId") String productId, @Param("orderType") int orderType);

    /**
     *通过物品名查找出入库单
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param orderType 0:入库，1:出库
     * @param productName 产品名
     * @return 出入库单列表
     */
    @SelectProvider(type = WarehouseSQL.class, method = "getGodownEntryListByProductName")
    List<GodownEntry>getGodownEntryListByProductName(@Param("startTime") Timestamp startTime, @Param("endTime") Timestamp endTime, @Param("orderType") int orderType, @Param("productName") String productName, @Param("code") String code);
    /**
     * 获取出入库单的数量总和
     * @param productId  库存ID
     * @param orderType 0:入库，1:出库
     * @return 数量总和
     */
    @Select("SELECT IFNULL(SUM(count),0) FROM warehousing WHERE productId = #{productId} AND orderType = #{orderType}")
    float queryGodownEntrySum(@Param("productId") String productId, @Param("orderType") int orderType);

    /**
     * 删除入库单
     * @param orderId 库单号
     */
    @Delete("DELETE FROM warehousing WHERE orderId=#{orderId}")
    void deleteWarehouseing(@Param("orderId") String orderId);

    /**
     * 删除入库单
     * @param productId 物品单号
     */
    @Delete("DELETE FROM warehousing WHERE productId=#{productId}")
    void deleteWarehouseingByProductId(@Param("productId") String productId);


    /**
     * 添加库存
     * @param productId 产品单号
     * @param stockCount 库存量
     */
    @Insert("INSERT INTO stock(productId,stockCount) VALUE(#{productId},#{stockCount})")
    void addStock(@Param("productId") String productId,@Param("stockCount") float stockCount);

    /**
     * 删除库存
     * @param productId 产品单号
     */
    @Delete("DELETE FROM stock WHERE productId=#{productId}")
    void deleteStock(@Param("productId") String productId);


    /**
     * 更新库存
     * @param productId 产品单号
     * @param stockCount 库存量
     */
    @Update("UPDATE stock SET stockCount=#{stockCount} WHERE productId=#{productId}")
    void updateStock(@Param("productId") String productId,@Param("stockCount") float stockCount);


    /**
     * 通过入库单号获取库存量
     * @param productId 产品单号
     * @return 库存量
     */
    @Select("SELECT * FROM stock WHERE productId = #{productId}")
    StockData queryStock(@Param("productId") String productId);

    @SelectProvider(type = WarehouseSQL.class, method = "productList")
    List<Product>getProductList(@Param("productClass") String productClass, @Param("productName") String productName);

}
