package com.warehouse.manage.tools;

import java.io.Serializable;

public class ResponseData<T> implements Serializable {
    private static final long serialVersionUID = 8322180227824525845L;
    //"返回状态 1:成功，其他失败"
    private int code;
    //"状态描述"
    private String msg;
    private int totalPage;
    private int page;
    private T data;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResponseData(){
        super();
    }

    public ResponseData(int code, String msg, int page, int totalPage, T data){
        super();
        if (data != null)
            this.data = data;
        this.code = code;
        this.totalPage = totalPage;
        this.page = page;
        this.msg = msg;
    }

    public ResponseData(int code, String msg){
        super();
        this.code = code;
        this.totalPage = 1;
        this.page = 1;
        this.msg = msg;
    }

    public ResponseData(int code, String msg, T data){
        super();
        if (data != null)
            this.data = data;
        this.code = code;
        this.totalPage = 1;
        this.page = 1;
        this.msg = msg;
    }

    public ResponseData(T data){
        super();
        if (data != null)
            this.data = data;
        this.code = 1;
        this.totalPage = 1;
        this.page = 1;
        this.msg = "处理成功";
    }
}
