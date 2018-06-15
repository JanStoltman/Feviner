package com.yggdralisk.feviner

import android.text.Editable
import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

/**
 * Created by Jan Stoltman on 6/5/18.
 */
fun Int?.liesBetween(bottom: Int, top: Int): Boolean = this != null && this in (bottom + 1)..(top - 1)

fun Editable.toIntOrNull(): Int? = this.toString().toIntOrNull()
