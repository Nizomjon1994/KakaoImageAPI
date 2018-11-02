package com.everything4droid.kakaoimage.data.util

import com.everything4droid.kakaoimage.data.response.Result
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

abstract class AbsRxSingleUseCase<T : Any, in Params> protected constructor(
        private val threadExecutor: Scheduler,
        private val postExecutionThread: Scheduler) {

    private val disposables: CompositeDisposable = CompositeDisposable()

    internal abstract fun createSingle(params: Params? = null): Single<T>

    fun execute(observer: DisposableSingleObserver<Result<T>>, params: Params? = null) {
        addDisposable(this.createSingle(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retrofitResponseToResult()
                .subscribeWith(observer))

    }

    internal fun execute(params: Params): Single<T> {
        return createSingle(params)
    }

    fun dispose() {
        if (!disposables.isDisposed) {
            disposables.clear()
        }
    }


    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }
}
