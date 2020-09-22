package com.advance.libnet.response;

import java.io.Serializable;

/**
 * SuccessCode 类型数据
 *
 * @author xugang
 * @date 2020/9/11
 */
public class SuccessCodeResponse<T> extends BaseResponse<T> {
    private static final long serialVersionUID = -1445892348348349172L;

    private String successCode;
    private String message;

    public String getSuccessCode() {
        return successCode;
    }

    public void setSuccessCode(String successCode) {
        this.successCode = successCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
