package com.advance.libnet.interceptor;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author xugang
 * @date 2020/9/11
 */
public class HttpLogger implements HttpLoggingInterceptor.Logger {
    @Override
    public void log(@NotNull String s) {
        Log.d("okhttp", s);
    }
}
