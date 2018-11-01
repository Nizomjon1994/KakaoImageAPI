package com.everything4droid.kakaoimage.data.response

import com.everything4droid.kakaoimage.data.entity.Image
import com.everything4droid.kakaoimage.data.entity.Meta
import com.google.gson.annotations.SerializedName

/**
 * Created by Khajiev Nizomjon on 29/10/2018.
 */
data class ImageResponse(
    @SerializedName("meta") val meta: Meta,
    @SerializedName("documents") val imageList: List<Image>
) {

    fun isEmpty() = imageList.isEmpty()

    fun isEnd() = meta.isEnd

}