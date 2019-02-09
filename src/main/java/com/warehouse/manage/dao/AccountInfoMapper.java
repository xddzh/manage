package com.warehouse.manage.dao;


import com.warehouse.manage.entity.AccountInfo;
import com.warehouse.manage.entity.AccountRole;
import com.warehouse.manage.entity.JobInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AccountInfoMapper {

    @Select("SELECT * FROM account WHERE username = #{username}")
    AccountInfo queryAccountInfoByUserName(@Param("username") String username);

    @Select("SELECT * FROM account WHERE id = #{id}")
    AccountInfo queryAccountInfoByUserId(@Param("id") int id);


    @SelectProvider(type = AccountSQL.class, method = "queryUserInfo")
    List<AccountInfo>getAccountInfoList(@Param("name") String name);

    /**
     * 注册用户
     * @param name 姓名
     * @param username 用户名
     * @param password 密码
     * @param job 职位
     * @param isEnable 是否可用
     */
    @Insert("INSERT INTO account(name,username,password,job,isEnable) VALUE(#{name},#{username},#{password},#{job},#{isEnable})")
    void addAccountInfo(@Param("name") String name,@Param("username") String username, @Param("password") String password,@Param("job") String job,@Param("isEnable") int isEnable);

    /**
     * 删除用户
     * @param id 用户id
     */
    @Delete("DELETE FROM account WHERE id=#{id}")
    void deleteAccountByUserId(@Param("id") int id);

    /**
     * 更新用户信息
     * @param id 用户id
     * @param name 用户姓名
     * @param username 用户名字
     * @param password 密码
     * @param job 工作
     * @param isEnable 是否可用
     */
    @SelectProvider(type = AccountSQL.class, method = "updateAccountInfo")
    void updateAccountInfo(@Param("id") int id, @Param("name") String name,@Param("username") String username, @Param("password") String password,@Param("job") String job,@Param("isEnable") int isEnable);

    /**
     * 批量添加权限
     * @param userId 用户id
     * @param roles 权限id
     */
    @SelectProvider(type = AccountSQL.class, method = "insertAccountRole")
    void insertAccountRole(@Param("userId") int userId,List<AccountRole> roles);

    /**
     * 清除用户的权限
     * @param userId 用户id
     */
    @Delete("DELETE FROM accountRole WHERE userId=#{userId}")
    void deleteAccountRole(@Param("userId") int userId);

    /**
     * 根据用户Id获取他的权限
     * @param userId 用户Id
     * @return 用户的权限
     */
    @Select("SELECT * FROM accountRole WHERE userId = #{userId}")
    List<AccountRole>queryRoleByUserId(@Param("userId") int userId);

    /**
     * 获取所有的职位列表
     * @return 职位列表
     */
    @Select("SELECT * FROM jobtype")
    List<JobInfo>getAllJob();

    /**
     * 根据权限获取用户列表
     * @param roleId 权限ID
     * @return 用户列表
     */
    @Select("SELECT * FROM accountRole JOIN account WHERE accountRole.roleId=#{roleId} AND accountRole.userId=account.id")
    List<AccountInfo>getUserListByRole(@Param("roleId") int roleId);
}
