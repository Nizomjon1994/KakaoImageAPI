package com.everything4droid.kakaoimage.mvvm

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.everything4droid.kakaoimage.data.util.CoroutinesContextProvider
import com.everything4droid.kakaoimage.domain.SearchImageUseCase

/**
 * Created by Khajiev Nizomjon on 29/10/2018.
 */
class SearchImageVMF(
    private val searchImageUseCase: SearchImageUseCase,
    private val contextProvider: CoroutinesContextProvider
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchImageVM(searchImageUseCase, contextProvider) as T
    }

}