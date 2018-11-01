package com.everything4droid.kakaoimage.data.datasourse

import com.everything4droid.kakaoimage.data.response.ImageResponse
import com.everything4droid.kakaoimage.data.response.Result
import com.everything4droid.kakaoimage.data.service.Api
import com.everything4droid.kakaoimage.data.util.safeApiCall
import java.io.IOException

/**
 * Created by Khajiev Nizomjon on 29/10/2018.
 */
class KakaoDataSource(private val api: Api) {


    suspend fun searchImage(keyword: String, page: Int) = safeApiCall(
        call = { requestImage(keyword, page) },
        errorMessage = "Error loading Image"
    )

    private suspend fun requestImage(keyword: String, page: Int): Result<ImageResponse> {
        api.searchImage(keyword, page).await().let {
            return Result.Success(it)
        }
    }
}