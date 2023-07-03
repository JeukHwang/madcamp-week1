package com.madcamp.week1.profile

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface GithubAPIInterface {
  @GET fun getGithubUser(@Url url: String): Call<GithubUserData>
}
