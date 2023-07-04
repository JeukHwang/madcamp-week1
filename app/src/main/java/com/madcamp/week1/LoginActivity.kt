package com.madcamp.week1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.madcamp.week1.databinding.ActivityLoginBinding
import com.madcamp.week1.profile.ServerApiClass
import com.madcamp.week1.profile.SignupData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : FragmentActivity() {
  private lateinit var binding: ActivityLoginBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityLoginBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)

    (binding.textFieldClass.editText as? MaterialAutoCompleteTextView)?.setSimpleItems(
        arrayOf("1분반", "2분반", "3분반", "4분반"))

    binding.loginButton.setOnClickListener {
      val name = binding.textFieldName.editText?.text.toString()
      val classStr = binding.textFieldClass.editText?.text.toString()
      val password = binding.textFieldPassword.editText?.text.toString()
      if (name.isNotBlank() && classStr.isNotBlank() && password.isNotBlank()) {
        tryValid(name, password, classStr)
      } else {
        Toast.makeText(this@LoginActivity, "모든 칸을 입력해주세요", Toast.LENGTH_SHORT).show()
      }
    }
  }

  private fun tryValid(name: String, password: String, classStr: String) {
    ServerApiClass.valid(
        name,
        password,
        object : Callback<Boolean> {
          override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
            if (response.isSuccessful && (response.body()!!)) {
              val sharedPref =
                  this@LoginActivity.getSharedPreferences(
                      getString(R.string.preference_file_key), Context.MODE_PRIVATE)
              with(sharedPref.edit()) {
                putString("name", name)
                putString("password", password)
                apply()
              }
              val intent = Intent(this@LoginActivity, MainActivity::class.java)
              startActivity(intent)
              finish()
            } else {
              trySignup(name, password, classStr)
            }
          }

          override fun onFailure(call: Call<Boolean>, t: Throwable) {
            trySignup(name, password, classStr)
          }
        })
  }

  fun trySignup(name: String, password: String, classStr: String) {
    ServerApiClass.signup(
        name,
        password,
        classStr,
        object : Callback<SignupData> {
          override fun onResponse(call: Call<SignupData>, response: Response<SignupData>) {
            if (response.isSuccessful && (response.body()?.name?.isNotBlank() == true)) {
              val sharedPref =
                  this@LoginActivity.getSharedPreferences(
                      getString(R.string.preference_file_key), Context.MODE_PRIVATE)
              with(sharedPref.edit()) {
                putString("name", name)
                putString("password", password)
                apply()
              }
              val intent = Intent(this@LoginActivity, MainActivity::class.java)
              startActivity(intent)
              finish()
            } else {
              Toast.makeText(this@LoginActivity, "오류가 있으니 개발자에게 알려주세요", Toast.LENGTH_SHORT).show()
            }
          }

          override fun onFailure(call: Call<SignupData>, t: Throwable) {
            Toast.makeText(this@LoginActivity, "오류가 있으니 개발자에게 알려주세요", Toast.LENGTH_SHORT).show()
          }
        })
  }
}
