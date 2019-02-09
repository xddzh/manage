package com.warehouse.manage.sysEnum;

public enum ErrorCodeEnum {

    LoginSucess("登录成功",1);

    private int code;
    private String desc;


    private ErrorCodeEnum(String desc, int code){
        this.code = code;
        this.desc = desc;
    }

    public static String getDesc(int code){
        for (ErrorCodeEnum errorCode: ErrorCodeEnum.values()
        ) {
            if (errorCode.getCode() == code){
                return  errorCode.desc;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
