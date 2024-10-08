package ru.androidschool.intensiv.data.network.source

import io.reactivex.Single
import timber.log.Timber

abstract class BaseRemoteDataSource<T>() {
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
