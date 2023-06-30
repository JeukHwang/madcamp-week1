package com.madcamp.week1.ui.notifications.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface APIInterface {
    @GET
    fun getGithubUser(@Url url: String): Call<GithubUserData>
}
