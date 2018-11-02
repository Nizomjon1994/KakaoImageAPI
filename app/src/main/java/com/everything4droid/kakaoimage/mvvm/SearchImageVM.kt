package com.everything4droid.kakaoimage.mvvm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.everything4droid.kakaoimage.data.entity.Image
import com.everything4droid.kakaoimage.data.entity.ImageRequestBody
import com.everything4droid.kakaoimage.data.response.ImageResponse
import com.everything4droid.kakaoimage.data.response.Result
import com.everything4droid.kakaoimage.data.util.DefaultSingleObserver
import com.everything4droid.kakaoimage.data.util.ErrorKit
import com.everything4droid.kakaoimage.domain.SearchImageUseCase
import kotlinx.coroutines.experimental.*

/**
 * Created by Khajiev Nizomjon on 29/10/2018.
 */
class SearchImageVM(
    private val searchImageUseCase: SearchImageUseCase
) : ViewModel() {

    var pageCounter = 0
    var isEnd = false


    private val _isSearching = MutableLiveData<Boolean>()
    val isSearching: LiveData<Boolean>
        get() = _isSearching

    private val _uiModel = MutableLiveData<List<Image>>()
    val uiModel: LiveData<List<Image>>
        get() = _uiModel

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean>
        get() = _isEmpty

    private val _isError = MutableLiveData<ErrorKit>()
    val isError: LiveData<ErrorKit>
        get() = _isError

    fun reset() {
        pageCounter = 0
        isEnd = false
    }


    fun search(keyword: String) {
        pageCounter++
        if (!isEnd) {
            _isSearching.postValue(true)
            launchSearchRx(keyword)
        }
    }

    private fun launchSearchRx(keyword: String) {
        val imageRequestBody = ImageRequestBody(keyword, pageCounter)
        searchImageUseCase.execute(SearchImageObserver(), imageRequestBody)
    }

    inner class SearchImageObserver : DefaultSingleObserver<Result<ImageResponse>>() {
        override fun onSuccess(model: Result<ImageResponse>) {
            super.onSuccess(model)
            _isSearching.value = false
            when (model) {

                is Result.Success -> {
                    val image = model.data
                    if (!image.isEmpty()) {
                        _isEmpty.value = false
                        isEnd = image.isEnd()
                        emitUiState(image.imageList)
                    } else {
                        _isEmpty.value = true
                    }
                }
                is Result.Error -> {
                    _isError.value = model.exception as ErrorKit?
                }
            }
        }

        override fun onError(error: Throwable) {
            super.onError(error)
            _isSearching.value = false
        }

    }

    private fun emitUiState(image: List<Image>? = null) {
        _uiModel.value = image
    }


    override fun onCleared() {
        super.onCleared()
        searchImageUseCase.dispose()
    }
}