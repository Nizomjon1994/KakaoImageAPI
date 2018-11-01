package com.everything4droid.kakaoimage.data.util

import android.databinding.BindingAdapter
import android.graphics.drawable.Animatable
import android.net.Uri
import android.widget.ImageView
import com.facebook.drawee.view.SimpleDraweeView
import android.support.annotation.DrawableRes
import android.widget.TextView
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.drawee.controller.ControllerListener
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.generic.GenericDraweeHierarchy
import com.facebook.imagepipeline.image.ImageInfo
import java.text.SimpleDateFormat


/**
 * Created by Khajiev Nizomjon on 31/10/2018.
 */

// ImageLoad
@BindingAdapter(value = ["imageUrl", "placeholderImageRes", "request_width", "request_height"], requireAll = false)
fun loadImage(
    imageView: SimpleDraweeView, uri: String,
    @DrawableRes placeholderImageRes: Int,
    width: Int, height: Int
) {
    val builder = GenericDraweeHierarchyBuilder(imageView.context.resources)
    val hierarchy = builder
        .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
        .build()

    val requestBuilder = ImageRequestBuilder.newBuilderWithSource(Uri.parse(uri))
        .setResizeOptions(ResizeOptions(width, height))
        .setProgressiveRenderingEnabled(true)
        .build()

    val contollerListener: ControllerListener<ImageInfo> = object : BaseControllerListener<ImageInfo>() {
        override fun onFinalImageSet(id: String?, imageInfo: ImageInfo?, animatable: Animatable?) {
            super.onFinalImageSet(id, imageInfo, animatable)
            if (imageInfo != null) {
                imageView.layoutParams.height = imageInfo.height
                imageView.requestLayout()
            }
        }
    }

    val contoller = Fresco.newDraweeControllerBuilder()
        .setControllerListener(contollerListener)
        .setImageRequest(requestBuilder)
        .build()

    imageView.hierarchy = hierarchy
    imageView.controller = contoller

}

// DateFormat
@BindingAdapter("date")
fun dateFormat(textView: TextView, dateValue: String) {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val output = SimpleDateFormat("yyyy-MM-dd")
    val d = sdf.parse(dateValue)
    val formattedTime = output.format(d)
    textView.text = formattedTime
}