package com.warehouse.manage.dao;

import com.warehouse.manage.entity.AccountRole;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public class AccountSQL {

    public String insertAccountRole(@Param("userId") int userId, List<AccountRole> roles){
        String sql = "INSERT INTO accountRole(roleId,userId,roleName)";

        List<String> values = new ArrayList<>();
        for (AccountRole role:
             roles) {
            values.add("("+role.getRoleId()+","+userId+","+"'"+role.getRoleName()+"'"+")");
        }
        if (values.size()>0){
            sql += " VALUES "+ String.join(",", values) + ";";
        }
        return sql;
    }

    public String queryUserInfo(@Param("name") String name){
        return "SELECT * FROM account JOIN jobtype WHERE account.name LIKE '%" + name + "%' AND account.job=jobtype.id AND account.username!='admin' ORDER BY account.id DESC";
    }

    public String updateAccountInfo(@Param("id") int id, @Param("name") String name,@Param("username") String username, @Param("password") String password,@Param("job") String job,@Param("isEnable") int isEnable){
        String sql = "UPDATE account SET ";
        List<String> strings = new ArrayList<>();
        if (name != null && name.length()>0){
            strings.add("name='"+name+"'");
        }
        if (username != null && username.length()>0){
            strings.add("username='"+username+"'");
        }
        if (password != null && password.length()>0){
            strings.add("password="+password);
        }
        if (job != null && job.length()>0){
            strings.add("job="+job);
        }
        strings.add("isEnable="+isEnable);

        sql +=String.join(",", strings);

        sql += " WHERE id="+id;

        return sql;
    }
}
