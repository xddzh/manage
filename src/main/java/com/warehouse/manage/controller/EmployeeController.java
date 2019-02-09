package com.warehouse.manage.controller;


import com.warehouse.manage.entity.AccountInfo;
import com.warehouse.manage.sysEnum.FunctionEnum;
import com.warehouse.manage.sysEnum.ProductTypeEnum;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EmployeeController {

    @RequestMapping("employee")
    public String employeeIndex(Model model) {
        return "employee/employee";
    }
}
