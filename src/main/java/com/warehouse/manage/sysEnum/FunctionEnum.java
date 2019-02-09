package com.warehouse.manage.sysEnum;

public enum FunctionEnum {
    Warehousing("仓库入库",1),
    Delivery("仓库出库",2),
    EmployeeManagement("员工管理",3),
    ProductClass("客户管理",4),
    ReportForm("报表管理",5);


    private int functionId;
    private String functionName;


    private FunctionEnum(String functionName, int functionId){
        this.functionId = functionId;
        this.functionName = functionName;
    }

    public static String getFunctionName(int functionId){
        for (FunctionEnum functionEnum: FunctionEnum.values()
        ) {
            if (functionEnum.getFunctionId() == functionId){
                return  functionEnum.functionName;
            }
        }
        return null;
    }

    public int getFunctionId() {
        return functionId;
    }

    public void setFunctionId(int functionId) {
        this.functionId = functionId;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }
}
