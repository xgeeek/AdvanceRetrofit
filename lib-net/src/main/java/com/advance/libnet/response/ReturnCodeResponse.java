package com.advance.libnet.response;

import java.io.Serializable;

/**
 * ReturnCode 类型数据
 * @author xugang
 * @date 2020/9/11
 */
public class ReturnCodeResponse<T> extends BaseResponse<T>{
    private static final long serialVersionUID = 7203045463385568195L;

    private String returnCode;
    private String message;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
