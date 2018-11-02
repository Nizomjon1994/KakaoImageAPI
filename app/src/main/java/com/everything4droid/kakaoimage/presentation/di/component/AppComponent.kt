package com.everything4droid.kakaoimage.presentation.di.component

import com.everything4droid.kakaoimage.App
import com.everything4droid.kakaoimage.presentation.di.module.BuildersModule
import com.everything4droid.kakaoimage.presentation.di.module.RepoModule
import com.everything4droid.kakaoimage.presentation.di.module.UseCaseModule
import com.everything4droid.kakaoimage.presentation.di.scope.AppScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

/**
 * Created by Khajiev Nizomjon on 03/11/2018.
 */

@AppScope
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        UseCaseModule::class,
        RepoModule::class,
        BuildersModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: App): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)

}