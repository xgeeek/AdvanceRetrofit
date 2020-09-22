package com.advance.libnet.response;

import java.io.Serializable;

/**
 * Code 类型数据
 *
 * @author xugang
 * @date 2020/9/11
 */
public class CodeResponse<T> extends BaseResponse<T> {
    private static final long serialVersionUID = -7381751792075259689L;

    private String code;
    private String msg;

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
