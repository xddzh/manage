package com.warehouse.manage.controller;

import com.alibaba.fastjson.JSONArray;
import com.warehouse.manage.authentication.WebSecurityConfig;
import com.warehouse.manage.entity.AccountInfo;
import com.warehouse.manage.entity.AccountRole;
import com.warehouse.manage.entity.JobInfo;
import com.warehouse.manage.service.AccountInfoService;
import com.warehouse.manage.sysEnum.FunctionEnum;
import com.warehouse.manage.tools.DESHelper;
import com.warehouse.manage.tools.ResponseData;
import com.warehouse.manage.tools.ResponseList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class AuthController {

    @Autowired
    AccountInfoService accountInfoService;

    @GetMapping("/")
    public String index(Model model,
                        @SessionAttribute(WebSecurityConfig.SESSION_KEY)String account) {
        AccountInfo accountInfo = accountInfoService.loadUserByUsername(account);
        model.addAttribute("userInfo" ,accountInfo);
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login/login";
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseData register(String name, String username, String password, String job, String roles, HttpSession session){
        AccountInfo accountInfo = accountInfoService.queryAccountInfoByUserName(username);
        ResponseData responseData;
        if (accountInfo == null){
            List<AccountRole> roleList = JSONArray.parseArray(roles, AccountRole.class);
            accountInfoService.addAccountInfo(name,username,DESHelper.encryptBasedDes(password),job,1);
            accountInfo = accountInfoService.queryAccountInfoByUserName(username);
            accountInfoService.addAccountRole(accountInfo.getId(),roleList);
            responseData = new ResponseData(1,"注册成功");
        }else {
            responseData = new ResponseData(0,"该用户名已经存在");
        }
        return responseData;
    }


    /**
     * 更新用户信息
     * @param userId 用户id
     * @param name 用户姓名
     * @param username 用户名字
     * @param password 密码
     * @param job 工作
     * @param session 认证
     */
    @PostMapping("/updateUserInfo")
    @ResponseBody
    public ResponseData updateUserInfo(int userId, String name, String username, String password, String job, String roles, HttpSession session){
        AccountInfo accountInfo = accountInfoService.queryAccountInfoByUserId(userId);
        ResponseData responseData;
        if (accountInfo == null){
            responseData = new ResponseData(0,"该用户不存在");
        }else {
            List<AccountRole> roleList = JSONArray.parseArray(roles, AccountRole.class);
            if (username.contains(accountInfo.getUsername())){
                accountInfoService.updateAccountInfo(accountInfo.getId(),name,username,password,job,1);
                accountInfoService.deleteAccountRole(accountInfo.getId());
                accountInfoService.addAccountRole(accountInfo.getId(),roleList);
                responseData = new ResponseData(1,"编辑成功");
            } else {
                AccountInfo temp = accountInfoService.queryAccountInfoByUserName(username);
                if(temp == null){
                    accountInfoService.updateAccountInfo(accountInfo.getId(),name,username,password,job,1);
                    accountInfoService.deleteAccountRole(accountInfo.getId());
                    accountInfoService.addAccountRole(accountInfo.getId(),roleList);
                    responseData = new ResponseData(1,"编辑成功");
                }else {
                    responseData = new ResponseData(0,"该用户名已经存在,请更换用户名");
                }
            }
        }
        return responseData;
    }

    /**
     * 删除用户
     * @param userId 用户id
     */
    @PostMapping("/deleteUser")
    @ResponseBody
    public ResponseData deleteUser(int userId){
        AccountInfo accountInfo = accountInfoService.queryAccountInfoByUserId(userId);
        ResponseData responseData;
        if (accountInfo == null){
            responseData = new ResponseData(0,"该用户不存在");
        }else {
            accountInfoService.deleteAccountByUserId(userId);
            accountInfoService.deleteAccountRole(userId);
            responseData = new ResponseData(1,"删除成功");
        }
        return responseData;
    }

    /**
     * 分页获取员工信息
     * @param page 页数
     * @param rows 每页容量
     * @return 员工信息列表
     */
    @GetMapping("/getUserList")
    @ResponseBody
    public ResponseList<List<AccountInfo>> getUserList(int page, int rows,String name){
        return accountInfoService.getUserList(page,rows,name);
    }

    /**
     * 根据权限获取用户列表
     * @param roleId 权限ID
     * @return 用户列表
     */
    @PostMapping("/userListByRole")
    @ResponseBody
    public List<AccountInfo>getUserListByRole(int roleId){
        return accountInfoService.getUserListByRole(roleId);
    }

    /**
     * 根据用户id获取权限列表
     * @param userId 用户id
     * @return 权限列表
     */
    @PostMapping("/getRoleList")
    @ResponseBody
    public List<AccountRole>queryRoleByUserId(int userId){

        return  accountInfoService.queryRoleByUserId(userId);
    }


    @PostMapping("/loginVerify")
    public String loginVerify(Model model, String username, String password, HttpSession session){
        AccountInfo accountInfo = accountInfoService.loadUserByUsername(username);
        if (null == accountInfo){
            return "redirect:/error";
        }else {
            String code = DESHelper.decryptBasedDes(accountInfo.getPassword());
            boolean isSame  = password.equals(code);
            if (!isSame) {
//            throw new UsernameNotFoundException("密码不正确");
//                return "redirect:/login";
                return "redirect:/error";
            } else {
                session.setAttribute(WebSecurityConfig.SESSION_KEY, username);
                model.addAttribute("userInfo" ,accountInfo);
                return "redirect:/";
            }
        }
    }


    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute(WebSecurityConfig.SESSION_KEY);
        return "redirect:/login";
    }

    @GetMapping("/jobList")
    @ResponseBody
    public List<JobInfo>getJobList(){
         return  accountInfoService.getAllJobList();
    }

//    public List<AccountRole>
}
