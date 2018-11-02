package com.everything4droid.kakaoimage.domain

import com.everything4droid.kakaoimage.data.entity.ImageRequestBody
import com.everything4droid.kakaoimage.data.repository.SearchImageRepository
import com.everything4droid.kakaoimage.data.response.ImageResponse
import com.everything4droid.kakaoimage.data.util.AbsRxSingleUseCase
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Khajiev Nizomjon on 03/11/2018.
 */
class SearchImageUseCase @Inject constructor(
    val searchImageRepository: SearchImageRepository,
    backgroundThread: Scheduler,
    mainThread: Scheduler
) : AbsRxSingleUseCase<ImageResponse, ImageRequestBody>(backgroundThread, mainThread) {
    override fun createSingle(params: ImageRequestBody?): Single<ImageResponse> {
        return searchImageRepository.searchImageRx(params!!.keyword, params.page)
    }
}