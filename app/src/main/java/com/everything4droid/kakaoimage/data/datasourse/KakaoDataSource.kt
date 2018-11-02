package com.everything4droid.kakaoimage.data.datasourse

import com.everything4droid.kakaoimage.data.response.ImageResponse
import com.everything4droid.kakaoimage.data.service.Api
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Khajiev Nizomjon on 03/11/2018.
 */
class KakaoDataSource @Inject constructor(private val api: Api) {

    fun searchImageRx(keyword: String, page: Int): Single<ImageResponse> {
        return api.searchImageRx(keyword, page)
    }
}