package com.madcamp.week1.profile

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface ServerAPIInterface {
  @POST("/auth/signup") fun signup(@Body params: RequestBody): Call<SignupData>
}

class ServerApiClass {
  companion object {
    private const val baseUrl = "https://madcamp-week1-server-production.up.railway.app/"
    private val getRetrofit by lazy {
      Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build()
    }
    private val getRetrofitService: ServerAPIInterface by lazy {
      getRetrofit.create(ServerAPIInterface::class.java)
    }

    private fun createJsonRequestBody(vararg params: Pair<String, String>) =
        JSONObject(mapOf(*params))
            .toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

    fun signup(name: String, password: String, email: String, callback: Callback<SignupData>) {
      val body = createJsonRequestBody("name" to name, "password" to password, "email" to email)
      val call = getRetrofitService.signup(body)
      call.enqueue(callback)
    }
  }
}
