package com.everything4droid.kakaoimage.presentation.di.module

import com.everything4droid.kakaoimage.presentation.MainActivity
import com.everything4droid.kakaoimage.presentation.di.main.ImageModule
import com.everything4droid.kakaoimage.presentation.di.scope.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Khajiev Nizomjon on 03/11/2018.
 */
@Module
abstract class BuildersModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [ImageModule::class])
    abstract fun bindImageModuleForLogin(): MainActivity

}