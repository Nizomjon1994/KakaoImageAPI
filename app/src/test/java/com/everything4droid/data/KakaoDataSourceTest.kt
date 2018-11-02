package com.everything4droid.data

import com.everything4droid.kakaoimage.data.datasourse.KakaoDataSource
import com.everything4droid.kakaoimage.data.entity.Image
import com.everything4droid.kakaoimage.data.entity.Meta
import com.everything4droid.kakaoimage.data.response.ImageResponse
import com.everything4droid.kakaoimage.data.response.Result
import com.everything4droid.kakaoimage.data.service.Api
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.experimental.CompletableDeferred
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import org.junit.Assert.assertEquals

/**
 * Created by Khajiev Nizomjon on 02/11/2018.
 */
class KakaoDataSourceTest {


    private val image1 = Image(
        collection = "Blog",
        dateTime = "2017-12-34",
        height = 600,
        width = 800,
        thumbUrl = "some_thumburl",
        imageUrl = "some_imageurl",
        displayName = "Image1",
        docUrl = "docUrl1"
    )

    private val image2 = Image(
        collection = "Blog2",
        dateTime = "2017-12-34",
        height = 600,
        width = 800,
        thumbUrl = "some_thumburl",
        imageUrl = "some_imageurl",
        displayName = "Image2",
        docUrl = "docUrl2"
    )

    private val meta = Meta(
        isEnd = false,
        totalCount = 100,
        pageableCount = 123
    )

    private val images = listOf(image1, image2)

    private val service: Api = mock()
    private val dataSource = KakaoDataSource(service)

    @Test
    fun getImages_withSuccess() = runBlocking {

        withImagesSuccess("Dog", 0, images = images)

        val result = dataSource.searchImage("Dog", 0)
        verify(service).searchImage("Dog", 0)

        assertEquals(Result.Success(ImageResponse(meta,images)), Result.Success(result).data)

    }

    @Test
    fun getEmptyImages_withSuccess() = runBlocking {

        withImagesSuccess("Dog", 0, images = emptyList())

        val result = dataSource.searchImage("Dog", 0)
        verify(service).searchImage("Dog", 0)

        assertEquals(Result.Success(ImageResponse(meta, emptyList())), Result.Success(result).data)

    }

    private fun withImagesSuccess(keyword: String, page: Int, images: List<Image>) {
        val result = ImageResponse(Meta(false, 100, 123), images)
        whenever(service.searchImage(keyword, page)).thenReturn(CompletableDeferred(result))
    }

}