package com.advance.libnet;

import com.advance.libnet.interceptor.HttpLogger;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * OkHttp 管理
 *
 * @author xugang
 * @date 2020/9/11
 */
public class OkHttpManager {

    private static volatile OkHttpClient sOkHttpClient;

    /**
     * OkHttpClient 初始化
     */
    public static OkHttpClient initOkHttpClient() {
        if (null == sOkHttpClient) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLogger());
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            sOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request()
                                    .newBuilder()
                                    .removeHeader("User-Agent")
                                    .addHeader("User-Agent", getUserAgent())
                                    .build();
                            return chain.proceed(request);
                        }
                    })
                    .addInterceptor(loggingInterceptor)
                    .build();
        }
        return sOkHttpClient;
    }


    /**
     * 返回正确的UserAgent
     */
    public static String getUserAgent() {
        try {
            String userAgent = "";
            StringBuffer sb = new StringBuffer();
            userAgent = System.getProperty("http.agent");//Dalvik/2.1.0 (Linux; U; Android 6.0.1; vivo X9L Build/MMB29M)

            for (int i = 0, length = userAgent.length(); i < length; i++) {
                char c = userAgent.charAt(i);
                if (c <= '\u001f' || c >= '\u007f') {
                    sb.append(String.format("\\u%04x", (int) c));
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


}
