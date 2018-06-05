package com.yggdralisk.feviner

import android.text.Editable

/**
 * Created by Jan Stoltman on 6/5/18.
 */
fun Int?.liesBetween(bottom:Int, top: Int): Boolean = this != null && this in (bottom + 1)..(top - 1)

fun Editable.toIntOrNull(): Int? = this.toString().toIntOrNull()
