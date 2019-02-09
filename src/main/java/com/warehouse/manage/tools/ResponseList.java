package com.warehouse.manage.tools;

import java.awt.*;
import java.io.Serializable;

public class ResponseList <T> implements Serializable {
    private int code;
    //"状态描述"
    private String msg;
    private long total;
    private T rows;

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

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public T getRows() {
        return rows;
    }

    public void setRows(T rows) {
        this.rows = rows;
    }

    public ResponseList(long total, T rows){
        super();
        if (rows != null)
            this.rows = rows;
        this.code = 1;
        this.total = total;
        this.msg = "处理成功";
    }
}
