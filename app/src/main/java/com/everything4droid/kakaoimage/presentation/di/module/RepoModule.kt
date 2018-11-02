package com.everything4droid.kakaoimage.presentation.di.module

import com.everything4droid.kakaoimage.data.datasourse.KakaoDataSource
import com.everything4droid.kakaoimage.data.repository.SearchImageRepository
import com.everything4droid.kakaoimage.presentation.di.scope.AppScope
import dagger.Module
import dagger.Provides

/**
 * Created by Khajiev Nizomjon on 03/11/2018.
 */
@Module(includes = [NetModule::class])
class RepoModule {

    @AppScope
    @Provides
    fun providesSearchImageRepo(dataSource: KakaoDataSource): SearchImageRepository {
        return SearchImageRepository(dataSource)
    }
}