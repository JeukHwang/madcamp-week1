package com.madcamp.week1.profile

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GithubApiObject {
  private const val baseUrl = "https://api.github.com/"

  private val getRetrofit by lazy {
    Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build()
  }

  val getRetrofitService: GithubAPIInterface by lazy { getRetrofit.create(GithubAPIInterface::class.java) }
}
