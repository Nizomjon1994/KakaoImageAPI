package com.everything4droid.kakaoimage

import android.app.Activity
import android.app.Application
import com.everything4droid.kakaoimage.presentation.di.component.AppComponent
import com.everything4droid.kakaoimage.presentation.di.component.DaggerAppComponent
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Created by Khajiev Nizomjon on 29/10/2018.
 */
class App : Application(),HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    private val applicationComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .application(this)
            .build()
    }

    fun inject() {
        applicationComponent.inject(this)
    }

    override fun onCreate() {
        super.onCreate()
        inject()
        Fresco.initialize(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }
}