package com.advance.libnet.response;

import java.io.Serializable;

/**
 * FlagCode 类型数据
 * @author xugang
 * @date 2020/9/11
 */
public class FlagCodeResponse<T> extends BaseResponse<T> {
    private static final long serialVersionUID = -4252233902030887087L;

    private boolean flag;
    private boolean timeout;
    private String msg;
    private String code;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isTimeout() {
        return timeout;
    }

    public void setTimeout(boolean timeout) {
        this.timeout = timeout;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
