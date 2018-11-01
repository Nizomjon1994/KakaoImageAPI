package com.everything4droid.kakaoimage.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by Khajiev Nizomjon on 01/11/2018.
 */
data class Meta(
    @SerializedName("is_end") val isEnd: Boolean,
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("pageable_count") val pageableCount: Int
)