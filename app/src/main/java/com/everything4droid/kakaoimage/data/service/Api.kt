package com.everything4droid.kakaoimage.data.service

import com.everything4droid.kakaoimage.data.response.ImageResponse
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Created by Khajiev Nizomjon on 29/10/2018.
 */
interface Api {

    @GET("/v2/search/image")
    fun searchImage(
        @Query("query") keyword: String,
        @Query("page") page: Int
    ): Deferred<ImageResponse>

}