package com.yggdralisk.feviner.api.interfaces

import com.yggdralisk.feviner.models.NumberModel
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by Jan Stoltman on 6/1/18.
 */
interface NumbersApiInterface {
    @GET("api/numbers/")
    fun getAllNumbers(): Single<List<NumberModel>>

    @GET("api/random-number/")
    fun getRandomNumber(): Single<NumberModel>

    /**
     * Returns list of random numbers of size equal to min(50, listOfNumsInDatabase)
     * Equivalent of getManyRandomNumbers(50)
     */
    @GET("api/many-random-numbers/")
    fun getManyRandomNumbers(): Single<List<NumberModel>>

    /**
     * Returns list of random numbers of size equal to min(fetchSize, listOfNumsInDatabase)
     */
    @POST("api/many-random-numbers/")
    @FormUrlEncoded
    fun getManyRandomNumbers(@Field("size") fetchSize: Int): Single<List<NumberModel>>
}