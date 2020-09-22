package com.advance.libnet.exception;

/**
 * 服务器产出的业务 Exception
 * @author xugang
 * @date 2020/9/11
 */
public class ApiException extends Exception {

    private static final long serialVersionUID = 9180664580427599951L;
    private String code;
    private String msg;

    public ApiException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

