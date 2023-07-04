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
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface ServerAPIInterface {
  @POST("/auth/valid") fun valid(@Body params: RequestBody): Call<Boolean>

  @POST("/auth/signup") fun signup(@Body params: RequestBody): Call<UserProfile>
  @POST("/user/update") fun update(@Body params: RequestBody): Call<UserProfile>
  @GET fun getOne(@Url name: String): Call<UserProfile>
  @GET("/user/all") fun getAll(): Call<Array<UserProfile>>
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

    fun signup(name: String, password: String, classStr: String, callback: Callback<UserProfile>) {
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
        password: String,
        email: String,
        profilePhoto: String,
        githubId: String,
        instagramId: String,
        linkedInId: String,
        explanation: String,
        callback: Callback<UserProfile>
    ) {
      val param = arrayListOf<Pair<String, String>>()
      if (name.isNotBlank()) {
        param.add("name" to name)
      }
      if (password.isNotBlank()) {
        param.add("password" to password)
      }
      if (email.isNotBlank()) {
        param.add("email" to email)
      }
      if (profilePhoto.isNotBlank()) {
        param.add("profilePhoto" to profilePhoto)
      }
      if (githubId.isNotBlank()) {
        param.add("githubId" to githubId)
      }
      if (githubId.isNotBlank()) {
        param.add("githubId" to githubId)
      }
      if (instagramId.isNotBlank()) {
        param.add("instagramId" to instagramId)
      }
      if (linkedInId.isNotBlank()) {
        param.add("linkedInId" to linkedInId)
      }
      if (explanation.isNotBlank()) {
        param.add("explanation" to explanation)
      }
      val body = createJsonRequestBody(*param.toTypedArray())
      val call = getRetrofitService.update(body)
      call.enqueue(callback)
    }

    fun getOne(name: String, callback: Callback<UserProfile>) {
      val call = getRetrofitService.getOne("/user/get/$name")
      call.enqueue(callback)
    }

    fun getAll(callback: Callback<Array<UserProfile>>) {
      val call = getRetrofitService.getAll()
      call.enqueue(callback)
    }
  }
}
