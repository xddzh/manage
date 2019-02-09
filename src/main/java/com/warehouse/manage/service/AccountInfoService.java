package com.warehouse.manage.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.warehouse.manage.dao.AccountInfoMapper;
import com.warehouse.manage.dao.AccountSQL;
import com.warehouse.manage.entity.AccountInfo;
import com.warehouse.manage.entity.AccountRole;
import com.warehouse.manage.entity.JobInfo;
import com.warehouse.manage.tools.ResponseData;
import com.warehouse.manage.tools.ResponseList;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountInfoService {

    private static Logger logger = LoggerFactory.getLogger(AccountInfoService.class);

    @Autowired
    private AccountInfoMapper accountInfoMapper;

    /**
     * 查询账户信息通过用户名
     * @param username 用户名
     * @return 账户信息
     */
    public AccountInfo queryAccountInfoByUserName(String username){
        return accountInfoMapper.queryAccountInfoByUserName(username);
    }

    /**
     * 查询账户信息通过用户ID
     * @param userId 用户ID
     * @return 账户信息
     */
    public AccountInfo queryAccountInfoByUserId( int userId){
        return accountInfoMapper.queryAccountInfoByUserId(userId);
    }

    /**
     * 分页获取员工信息
     * @param page 页数
     * @param rows 每页容量
     * @return 员工信息列表
     */
    public ResponseList<List<AccountInfo>> getUserList(int page, int rows, String name){
        Page<List<AccountInfo>> pager = PageHelper.startPage(page, rows);
        List<AccountInfo> bookInfoList = accountInfoMapper.getAccountInfoList(name);
        PageInfo<List<AccountInfo>> info = new PageInfo<List<AccountInfo>>(pager.getResult());
        return new ResponseList<List<AccountInfo>>(info.getTotal(),info.getPages()>=page?bookInfoList:new ArrayList<>());
    }

    /**
     * 注册用户
     * @param name 姓名
     * @param username 用户名
     * @param password 密码
     * @param job 职位
     * @param isEnable 是否可用
     */
    public void addAccountInfo(String name, String username, String password,String job, int isEnable){
        accountInfoMapper.addAccountInfo(name,username,password,job,isEnable);
    }

    /**
     * 删除用户
     * @param userId 用户id
     */
    public void deleteAccountByUserId(int userId){
        accountInfoMapper.deleteAccountByUserId(userId);
    }


    /**
     * 更新用户信息
     * @param id 用户id
     * @param name 用户姓名
     * @param username 用户名字
     * @param password 密码
     * @param job 工作
     * @param isEnable 是否可用
     */
    public void updateAccountInfo(int id, String name, String username, String password, String job, int isEnable){
        accountInfoMapper.updateAccountInfo(id, name, username, password, job, isEnable);
    }


    /**
     * 登录时查询用户信息
     * @param username  用户名
     * @return 用户信息
     */
    public AccountInfo loadUserByUsername(String username) {
        AccountInfo accountInfo = queryAccountInfoByUserName(username);
        //查询该用户的全部权限
        if (accountInfo != null){
            List<AccountRole> accountRoles = accountInfoMapper.queryRoleByUserId(accountInfo.getId());
            accountInfo.setAuthorities(accountRoles);
        }
        return accountInfo;
    }

    /**
     * 根据权限获取用户列表
     * @param roleId 权限ID
     * @return 用户列表
     */
    public List<AccountInfo>getUserListByRole(int roleId){
        return accountInfoMapper.getUserListByRole(roleId);
    }

    /**
     * 查询用户权限信息
     * @param userId 用户名ID
     * @return 用户权限信息
     */
    public List<AccountRole> queryRoleByUserId(int userId){
        return accountInfoMapper.queryRoleByUserId(userId);
    }

    /**
     * 获取所有的职位列表
     * @return 职位列表
     */
    public List<JobInfo>getAllJobList(){
        return accountInfoMapper.getAllJob();
    }

    /**
     * 批量添加权限
     * @param userId 用户id
     * @param roles 权限id
     */
    public void addAccountRole(int userId, List<AccountRole> roles){
        accountInfoMapper.insertAccountRole(userId, roles);
    }

    /**
     * 清除用户的权限
     * @param userId 用户id
     */
    public void deleteAccountRole(int userId){
        accountInfoMapper.deleteAccountRole(userId);
    }
}
