package com.everything4droid.kakaoimage.presentation.di.module

import com.everything4droid.kakaoimage.data.service.Api
import com.everything4droid.kakaoimage.presentation.di.scope.AppScope
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

/**
 * Created by Khajiev Nizomjon on 03/11/2018.
 */
@Module
class NetModule {

    @AppScope
    @Provides
    fun providesGson(): Gson {
        val gson = GsonBuilder()
            .setLenient()
        return gson.create()
    }

    @AppScope
    @Provides
    fun providesOkHttpClient(): OkHttpClient {

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(headersInterceptor())
        return okHttpClient.build()
    }

    fun headersInterceptor() = Interceptor { chain ->
        chain.proceed(
            chain.request().newBuilder()
                .addHeader("Authorization", "KakaoAK c535cff7926c7b848aecd9e55516f6a3")
                .build()
        )
    }

    @AppScope
    @Provides
    fun providesRetrofit(gson: Gson, client: OkHttpClient): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dapi.kakao.com")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)

        return retrofit.build()

    }

    @AppScope
    @Provides
    fun providesEndpoint(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }


}