package com.advance.libcommon.net;

import com.advance.libcommon.bean.respone.NewExpressEnetity;
import com.advance.libnet.response.CodeResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 资讯请求 test
 * @author xugang
 * @date 2020/9/11
 */
public interface NewsService {

    String API_BASE_URL = "https://future.18qh.com/webapi/";//行为上报

    //获取资讯中心文章列表
    @GET("news/index/{id}/")
    Observable<CodeResponse<List<NewExpressEnetity>>> getNew24Data(@Path("id") int id);
}
