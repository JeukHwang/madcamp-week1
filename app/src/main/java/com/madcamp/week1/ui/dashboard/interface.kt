package com.madcamp.week1.ui.dashboard

import retrofit2.Call
import retrofit2.http.GET

interface APIInterface {
    @GET("v1/market/all")
    fun getCoinAll(
    ): Call<List<Coin>>
}