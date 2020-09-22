package com.advance.advanceretrofit

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.advance.libcommon.net.NewsService
import com.advance.libnet.RetrofitManager
import com.advance.libnet.exception.ApiException
import com.advance.libnet.schedulers.SchedulerProvider
import com.advance.libnet.transformer.CodeResponseTransformer
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.internal.and
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_code_response_1.setOnClickListener {
            RetrofitManager.getInstance()
                .getRetrofit(NewsService.API_BASE_URL)
                .create(NewsService::class.java)
                .getNew24Data(1)
                .compose(CodeResponseTransformer.handleResponse())
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .subscribe({
                    Log.d("apiSuccess", it.toString())

                }, { throwable ->
                    val apiException: ApiException = throwable as ApiException
                    Log.d("apiException", "${apiException.code}+${apiException.msg}")

                })

        }

    }
}