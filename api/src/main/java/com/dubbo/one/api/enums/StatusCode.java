package com.dubbo.one.api.enums;

/**
 * 自定义返回状态码
 */
public enum StatusCode {
    Success(0, "成功"),
    Fail(-1, "失败"),
    ErrParams(200,"无效的参数"),
    ItemNotExist(201,"商品不存在");

    private Integer code;//状态码
    private String msg;//简要消息

    StatusCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
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
}
