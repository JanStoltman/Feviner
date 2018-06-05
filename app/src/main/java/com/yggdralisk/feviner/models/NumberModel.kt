package com.yggdralisk.feviner.models

import android.text.Editable
import com.google.gson.annotations.SerializedName
import com.yggdralisk.feviner.toIntOrNull

/**
 * Created by Jan Stoltman on 6/1/18.
 */
data class NumberModel(
        @SerializedName("arabic_num") val arabicNumber: Int,
        @SerializedName("latin_num") val latinNumber: String
) {
    fun validateAnswer(answer: Editable, isAnswerAsNumModeActive: Boolean): Boolean = if (isAnswerAsNumModeActive) {
        arabicNumber == answer.toIntOrNull()
    } else {
        latinNumber.toLowerCase() == answer.toString().toLowerCase()
    }

    fun getText(answerAsNumModeActive: Boolean): String = if (answerAsNumModeActive) arabicNumber.toString() else latinNumber
}