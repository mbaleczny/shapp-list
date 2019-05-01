package com.mbaleczny.shapp_list.util

/**
 * @author Mariusz Baleczny
 * @date 02/05/19
 */

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe

fun Completable.applySchedulers(provider: SchedulerProvider): Completable =
    this.subscribeOn(provider.io()).observeOn(provider.ui())

fun <T> Flowable<T>.applySchedulers(provider: SchedulerProvider): Flowable<T> =
    this.subscribeOn(provider.io()).observeOn(provider.ui())

fun <T> Maybe<T>.applySchedulers(provider: SchedulerProvider): Maybe<T> =
    this.subscribeOn(provider.io()).observeOn(provider.ui())
