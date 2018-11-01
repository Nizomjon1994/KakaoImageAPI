package com.everything4droid.kakaoimage

import android.app.Application
import com.everything4droid.kakaoimage.data.datasourse.KakaoDataSource
import com.everything4droid.kakaoimage.data.repository.SearchImageRepository
import com.everything4droid.kakaoimage.data.service.Api
import com.everything4droid.kakaoimage.data.service.ApiCreator
import com.everything4droid.kakaoimage.data.util.CoroutinesContextProvider
import com.everything4droid.kakaoimage.domain.SearchImageUseCase
import com.everything4droid.kakaoimage.mvvm.SearchImageVM
import com.everything4droid.kakaoimage.mvvm.SearchImageVMF
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.android.startKoin

/**
 * Created by Khajiev Nizomjon on 29/10/2018.
 */
class App : Application() {

    private val module = org.koin.dsl.module.applicationContext {

        bean { ApiCreator.get(Api::class.java) }
        bean { CoroutinesContextProvider() }

        bean { KakaoDataSource(get()) }

        bean { SearchImageRepository(get()) }

        bean { SearchImageUseCase(get()) }

        bean { SearchImageVMF(get(), get()) }
        viewModel { SearchImageVM(get(), get()) }


    }

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(module))
        Fresco.initialize(this)
    }
}