package com.advance.libnet;


import com.advance.libnet.converter.GsonConverterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;

/**
 * 多域名管理
 *
 * @author xugang
 * @date 2020/9/11
 */
public class RetrofitManager {

    private static RetrofitManager INSTANCE;
    private Map<String, Retrofit> retrofits = Collections.synchronizedMap(new HashMap<String, Retrofit>());

    private RetrofitManager() {
    }

    public static RetrofitManager getInstance() {
        if (INSTANCE == null) {
            synchronized (RetrofitManager.class) {
                INSTANCE = new RetrofitManager();
            }
        }
        return INSTANCE;
    }

    public Retrofit getRetrofit(String baseUrl) {
        Retrofit retrofit = retrofits.get(baseUrl);
        if (retrofit != null) {
            return retrofit;
        } else {
            Gson gson = new GsonBuilder()
                    //.excludeFieldsWithoutExposeAnnotation() // 不导出实体中没有用@Expose注解的属性
                    .enableComplexMapKeySerialization() // 支持Map的key为复杂对象的形式
                    .serializeNulls()
                    //.setDateFormat("yyyy-MM-dd HH:mm:ss")// 时间转化为特定格式
                    //.setPrettyPrinting() // 对json结果格式化.
                    .create();

            Retrofit newRetrofit = new Retrofit
                    .Builder()
                    .client(OkHttpManager.initOkHttpClient())
                    .baseUrl(baseUrl)
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            retrofits.put(baseUrl, newRetrofit);
            return newRetrofit;
        }
    }

}
