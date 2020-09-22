package com.advance.libnet.transformer;

import com.advance.libnet.exception.ApiException;
import com.advance.libnet.exception.LocalException;
import com.advance.libnet.response.BaseResponse;
import com.advance.libnet.response.CodeResponse;
import com.advance.libnet.response.FlagCodeResponse;
import com.advance.libnet.response.FlagResponse;
import com.advance.libnet.response.ReturnCodeResponse;
import com.advance.libnet.response.SuccessCodeResponse;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.functions.Function;

/**
 * 对上游拿到的 response数据解析
 *
 * 不统一处理 减少耦合 和接收时的强转
 *
 * @author xugang
 * @date 2020/9/11
 */
@Deprecated
public class ResponseTransformer {

    /**
     * 处理上游数据
     *
     * @param <T>
     */
    public static <T> ObservableTransformer<BaseResponse<T>, BaseResponse<T>> handleResponse() {
        // 一旦源Observable遇到错误，这个onErrorResumeNext会把源Observable用一个新的Observable替掉，
        // 然后这个新的Observable如果没遇到什么问题就会释放item给Observer
        return upstream -> upstream
                .onErrorResumeNext(new ErrorResumeFunction<>())
                .flatMap(new ResponseFunction<>());
    }

    /**
     * 非服务器产生的异常，比如本地无网络请求，Json数据解析错误等等。
     *
     * @param <T>
     */
    private static class ErrorResumeFunction<T> implements Function<Throwable, ObservableSource<? extends BaseResponse<T>>> {

        @Override
        public ObservableSource<? extends BaseResponse<T>> apply(Throwable throwable) throws Exception {
            return Observable.error(LocalException.handleException(throwable));
        }
    }

    /**
     * 服务其返回的数据解析
     * 正常服务器返回数据和服务器可能产出的exception
     *
     * @param <T>
     */
    private static class ResponseFunction<T> implements Function<BaseResponse<T>, ObservableSource<BaseResponse<T>>> {

        @Override
        public ObservableSource<BaseResponse<T>> apply(BaseResponse<T> tResponse) {
            if (tResponse instanceof CodeResponse) {
                CodeResponse<T> tCodeResponse = (CodeResponse<T>) tResponse;
                String code = tCodeResponse.getCode();
                if (code.equals("0")) {
                    return Observable.just(tCodeResponse);
                } else {
                    return Observable.error(new ApiException(code, tCodeResponse.getMsg()));
                }
            } else if (tResponse instanceof FlagCodeResponse) {
                FlagCodeResponse<T> tFlagCodeResponse = (FlagCodeResponse<T>) tResponse;
                boolean flag = tFlagCodeResponse.isFlag();
                if (flag) {
                    return Observable.just(tFlagCodeResponse);
                } else {
                    return Observable.error(new ApiException(tFlagCodeResponse.getCode(), tFlagCodeResponse.getMsg()));
                }
            } else if (tResponse instanceof FlagResponse) {
                FlagResponse<T> tFlagResponse = (FlagResponse<T>) tResponse;
                boolean flag = tFlagResponse.isFlag();
                if (flag) {
                    return Observable.just(tFlagResponse);
                    //return Observable.just(tResponse.getData());
                } else {
                    return Observable.error(new ApiException(LocalException.UNKNOWN, tFlagResponse.getMsg()));
                }

            } else if (tResponse instanceof ReturnCodeResponse) {
                ReturnCodeResponse<T> tReturnCodeResponse = (ReturnCodeResponse<T>) tResponse;
                String returnCode = tReturnCodeResponse.getReturnCode();
                if (returnCode.equals("0")) {
                    return Observable.just(tReturnCodeResponse);
                } else {
                    return Observable.error(new ApiException(returnCode, tReturnCodeResponse.getMessage()));
                }

            } else if (tResponse instanceof SuccessCodeResponse) {
                SuccessCodeResponse<T> tSuccessCodeResponse = (SuccessCodeResponse<T>) tResponse;
                String successCode = tSuccessCodeResponse.getSuccessCode();
                if (successCode.equals("0")) {
                    return Observable.just(tSuccessCodeResponse);
                } else {
                    return Observable.error(new ApiException(successCode, tSuccessCodeResponse.getMessage()));
                }
            } else {
                return Observable.error(new ApiException(LocalException.UNKNOWN, "未知数据类型"));
            }

        }
    }
}
