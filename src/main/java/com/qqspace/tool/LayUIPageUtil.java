package com.qqspace.tool;

import java.util.List;

/**
 * @Title
 * @Description
 * @Author zzke
 * @Email 956468746@qq.com
 */
public class LayUIPageUtil<T> {
    //总记录数（表）
    private Integer count = 0;
    private Integer code;
    private String msg;
    private List<T> data;

    public LayUIPageUtil() {
    }

    public LayUIPageUtil(Integer count, List<T> data) {
        this(0,"调用成功！",count,data);
    }

    public LayUIPageUtil(Integer code, String msg,Integer count, List<T> data) {
        this.count = count;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LayUIPageUtil{" +
                "count=" + count +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
