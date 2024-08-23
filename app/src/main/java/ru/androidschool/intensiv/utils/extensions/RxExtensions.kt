package ru.androidschool.intensiv.utils.extensions

import android.view.View
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

fun <T> Single<T>.applyLoader(
    view: View
): Single<T> {
    return this.doOnSubscribe { view.visibility = View.VISIBLE }
        .doFinally { view.visibility = View.GONE }
}
