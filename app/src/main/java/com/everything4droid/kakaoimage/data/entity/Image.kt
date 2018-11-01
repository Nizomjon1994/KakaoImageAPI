package com.everything4droid.kakaoimage.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by Khajiev Nizomjon on 29/10/2018.
 */
data class Image(
    val collection: String,
    @SerializedName("datetime") val dateTime: String,
    val height: Int,
    val width: Int,
    @SerializedName("thumbnail_url") val thumbUrl: String,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("display_sitename") val displayName: String,
    @SerializedName("doc_url") val docUrl: String
)