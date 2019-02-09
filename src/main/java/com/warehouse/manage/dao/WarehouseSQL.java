package com.warehouse.manage.dao;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class WarehouseSQL {

    public String productList(@Param("productClass") String productClass, @Param("productName") String productName){
        String sql = "SELECT * FROM product JOIN stock";
        List<String> sqlContents = new ArrayList<>();
        if (productClass != null && productClass.length() > 0){
            sqlContents.add("product.productClass = '"+ productClass + "'");
        }
        if (productName != null && productName.length() > 0){
            sqlContents.add("product.productName LIKE '%"+ productName+"%'");
        }

        sqlContents.add("product.productId=stock.productId");


        if (sqlContents.size() > 0){
            sql += " WHERE " + String.join(" AND ",sqlContents );
        }

        sql += " ORDER BY product.id DESC";
        return sql;
    }


    public String getGodownEntryListByProductName(@Param("startTime") Timestamp startTime,@Param("endTime") Timestamp endTime,@Param("orderType") int orderType, @Param("productName") String productName, @Param("code") String code){
        String sql = "SELECT * FROM warehousing ";

        List<String> sqlContents = new ArrayList<>();
        if (startTime != null){
            sqlContents.add("time >= '"+startTime+"'");
        }
        if (endTime != null){
            sqlContents.add("time <= '"+endTime+"'");
        }
        if (orderType == 0 || orderType == 1){
            sqlContents.add("orderType="+orderType);
        }
        if (code != null && code.length() > 0){
            sqlContents.add("code='"+code+"'");
        }
        if (productName != null && productName.length() > 0){
            sqlContents.add("productName LIKE '%"+ productName+"%'");
        }
        if (sqlContents.size() > 0){
            sql += " WHERE " + String.join(" AND ",sqlContents );
        }

        sql += " ORDER BY id DESC";
        return sql;

    }

    public String getGodownEntryListByOrderId(@Param("orderId") String orderId, @Param("productId") String productId, @Param("orderType") int orderType){
        String sql = "SELECT * FROM warehousing ";
        List<String> sqlContents = new ArrayList<>();
        if (orderId != null && orderId.length()>0){
            sqlContents.add("orderId LIKE '%"+orderId+"%'");
        }
        if (productId != null && productId.length()>0){
            sqlContents.add("productId='"+productId+"'");
        }
        if (orderType == 0 || orderType == 1){
            sqlContents.add("orderType="+orderType);
        }

        if (sqlContents.size() > 0){
            sql += " WHERE " + String.join(" AND ",sqlContents );
        }
        sql += " ORDER BY id DESC";
        return sql;
    }
}
