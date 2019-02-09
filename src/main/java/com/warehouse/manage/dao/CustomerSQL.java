package com.warehouse.manage.dao;

import org.apache.ibatis.annotations.Param;

public class CustomerSQL {

    public String queryCustomer(@Param("code") String code){
        String sql = "SELECT * FROM customer";
        if (code != null && code.length() > 0){
            sql += " WHERE code = '"+code +"'";
        }
        return sql;
    }

    public String queryCustomerByName(@Param("name") String name){
        String sql = "SELECT * FROM customer";
        if (name != null && name.length() > 0){
            sql += " WHERE name LIKE '%"+name +"%'";
        }
        return sql;
    }


}
