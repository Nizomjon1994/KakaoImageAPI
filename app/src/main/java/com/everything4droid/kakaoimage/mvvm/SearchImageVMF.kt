package com.everything4droid.kakaoimage.mvvm

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.everything4droid.kakaoimage.domain.SearchImageUseCase

/**
 * Created by Khajiev Nizomjon on 29/10/2018.
 */
class SearchImageVMF(
    private val searchImageUseCase: SearchImageUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchImageVM(searchImageUseCase) as T
    }

}