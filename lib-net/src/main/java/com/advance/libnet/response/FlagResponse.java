package com.advance.libnet.response;

import java.io.Serializable;

/**
 * Flag 类型数据
 * @author xugang
 * @date 2020/9/11
 */
public class FlagResponse<T> extends BaseResponse<T>{
    private static final long serialVersionUID = 7804376641219733762L;

    private boolean flag;
    private String msg;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
