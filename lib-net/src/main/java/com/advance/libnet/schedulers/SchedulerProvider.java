package com.advance.libnet.schedulers;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 线程切换管理
 *
 * @author xugang
 * @date 2020/9/11
 */
public class SchedulerProvider implements BaseSchedulerProvider {

    @Nullable
    private static SchedulerProvider INSTANCE;

    private SchedulerProvider() {
    }

    public static SchedulerProvider getInstance() {
        if (INSTANCE == null) {
            synchronized (SchedulerProvider.class) {
                INSTANCE = new SchedulerProvider();
            }
        }
        return INSTANCE;
    }

    @Override
    @NonNull
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @Override
    @NonNull
    public Scheduler io() {
        return Schedulers.io();
    }

    @Override
    @NonNull
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }

    @NonNull
    @Override
    public <T> ObservableTransformer<T, T> applySchedulers() {
        // 上游io线程 下游ui线程
        return observable -> observable.subscribeOn(io())
                .observeOn(ui());
    }
}
