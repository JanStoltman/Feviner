package com.yggdralisk.feviner.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Jan Stoltman on 6/2/18.
 */
class ApiController{
    companion object {
        private const val BASE_URL = "http://yggdralisk.pythonanywhere.com/"

        private val builder: Retrofit.Builder = Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

        private var retrofit = builder.build()

        private val logging: HttpLoggingInterceptor =
                HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY)

        private val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()

        fun <S> createService(serviceClass: Class<S>): S {
            if (!httpClient.interceptors().contains(logging)) {
                httpClient.addInterceptor(logging)
                builder.client(httpClient.build())
                retrofit = builder.build()
            }
            return retrofit.create(serviceClass)
        }
    }
}