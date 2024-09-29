package ru.androidschool.intensiv.data.local.source

import io.reactivex.Completable
import io.reactivex.Single
import timber.log.Timber

abstract class BaseLocalDataSource<T>() {

    protected fun performOperation(
        dbOperation: () -> Completable,
        tag: String,
        nameOperation: String
    ): Completable {
        return dbOperation()
            .doOnComplete {
                Timber.tag(tag).d("$nameOperation completed successfully")
            }
            .doOnError { throwable ->
                Timber.tag(tag).e(throwable, "Error $nameOperation")
            }
    }

    protected fun <R> fetchData(
        call: () -> Single<R>,
        mapper: (R) -> T,
        emptyResult: T,
        tag: String
    ): Single<T> {
        return call()
            .map { response ->
                mapper(response) ?: emptyResult
            }
            .doOnError { throwable ->
                Timber.tag(tag).e(throwable)
            }
    }
}
