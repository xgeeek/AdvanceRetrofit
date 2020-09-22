package com.advance.libnet.response;

import java.io.Serializable;

/**
 * 响应数据基类
 *
 * @author xugang
 * @date 2020/9/11
 */
public class BaseResponse<T> implements Serializable {
    private static final long serialVersionUID = 4224217922188989039L;

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
