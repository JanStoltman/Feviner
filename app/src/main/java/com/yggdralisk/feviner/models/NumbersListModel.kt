package com.yggdralisk.feviner.models

import io.reactivex.subjects.ReplaySubject

/**
 * Created by Jan Stoltman on 6/5/18.
 * Wrapper for ArrayList, it takes care of providing random elements
 * and notifies observers about it's size changes
 */
class NumbersListModel(private val numbers: ArrayList<NumberModel>){
    fun getRandomNumber(): NumberModel?{
        val num = numbers.shuffled().take(minOf(1,numbers.size)).firstOrNull()
        numbers.remove(num)
        sizeObservable.onNext(numbers.size)
        return num
    }

    fun getRandomNumbers(size: Int):List<NumberModel>{
        val nums = numbers.shuffled().take(minOf(size,numbers.size))
        numbers.removeAll(nums)
        sizeObservable.onNext(numbers.size)
        return nums
    }

    fun appendNumbers(newNumbers: List<NumberModel>){
        numbers.addAll(newNumbers)
        sizeObservable.onNext(numbers.size)
    }

    fun isNotEmpty():Boolean = numbers.isNotEmpty()

    private val sizeObservable: ReplaySubject<Int> = ReplaySubject.create<Int>()
}