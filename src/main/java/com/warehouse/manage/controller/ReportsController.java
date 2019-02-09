package com.warehouse.manage.controller;

import com.warehouse.manage.entity.AccountInfo;
import com.warehouse.manage.sysEnum.ProductTypeEnum;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ReportsController {
    @RequestMapping("reports")
    public String warehousingIndex(Model model) {
        return "reports/reports";
    }

    @RequestMapping("stockReports")
    public String stockReportsIndex(Model model) {
        List<String> productTypes = new ArrayList<>();
        for (ProductTypeEnum typeEnum :ProductTypeEnum.values()){
            productTypes.add(typeEnum.getProductTypeName());
        }
        model.addAttribute("productTypes" ,productTypes);
        return "reports/stockReports";
    }
}
