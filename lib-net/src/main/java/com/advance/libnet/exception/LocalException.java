package com.advance.libnet.exception;

import android.net.ParseException;
import android.util.Log;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * 对本地产出的 Exception
 * 进行处理
 *
 * @author xugang
 * @date 2020/9/11
 */
public class LocalException {
    /**
     * 未知错误
     */
    public static final String UNKNOWN = "20200911";

    /**
     * 解析错误
     */
    public static final String PARSE_ERROR = "20200912";

    /**
     * 网络错误
     */
    public static final String NETWORK_ERROR = "20200913";

    /**
     * 解析数据错误
     */
    public static final String DATA_ERROR = "20200914";

    public static ApiException handleException(Throwable e) {
        Log.d("okhttp localException", e.getMessage());
        ApiException ex;
        if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            //解析错误
            ex = new ApiException(PARSE_ERROR, e.getMessage());
            return ex;
        } else if (e instanceof ConnectException) {
            //网络错误
            ex = new ApiException(NETWORK_ERROR, e.getMessage());
            return ex;
        } else if (e instanceof UnknownHostException || e instanceof SocketTimeoutException) {
            //连接错误
            ex = new ApiException(NETWORK_ERROR, e.getMessage());
            return ex;
        } else {
            //未知错误
            ex = new ApiException(UNKNOWN, e.getMessage());
            return ex;
        }
    }
}

