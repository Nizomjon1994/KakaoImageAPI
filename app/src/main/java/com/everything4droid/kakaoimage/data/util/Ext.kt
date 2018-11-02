package com.everything4droid.kakaoimage.data.util

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import com.everything4droid.kakaoimage.data.response.Result
import io.reactivex.Single
import io.reactivex.observers.DisposableSingleObserver
import retrofit2.HttpException
import java.io.IOException
import java.util.logging.Level
import java.util.logging.Logger

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

fun <T : Any> Single<T>.retrofitResponseToResult(): Single<Result<T>> {
    return this.map { it.asResult() }
        .onErrorReturn {
            return@onErrorReturn it.asErrorResult<T>()
        }
}

fun <T : Any> T.asResult(): Result<T> {
    return Result.Success(this)
}

fun <T : Any> Throwable.asErrorResult(): Result<T> {

    return when (this) {
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

open class DefaultSingleObserver<T> : DisposableSingleObserver<T>() {

    private val logger: Logger = Logger.getLogger("DefaultSingleObserver")

    override fun onSuccess(model: T) {}

    override fun onError(error: Throwable) {
        logger.log(Level.WARNING, error.message + " | \n" + error.stackTrace)
    }

}