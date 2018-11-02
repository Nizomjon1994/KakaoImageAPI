package com.everything4droid.kakaoimage.presentation.di.module

import com.everything4droid.kakaoimage.data.repository.SearchImageRepository
import com.everything4droid.kakaoimage.domain.SearchImageUseCase
import com.everything4droid.kakaoimage.presentation.di.scope.AppScope
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named

/**
 * Created by Khajiev Nizomjon on 03/11/2018.
 */
@Module(includes = [RepoModule::class])
class UseCaseModule {

    @AppScope
    @Provides
    @Named("io")
    fun providesIoScheduler(): Scheduler = Schedulers.io()


    @AppScope
    @Provides
    @Named("main")
    fun providesMainThreadScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @AppScope
    @Provides
    fun providesSearchImageUseCaseRx(
        @Named("io") ioScheduler: Scheduler,
        @Named("main") mainThreadScheduler: Scheduler,
        searchImageRepository: SearchImageRepository
    ): SearchImageUseCase {
        return SearchImageUseCase(searchImageRepository, ioScheduler, mainThreadScheduler)
    }

}