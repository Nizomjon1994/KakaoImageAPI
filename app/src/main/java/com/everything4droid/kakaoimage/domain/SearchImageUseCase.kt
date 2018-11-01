package com.everything4droid.kakaoimage.domain

import com.everything4droid.kakaoimage.data.repository.SearchImageRepository
import com.everything4droid.kakaoimage.data.response.ImageResponse
import com.everything4droid.kakaoimage.data.response.Result

/**
 * Created by Khajiev Nizomjon on 29/10/2018.
 */
class SearchImageUseCase(private val searchImageRepository: SearchImageRepository) {
    suspend operator fun invoke(keyword: String, page: Int): Result<ImageResponse> {
        return searchImageRepository.searchImage(keyword, page)
    }
}