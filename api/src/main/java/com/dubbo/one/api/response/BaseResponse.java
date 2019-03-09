package com.dubbo.one.api.response;

import com.dubbo.one.api.enums.StatusCode;

import java.io.Serializable;

/**
 * 自定义页面返回格式
 */
public class BaseResponse<T> implements Serializable {

    private Integer code;//状态码
    private String msg;//简要消息
    private T data;//要返回的数据

    public BaseResponse() {
    }

    public BaseResponse(StatusCode statusCode) {
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
    }

    public BaseResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseResponse(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
