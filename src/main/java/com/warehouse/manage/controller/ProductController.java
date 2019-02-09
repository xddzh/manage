package com.warehouse.manage.controller;

import com.warehouse.manage.entity.CustomerData;
import com.warehouse.manage.service.CustomerService;
import com.warehouse.manage.tools.ResponseData;
import com.warehouse.manage.tools.ResponseList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    CustomerService customerService;

    @RequestMapping("product")
    public String warehousingIndex(Model model) {
        return "product/product";
    }


    /**
     * 添加客户信息
     * @param code 客户编号
     * @param name 客户名称
     * @param phone 客户电话
     * @return 结果信息
     */
    @PostMapping("/addCustomer")
    @ResponseBody
    public ResponseData addCustomer(String code, String name, String phone){
        return customerService.addCustomerInfo(code,name,phone);
    }


    /**
     * 通过客户ID修改客户信息
     * @param code 客户编号
     * @param name 客户名称
     * @param phone 客户电话
     * @param id 客户ID
     * @return 结果信息
     */
    @PostMapping("/updateCustomerById")
    @ResponseBody
    public ResponseData updateCustomerById(String code,String name,String phone,int id){
        return customerService.updateCustomerById(code, name, phone, id);
    }


    /**
     * 通过客户ID删除客户信息
     * @param id 客户ID
     * @return 结果信息
     */
    @PostMapping("/deleteCustomerById")
    @ResponseBody
    public ResponseData deleteCustomerById(int id){
        customerService.deleteCustomerById(id);
        return new ResponseData(1,"删除成功");
    }


    /**
     * 根据客户名称分页搜索客户信息
     * @param page 页数
     * @param rows 每页容量
     * @param name 客户名称
     * @return 客户信息列表
     */
    @GetMapping("/queryCustomers")
    @ResponseBody
    public ResponseList<List<CustomerData>> queryCustomersByName(int page, int rows, String name){
        return customerService.queryCustomersByName(page, rows, name);
    }

    /**
     * 根据name获取所有的客户信息
     * @return 客户信息列表
     */
    @GetMapping("/queryAllCustomers")
    @ResponseBody
    public List<CustomerData> queryAllCustomers(){
        return customerService.queryAllCustomers(null);
    }

}
