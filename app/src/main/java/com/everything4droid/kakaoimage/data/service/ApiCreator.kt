package com.everything4droid.kakaoimage.data.service

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Khajiev Nizomjon on 29/10/2018.
 */
object ApiCreator {

    fun <T> get(type: Class<T>): T {

        return Retrofit.Builder()
            .baseUrl("https://dapi.kakao.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(makeHttpClient())
            .build()
            .create(type)
    }

    private fun makeHttpClient() = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(headersInterceptor())
        .build()

    fun headersInterceptor() = Interceptor { chain ->
        chain.proceed(
            chain.request().newBuilder()
                .addHeader("Authorization", "KakaoAK c535cff7926c7b848aecd9e55516f6a3")
                .build()
        )
    }
}