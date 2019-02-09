package com.warehouse.manage.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.warehouse.manage.dao.AccountInfoMapper;
import com.warehouse.manage.dao.CustomerMapper;
import com.warehouse.manage.entity.CustomerData;
import com.warehouse.manage.tools.ResponseData;
import com.warehouse.manage.tools.ResponseList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerMapper customerMapper;


    /**
     * 添加客户信息
     * @param code 客户编号
     * @param name 客户名称
     * @param phone 客户电话
     * @return 结果信息
     */
    public ResponseData addCustomerInfo(String code, String name, String phone){
        ResponseData responseData;
        CustomerData customerData = customerMapper.getCustomerByCode(code);
        if (customerData != null){
            responseData = new ResponseData(0,"该客户编号已经存在");
        } else {
            customerData = customerMapper.getCustomerByName(name);
            if (customerData != null){
                responseData = new ResponseData(0,"该客户名称已经存在");
            } else {
                customerMapper.addCustomer(code, name,phone);
                responseData = new ResponseData(1,"添加客户成功");
            }
        }
        return responseData;
    }

    /**
     * 通过客户ID修改客户信息
     * @param code 客户编号
     * @param name 客户名称
     * @param phone 客户电话
     * @param id 客户ID
     */
    public ResponseData updateCustomerById(String code,String name, String phone,int id){
        ResponseData responseData;
        CustomerData customerData = customerMapper.getCustomerByCode(code);

        if (customerData != null && customerData.getId() != id){
            responseData = new ResponseData(0,"该客户编号已经存在");
        } else {
            customerData = customerMapper.getCustomerByName(name);
            if (customerData != null && customerData.getId() != id){
                responseData = new ResponseData(0,"该客户名称已经存在");
            } else {
                customerMapper.updateCustomerById(code, name, phone, id);
                responseData = new ResponseData(1,"修改成功");
            }
        }
        return responseData;
    }

    /**
     * 通过客户ID删除客户信息
     * @param id 客户ID
     */
    public void deleteCustomerById(int id){
        customerMapper.deleteCustomer(id);
    }


    /**
     * 根据客户名称分页搜索客户信息
     * @param page 页数
     * @param rows 每页容量
     * @param name 客户名称
     * @return 客户信息列表
     */
    public ResponseList<List<CustomerData>> queryCustomersByName(int page, int rows, String name){
        Page<List<CustomerData>> pager = PageHelper.startPage(page, rows);
        List<CustomerData> bookInfoList = customerMapper.queryCustomerByName(name);
        PageInfo<List<CustomerData>> info = new PageInfo<List<CustomerData>>(pager.getResult());
        return new ResponseList<List<CustomerData>>(info.getTotal(),info.getPages()>=page?bookInfoList:new ArrayList<>());
    }

    /**
     * 根据name获取所有的客户信息
     * @param name 客户名称
     * @return 客户信息列表
     */
    public List<CustomerData> queryAllCustomers(String name){
        return customerMapper.queryCustomerByName(name);
    }


}
