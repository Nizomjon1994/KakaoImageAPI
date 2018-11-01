package com.everything4droid.kakaoimage.mvvm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.everything4droid.kakaoimage.R
import com.everything4droid.kakaoimage.data.entity.Image
import com.everything4droid.kakaoimage.data.response.Result
import com.everything4droid.kakaoimage.data.util.CoroutinesContextProvider
import com.everything4droid.kakaoimage.data.util.ErrorKit
import com.everything4droid.kakaoimage.domain.SearchImageUseCase
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Created by Khajiev Nizomjon on 29/10/2018.
 */
class SearchImageVM(
    private val searchImageUseCase: SearchImageUseCase,
    private val coroutinesContextProvider: CoroutinesContextProvider
) : ViewModel() {

    private val parentJob = Job()
    private val scope = CoroutineScope(coroutinesContextProvider.main + parentJob)
    private var searchJob: Job? = null

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
            searchJob = launchSearch(keyword)
        }
    }


    private fun launchSearch(keyword: String): Job {
        return scope.launch(coroutinesContextProvider.io) {
            val result = searchImageUseCase(keyword, pageCounter)
            withContext(coroutinesContextProvider.main) {
                _isSearching.value = false
                when (result) {

                    is Result.Success -> {
                        val image = result.data
                        if (!image.isEmpty()) {
                            _isEmpty.value = false
                            isEnd = image.isEnd()
                            emitUiState(image.imageList)
                        } else {
                            _isEmpty.value = true
                        }
                    }
                    is Result.Error -> {
                        _isError.value = result.exception as ErrorKit?
                    }
                }
            }
        }
    }

    private fun emitUiState(image: List<Image>? = null) {
        _uiModel.value = image
    }


    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}