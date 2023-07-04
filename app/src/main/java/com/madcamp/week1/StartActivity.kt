package com.madcamp.week1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import androidx.appcompat.app.AppCompatActivity
import com.madcamp.week1.databinding.ActivityStartBinding
import com.madcamp.week1.profile.ServerApiClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StartActivity : AppCompatActivity() {
  private lateinit var binding: ActivityStartBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityStartBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)
    val fadeOut = AlphaAnimation(1f, 0f)
    fadeOut.interpolator = AccelerateInterpolator() // and this
    fadeOut.startOffset = 500
    fadeOut.duration = 500
    fadeOut.setAnimationListener(
        object : AnimationListener {
          override fun onAnimationStart(animation: Animation) {}
          override fun onAnimationRepeat(animation: Animation) {}
          override fun onAnimationEnd(animation: Animation) {
            binding.lottieAnim.visibility = View.GONE
          }
        })
    binding.lottieLayout.animation = fadeOut

    Handler(Looper.getMainLooper()).postDelayed({ checkValid() }, 1000)
  }

  private fun checkValid() {
    val sharedPref =
        this@StartActivity.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
    val name = sharedPref.getString("name", "")!!
    val password = sharedPref.getString("password", "")!!
    if (name === "" && password === "") {
      val intent = Intent(this@StartActivity, MainActivity::class.java)
      startActivity(intent)
      finish()
    } else {
      ServerApiClass.valid(
          name,
          password,
          object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
              if (response.isSuccessful && (response.body()!!)) {
                //              Toast.makeText(this@StartActivity, "자동 로그인 성공",
                // Toast.LENGTH_SHORT).show()
                val intent = Intent(this@StartActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
              } else {
                //              Toast.makeText(this@StartActivity, "자동 로그인 실패",
                // Toast.LENGTH_SHORT).show()
                val intent = Intent(this@StartActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
              }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
              //            Toast.makeText(this@StartActivity, "자동 로그인 실패",
              // Toast.LENGTH_SHORT).show()
              val intent = Intent(this@StartActivity, MainActivity::class.java)
              startActivity(intent)
              finish()
            }
          })
    }
  }
}
