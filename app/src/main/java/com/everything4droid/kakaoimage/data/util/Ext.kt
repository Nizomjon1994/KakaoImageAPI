package com.everything4droid.kakaoimage.data.util

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import com.everything4droid.kakaoimage.data.response.Result
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by Khajiev Nizomjon on 29/10/2018.
 */

fun <D : Any, T : LiveData<D>> T.observeWith(owner: LifecycleOwner, receiver: (D) -> Unit) {
    observe(owner, Observer<D> {
        if (it != null) {
            receiver(it)
        }
    })
}


suspend fun <T : Any> safeApiCall(call: suspend () -> Result<T>, errorMessage: String): Result<T> {
    return try {
        call()
    } catch (e: Exception) {
        when (e) {
            is IOException -> {
                Result.Error(ErrorKit(ERROR_STATUS.NETWORK))
            }
            is HttpException -> {
                //  when (e.code()) {
//                    HttpURLConnection.HTTP_BAD_REQUEST -> {
//                        Result.Error(ErrorKit(ERROR_STATUS.BAD_REQUEST))
//                    }
//                    HttpURLConnection.HTTP_INTERNAL_ERROR -> {
//                        Result.Error(ErrorKit(ERROR_STATUS.INTERNAL_SERVER))
//                    }
//                    HttpURLConnection.HTTP_NOT_FOUND -> {
//                        Result.Error(ErrorKit(ERROR_STATUS.NOT_FOUND))
//                    }
//                    else -> {
//                        Result.Error(ErrorKit(ERROR_STATUS.UNKNOWN))
//                    }
//                }
                Result.Error(ErrorKit(ERROR_STATUS.INTERNAL_SERVER))
            }
            else -> {
                Result.Error(ErrorKit(ERROR_STATUS.UNKNOWN))
            }
        }
    }
}