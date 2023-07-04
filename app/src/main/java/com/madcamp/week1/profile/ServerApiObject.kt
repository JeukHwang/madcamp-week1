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
  @POST("/auth/valid") fun valid(@Body params: RequestBody): Call<Boolean>

  @POST("/auth/signup") fun signup(@Body params: RequestBody): Call<SignupData>
  @POST("/user/update") fun update(@Body params: RequestBody): Call<UpdateData>
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

    fun valid(name: String, password: String, callback: Callback<Boolean>) {
      val body = createJsonRequestBody("name" to name, "password" to password)
      val call = getRetrofitService.valid(body)
      call.enqueue(callback)
    }

    fun signup(name: String, password: String, classStr: String, callback: Callback<SignupData>) {
      val classNum = classStr[0].digitToInt()
      val body =
          JSONObject(mapOf("name" to name, "password" to password, "classNum" to classNum))
              .toString()
              .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
      val call = getRetrofitService.signup(body)
      call.enqueue(callback)
    }

    fun update(
        name: String,
        email: String,
        profilePhoto: String,
        githubId: String,
        instagramId: String,
        linkedInId: String,
        explanation: String,
        callback: Callback<UpdateData>
    ) {
      val param =
          arrayOf(
              "name" to name,
              "email" to email,
              "profilePhoto" to profilePhoto,
              "githubId" to githubId,
              "instagramId" to instagramId,
              "linkedInId" to linkedInId,
              "explanation" to explanation)
      val body = createJsonRequestBody(*param)
      val call = getRetrofitService.update(body)
      call.enqueue(callback)
    }
  }
}
