package com.warehouse.manage.dao;

import com.warehouse.manage.entity.CustomerData;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CustomerMapper {
    /**
     * 添加客户
     * @param code 客户编号
     * @param name 客户名称
     * @param phone 客户电话
     */
    @Insert("INSERT INTO customer(code,name,phone) VALUE(#{code},#{name},#{phone})")
    void addCustomer(@Param("code") String code, @Param("name") String name, @Param("phone") String phone);

    /**
     * 删除客户信息
     * @param id 客户ID
     */
    @Delete("DELETE FROM customer WHERE id=#{id}")
    void deleteCustomer(@Param("id") int id);

    /**
     * 修改客户信息
     * @param code 客户编号
     * @param name 客户名称
     * @param phone 客户电话
     */
    @Update("UPDATE customer SET name=#{name}, phone=#{phone} WHERE code=#{code}")
    void updateCustomer(@Param("code") String code, @Param("name") String name, @Param("phone") String phone);

    /**
     * 通过客户ID修改客户信息
     * @param code 客户编号
     * @param name 客户名称
     * @param phone 客户电话
     * @param id 客户ID
     */
    @Update("UPDATE customer SET name=#{name}, code=#{code}, phone=#{phone} WHERE id=#{id}")
    void updateCustomerById(@Param("code") String code, @Param("name") String name, @Param("phone") String phone, @Param("id") int id);

    /**
     * 根据客户编号搜索客户信息
     * @param code 客户编号
     * @return 客户信息列表
     */
    @SelectProvider(type = CustomerSQL.class, method = "queryCustomer")
    List<CustomerData> queryCustomer(@Param("code") String code);

    /**
     * 根据客户名称搜索客户信息
     * @param name 客户名称
     * @return 客户信息列表
     */
    @SelectProvider(type = CustomerSQL.class, method = "queryCustomerByName")
    List<CustomerData> queryCustomerByName(@Param("name") String name);


    /**
     * 根据客户编号获取客户信息
     * @param code 客户编号
     * @return 客户信息
     */
    @Select("SELECT * FROM customer WHERE code = #{code}")
    CustomerData getCustomerByCode(@Param("code") String code);

    /**
     * 根据客户名称获取客户信息
     * @param name 客户名称
     * @return 客户信息
     */
    @Select("SELECT * FROM customer WHERE name = #{name}")
    CustomerData getCustomerByName(@Param("name") String name);
}
