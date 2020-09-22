package com.advance.libnet.transformer;

import com.advance.libnet.exception.ApiException;
import com.advance.libnet.exception.LocalException;
import com.advance.libnet.response.CodeResponse;


import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.functions.Function;

/**
 * @author xugang
 * @date 2020/9/14
 */
public class CodeResponseTransformer {

    /**
     * 处理上游数据
     *
     * @param <T>
     */
    public static <T> ObservableTransformer<CodeResponse<T>, CodeResponse<T>> handleResponse() {
        // 一旦源Observable遇到错误，这个onErrorResumeNext会把源Observable用一个新的Observable替掉，
        // 然后这个新的Observable如果没遇到什么问题就会释放item给Observer
        return upstream -> upstream
                .onErrorResumeNext(new CodeResponseTransformer.ErrorResumeFunction<>())
                .flatMap(new CodeResponseTransformer.ResponseFunction<>());
    }

    /**
     * 非服务器产生的异常，比如本地无网络请求，Json数据解析错误等等。
     *
     * @param <T>
     */
    private static class ErrorResumeFunction<T> implements Function<Throwable, ObservableSource<? extends CodeResponse<T>>> {

        @Override
        public ObservableSource<? extends CodeResponse<T>> apply(Throwable throwable) throws Exception {
            return Observable.error(LocalException.handleException(throwable));
        }
    }

    /**
     * 服务其返回的数据解析
     * 正常服务器返回数据和服务器可能产出的exception
     *
     * @param <T>
     */
    private static class ResponseFunction<T> implements Function<CodeResponse<T>, ObservableSource<CodeResponse<T>>> {

        @Override
        public ObservableSource<CodeResponse<T>> apply(CodeResponse<T> tResponse) {
            String code = tResponse.getCode();
            if (code.equals("0")) {
                return Observable.just(tResponse);
            } else {
                return Observable.error(new ApiException(code, tResponse.getMsg()));
            }
        }
    }
}
