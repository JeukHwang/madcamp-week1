package com.madcamp.week1.ui.dashboard

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiObject {
    private const val baseUrl = "https://api.upbit.com/"

    private val getRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val getRetrofitService: APIInterface by lazy {
        getRetrofit.create(APIInterface::class.java)
    }
}