package com.everything4droid.kakaoimage.data.repository

import com.everything4droid.kakaoimage.data.datasourse.KakaoDataSource
import com.everything4droid.kakaoimage.data.response.ImageResponse
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Khajiev Nizomjon on 03/11/2018.
 */
class SearchImageRepository @Inject constructor(private val kakaoDataSource: KakaoDataSource) {

    fun searchImageRx(keyword: String, page: Int): Single<ImageResponse> {
        return kakaoDataSource.searchImageRx(keyword, page)
    }

}