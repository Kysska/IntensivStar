package ru.androidschool.intensiv.data.repository

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import timber.log.Timber

abstract class BaseRepository<T>() {

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

    protected fun <R> fetchData(
        call: () -> Observable<R>,
        mapper: (R) -> T,
        tag: String
    ): Observable<T> {
        return call()
            .map { response ->
                mapper(response)
            }
            .doOnError { throwable ->
                Timber.tag(tag).e(throwable)
            }
    }

    protected fun performDatabaseOperation(
        daoOperation: () -> Completable,
        operationType: String,
        tag: String
    ): Completable {
        return daoOperation()
            .doOnComplete {
                Timber.tag(tag).d("$operationType completed successfully")
            }
            .doOnError { throwable ->
                Timber.tag(tag).e(throwable, "Error $operationType")
            }
    }
}
