package com.madcamp.week1

import android.app.Activity
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
    checkValid()
  }

  private fun checkValid() {
    val sharedPref =
        this@StartActivity.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
    val name = sharedPref.getString("name", "")!!
    val password = sharedPref.getString("password", "")!!
    if (name === "" && password === "") {
      val intent = Intent(this@StartActivity, LoginActivity::class.java)
      startActivity(intent)
      finish()
    } else {
      ServerApiClass.valid(
          name,
          password,
          object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
              if (response.isSuccessful && (response.body()!!)) {
                moveActivity(MainActivity::class.java)
              } else {
                moveActivity(LoginActivity::class.java)
              }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
              moveActivity(LoginActivity::class.java)
            }
          })
    }
  }

  fun <T : Activity> moveActivity(targetActivity: Class<T>) {
    val fadeOut = AlphaAnimation(1f, 0f)
    fadeOut.interpolator = AccelerateInterpolator()
    fadeOut.startOffset = 0
    fadeOut.duration = 900
    fadeOut.setAnimationListener(
        object : AnimationListener {
          override fun onAnimationStart(animation: Animation) {}
          override fun onAnimationRepeat(animation: Animation) {}
          override fun onAnimationEnd(animation: Animation) {
            binding.lottieAnim.visibility = View.GONE
          }
        })
    binding.lottieLayout.startAnimation(fadeOut)
    Handler(Looper.getMainLooper())
        .postDelayed(
            {
              val intent = Intent(this@StartActivity, targetActivity)
              startActivity(intent)
              overridePendingTransition(R.anim.hold, R.anim.fade_out)
              finish()
            },
            1000)
  }
}
