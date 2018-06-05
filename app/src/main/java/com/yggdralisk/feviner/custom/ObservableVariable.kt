package com.yggdralisk.feviner.custom

import io.reactivex.subjects.ReplaySubject

/**
 * Created by Jan Stoltman on 6/5/18.
 * Simble ObservableVariable which calls all subscribed observers on
 * value reassignment
 */
class ObservableVariable<T>(private val initValue: T) {
    var value: T = initValue
        set(value) {
            field = value
            if (value != null) observable.onNext(value)
        }

    val observable = ReplaySubject.create<T>()
}