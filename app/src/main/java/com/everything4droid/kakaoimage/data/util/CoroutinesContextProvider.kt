package com.everything4droid.kakaoimage.data.util

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.CoroutineDispatcher
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.IO
import kotlinx.coroutines.experimental.android.Main
import kotlinx.coroutines.experimental.android.UI

/**
 * Created by Khajiev Nizomjon on 29/10/2018.
 */
data class CoroutinesContextProvider(
    val main: CoroutineDispatcher = Dispatchers.Main,
    val computation: CoroutineDispatcher = Dispatchers.Default,
    val io: CoroutineDispatcher = Dispatchers.IO
)