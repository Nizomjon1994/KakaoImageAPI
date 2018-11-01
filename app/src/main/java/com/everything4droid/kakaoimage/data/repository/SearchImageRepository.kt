package com.everything4droid.kakaoimage.data.repository

import com.everything4droid.kakaoimage.data.datasourse.KakaoDataSource
import com.everything4droid.kakaoimage.data.response.ImageResponse
import com.everything4droid.kakaoimage.data.response.Result

/**
 * Created by Khajiev Nizomjon on 29/10/2018.
 */
class SearchImageRepository(private val kakaoDataSource: KakaoDataSource) {

    suspend fun searchImage(keyword: String,page:Int): Result<ImageResponse> {
        return kakaoDataSource.searchImage(keyword,page)
    }

}