package ru.androidschool.intensiv.utils.extensions

import android.view.View
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.applySchedulers(
    subscribeOnScheduler: Scheduler = Schedulers.io(),
    observeOnScheduler: Scheduler = AndroidSchedulers.mainThread()
): Single<T> {
    return this.subscribeOn(subscribeOnScheduler)
        .observeOn(observeOnScheduler)
}

fun Completable.applySchedulers(
    subscribeOnScheduler: Scheduler = Schedulers.io(),
    observeOnScheduler: Scheduler = AndroidSchedulers.mainThread()
): Completable {
    return this.subscribeOn(subscribeOnScheduler)
        .observeOn(observeOnScheduler)
}

fun <T> Maybe<T>.applySchedulers(
    subscribeOnScheduler: Scheduler = Schedulers.io(),
    observeOnScheduler: Scheduler = AndroidSchedulers.mainThread()
): Maybe<T> {
    return this.subscribeOn(subscribeOnScheduler)
        .observeOn(observeOnScheduler)
}

fun <T> Observable<T>.applySchedulers(
    subscribeOnScheduler: Scheduler = Schedulers.io(),
    observeOnScheduler: Scheduler = AndroidSchedulers.mainThread()
): Observable<T> {
    return this.subscribeOn(subscribeOnScheduler)
        .observeOn(observeOnScheduler)
}

fun <T> Single<T>.applyLoader(
    view: View
): Single<T> {
    return this.doOnSubscribe { view.visibility = View.VISIBLE }
        .doFinally { view.visibility = View.GONE }
}

fun <T> Observable<T>.applyLoader(
    view: View
): Observable<T> {
    return this.doOnSubscribe {
        view.visibility = View.VISIBLE
    }
        .doOnNext { view.visibility = View.GONE }
}

fun <T> Maybe<T>.applyLoader(
    view: View
): Maybe<T> {
    return this.doOnSubscribe { view.visibility = View.VISIBLE }
        .doFinally { view.visibility = View.GONE }
}
