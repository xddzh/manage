package com.warehouse.manage.controller;

import com.warehouse.manage.authentication.WebSecurityConfig;
import com.warehouse.manage.entity.AccountInfo;
import com.warehouse.manage.entity.GodownEntry;
import com.warehouse.manage.entity.Product;
import com.warehouse.manage.entity.StockData;
import com.warehouse.manage.service.AccountInfoService;
import com.warehouse.manage.service.WarehouseService;
import com.warehouse.manage.sysEnum.ProductTypeEnum;
import com.warehouse.manage.tools.ResponseData;
import com.warehouse.manage.tools.ResponseList;
import com.warehouse.manage.tools.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
public class WarehouseController {

    @Autowired
    AccountInfoService accountInfoService;
    @Autowired
    WarehouseService warehouseService;

    @RequestMapping("warehousing")
    public String warehousingIndex(Model model, @SessionAttribute(WebSecurityConfig.SESSION_KEY)String account) {
        List<String> productTypes = new ArrayList<>();
        for (ProductTypeEnum typeEnum :ProductTypeEnum.values()){
            productTypes.add(typeEnum.getProductTypeName());
        }
        AccountInfo accountInfo = accountInfoService.loadUserByUsername(account);
        model.addAttribute("userInfo" ,accountInfo);
        model.addAttribute("productTypes" ,productTypes);
        return "warehousing/warehousing";
    }


    @PostMapping("/addProduct")
    @ResponseBody
    public ResponseData addProduct(String productName, String productIcon, String productClass, String supplier, String personName, long time, String productType, float unitPrice, float count, String unit, String remarks, String code, String name){
        String productId = Tool.getCurrentOrderId(3);
        String orderId = Tool.getCurrentOrderId(5);
        Timestamp timeValue = new Timestamp(time);
        Timestamp eidtTime = new Timestamp(new Date().getTime());
        warehouseService.addPrduct(productId,orderId,productName,productIcon,productClass,supplier,personName,timeValue,eidtTime,productType,unitPrice,count,unit,remarks,0,code, name);
        return new ResponseData(1,"添加成功");
    }

    @PostMapping("/eidtProduct")
    @ResponseBody
    public ResponseData eidtProduct(String productId, String productName, String productIcon, String productClass, String supplier,String productType,float unitPrice,String unit,String remarks){
        warehouseService.eidtProduct(productId, productName, productIcon, productClass, supplier, productType, unitPrice, unit, remarks);
        return new ResponseData(1,"编辑成功");
    }

    @PostMapping("/deleteProduct")
    @ResponseBody
    public ResponseData deleteProduct(String productId){
        StockData stockData = warehouseService.queryStock(productId);
        ResponseData responseData;
        if (stockData.getStockCount() == 0){//没有库存了允许删除
            warehouseService.deletProduct(productId);
            responseData = new ResponseData(1,"删除物品成功");
        }else {
            float onValue = warehouseService.queryGodownEntrySum(productId,0);
            float offValue = warehouseService.queryGodownEntrySum(productId,1);
            if (onValue-offValue == stockData.getStockCount()){
                warehouseService.deletProduct(productId);
                responseData = new ResponseData(1,"删除物品成功");
            }else {
                responseData = new ResponseData(0,"物品无法删除，该物品领用与库存不一致");
            }
        }
        return responseData;
    }

    @PostMapping("/addWarehousing")
    @ResponseBody
    public ResponseData addWarehousing(String productId, String productName, String productClass, String supplier, String personName, long time, String productType, float unitPrice, float count, String unit, String remarks, String code, String name){
        String orderId = Tool.getCurrentOrderId(5);
        Date now = new Date();
        //编辑时间
        Timestamp eidtTime = new Timestamp(now.getTime());
        Timestamp timeValue = new Timestamp(time);
        warehouseService.addWarehouseinge(productId,orderId,productName,productClass,supplier,personName,timeValue,eidtTime,productType,unitPrice,count,unit,remarks,0,"", code, name);
        return new ResponseData(1,"入库成功");
    }

    @PostMapping("/outWarehousing")
    @ResponseBody
    public ResponseData outWarehousing(String productId, String productName, String productClass, String supplier, String personName, long time, String productType, float unitPrice, float count, String unit, String remarks,String receiver, String code, String name){
        String orderId = Tool.getCurrentOrderId(5);
        Date now = new Date();
        //编辑时间
        Timestamp eidtTime = new Timestamp(now.getTime());
        Timestamp timeValue = new Timestamp(time);
        return warehouseService.addWarehouseinge(productId,orderId,productName,productClass,supplier,personName,timeValue,eidtTime,productType,unitPrice,count,unit,remarks,1,receiver, code, name);
    }


    @PostMapping("/updateWarehouseing")
    @ResponseBody
    public ResponseData updateWarehouseing(String productId, String orderId, String personName, long time, String receiver, float count, String wareRemarks, String code, String name){
        Date now = new Date();
        //编辑时间
        Timestamp eidtTime = new Timestamp(now.getTime());
        Timestamp timeValue = new Timestamp(time);
        return  warehouseService.updateWarehouseing(productId,orderId,personName,timeValue,eidtTime,receiver,count,wareRemarks,code,name);
    }


    @PostMapping("/deleteWarehouseing")
    @ResponseBody
    public ResponseData deleteWarehouseing(String productId, String orderId){
        return  warehouseService.deleteWarehouseing(productId,orderId);
    }


    /**
     * 通过入库单号获取库存量
     * @param orderId 入库单号
     * @return 库存量
     */
    @PostMapping("/queryStock")
    @ResponseBody
    public StockData queryStock(String orderId){
        return warehouseService.queryStock(orderId);
    }




    /**
     * 分页获取物品报表
     * @param page 页数
     * @param rows 每页容量
     * @param productClass 类型
     * @param productName 物品名称
     * @return 入库单
     */
    @GetMapping("/productList")
    @ResponseBody
    public ResponseList<List<Product>> getProductList(int page, int rows, String productClass, String productName){
        return warehouseService.getProductList(page, rows,  productClass, productName);
    }


    /**
     * 通过单号获取入库单报表
     * @param page 页数
     * @param rows 每页容量
     * @param orderId 单号
     * @return 入库单报表
     */
    @GetMapping("/godownEntryListByOrderId")
    @ResponseBody
    public ResponseList<List<GodownEntry>> getGodownEntryListByOrderId(int page, int rows, String orderId,String productId,int orderType){
        return warehouseService.getGodownEntryListByOrderId(page, rows, orderId, productId,orderType);
    }


    /**
     * 通过单号获取入库单报表
     * @param page 页数
     * @param rows 每页容量
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param orderType 0:入库，1:出库
     * @param productName 产品名
     * @return 入库单报表
     */
    @GetMapping("/getGodownEntryListByProductName")
    @ResponseBody
    public ResponseList<List<GodownEntry>>getGodownEntryListByProductName(int page, int rows, long startTime, long endTime, int orderType, String productName, String code){
        return warehouseService.getGodownEntryListByProductName(page, rows, startTime==0?null:new Timestamp(startTime), endTime==0?null:new Timestamp(endTime), orderType, productName, code);
    }


    @RequestMapping("placing")
    public String placingIndex(Model model,@SessionAttribute(WebSecurityConfig.SESSION_KEY)String account) {
        List<String> productTypes = new ArrayList<>();
        for (ProductTypeEnum typeEnum :ProductTypeEnum.values()){
            productTypes.add(typeEnum.getProductTypeName());
        }
        AccountInfo accountInfo = accountInfoService.loadUserByUsername(account);
        model.addAttribute("userInfo" ,accountInfo);
        model.addAttribute("productTypes" ,productTypes);
        return "placing/placing";
    }
}
