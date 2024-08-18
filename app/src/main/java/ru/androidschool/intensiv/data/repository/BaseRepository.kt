package ru.androidschool.intensiv.data.repository

import io.reactivex.Single
import timber.log.Timber

abstract class BaseRepository<T>() {

    protected fun <R> fetchData(
        apiCall: () -> Single<R>,
        mapper: (R) -> T,
        emptyResult: T
    ): Single<T> {
        return apiCall()
            .map { response ->
                mapper(response) ?: emptyResult
            }
            .doOnError { throwable ->
                Timber.tag(this::class.java.simpleName).e(throwable)
            }
            .onErrorReturn {
                emptyResult
            }
    }
}
