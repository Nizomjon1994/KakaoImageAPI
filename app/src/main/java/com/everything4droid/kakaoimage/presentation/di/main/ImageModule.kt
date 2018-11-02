package com.everything4droid.kakaoimage.presentation.di.main

import com.everything4droid.kakaoimage.domain.SearchImageUseCase
import com.everything4droid.kakaoimage.mvvm.SearchImageVMF
import dagger.Module
import dagger.Provides

/**
 * Created by Khajiev Nizomjon on 03/11/2018.
 */
@Module()
class ImageModule {

    @Provides
    fun providesImageVMF(
        searchImageUseCase: SearchImageUseCase
    ): SearchImageVMF {
        return SearchImageVMF(searchImageUseCase)
    }

}