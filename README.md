## AdvanceRetrofit
### retrofit+okhttp+rxjava3+多域名管理+多数据类型处理

## 如何将retrofit返回的可观察数据进行统一处理？
#### 在项目中我们都会遇到对服务端返回的数据进行一层统一的上层处理，做一些用户失效弹窗之类的需求。简单的做法就是服务端在每个接口都返回特定的错误码。
#### 这时我们需要对接收数据回调有一个统一处理的地方。 我们可以利用Rxjava的数据源转换来处理。如下

### retrofit 返回数据
```
 @GET("news/index/{id}/")
 Observable<CodeResponse<List<NewExpressEnetity>>> getNew24Data(@Path("id") int id);
```
### ObservableTransformer 统一处理数据
```
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
                // 这里也可以对正常数据做一些统一处理
                return Observable.just(tResponse);
            } else {
                // 对异常进行统一处理
                return Observable.error(new ApiException(code, tResponse.getMsg()));
            }
        }
    }
```

## 多域名/多数据类型如何处理呢
### 多域名
#### 通过Map进行存储
```
private Map<String, Retrofit> retrofits = Collections.synchronizedMap(new HashMap<String, Retrofit>());
```

### 多数据类型
#### 这里我是通过定义不同的 ObservableTransformer 来进行处理的

## 看一个请求实例
```
RetrofitManager.getInstance()
                .getRetrofit(NewsService.API_BASE_URL)
                .create(NewsService::class.java)
                .getNew24Data(1)
                .compose(CodeResponseTransformer.handleResponse())
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .subscribe({
                    Log.d("apiSuccess", it.toString())

                }, { throwable ->
                    if(throwable is ApiException){
                        Log.d("apiException", "${throwable.code}+${throwable.msg}")
                    }else{
                        Log.d("apiException", "${throwable.message}")
                    }
                })
```
