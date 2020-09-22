package com.advance.libnet.transformer;

import com.advance.libnet.exception.ApiException;
import com.advance.libnet.exception.LocalException;
import com.advance.libnet.response.CodeResponse;
import com.advance.libnet.response.FlagResponse;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.functions.Function;

/**
 * @author xugang
 * @date 2020/9/14
 */
public class FlagResponseTransformer {
    /**
     * 处理上游数据
     *
     * @param <T>
     */
    public static <T> ObservableTransformer<FlagResponse<T>, FlagResponse<T>> handleResponse() {
        // 一旦源Observable遇到错误，这个onErrorResumeNext会把源Observable用一个新的Observable替掉，
        // 然后这个新的Observable如果没遇到什么问题就会释放item给Observer
        return upstream -> upstream
                .onErrorResumeNext(new FlagResponseTransformer.ErrorResumeFunction<>())
                .flatMap(new FlagResponseTransformer.ResponseFunction<>());
    }

    /**
     * 非服务器产生的异常，比如本地无网络请求，Json数据解析错误等等。
     *
     * @param <T>
     */
    private static class ErrorResumeFunction<T> implements Function<Throwable, ObservableSource<? extends FlagResponse<T>>> {

        @Override
        public ObservableSource<? extends FlagResponse<T>> apply(Throwable throwable) throws Exception {
            return Observable.error(LocalException.handleException(throwable));
        }
    }

    /**
     * 服务其返回的数据解析
     * 正常服务器返回数据和服务器可能产出的exception
     *
     * @param <T>
     */
    private static class ResponseFunction<T> implements Function<FlagResponse<T>, ObservableSource<FlagResponse<T>>> {

        @Override
        public ObservableSource<FlagResponse<T>> apply(FlagResponse<T> tResponse) {
            boolean flag = tResponse.isFlag();
            if (flag) {
                return Observable.just(tResponse);
            } else {
                return Observable.error(new ApiException(LocalException.UNKNOWN, tResponse.getMsg()));
            }
        }
    }
}
