package com.madcamp.week1.ui.dashboard.api

import retrofit2.Call
import retrofit2.http.GET

interface APIInterface {
    @GET("users/jeukhwang")
    fun getGithubUser(
    ): Call<GithubUserData>
}
