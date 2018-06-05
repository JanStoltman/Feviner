package com.yggdralisk.feviner.api.calls

import com.yggdralisk.feviner.api.ApiController
import com.yggdralisk.feviner.api.interfaces.NumbersApiInterface
import com.yggdralisk.feviner.models.NumberModel
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Jan Stoltman on 6/1/18.
 */
class NumbersServiceCalls {
    companion object {
        fun getAllNumbers(): Single<List<NumberModel>> = ApiController.createService(NumbersApiInterface::class.java)
                .getAllNumbers()

        fun getRandomNumber(): Single<NumberModel> = ApiController.createService(NumbersApiInterface::class.java)
                .getRandomNumber()

        fun getManyRandomNumbers(): Single<List<NumberModel>> = ApiController.createService(NumbersApiInterface::class.java)
                .getManyRandomNumbers()

        fun getManyRandomNumbers(fetchSize: Int): Single<List<NumberModel>> = ApiController.createService(NumbersApiInterface::class.java)
                .getManyRandomNumbers(fetchSize)
    }
}