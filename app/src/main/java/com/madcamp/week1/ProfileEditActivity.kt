package com.madcamp.week1

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.madcamp.week1.databinding.ActivityProfileEditBinding
import com.madcamp.week1.profile.ServerApiClass
import com.madcamp.week1.profile.UpdateData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileEditActivity : AppCompatActivity() {
  private lateinit var binding: ActivityProfileEditBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityProfileEditBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)
    binding.toolbar.setNavigationOnClickListener { finish() }

    binding.filledButton.setOnClickListener { editUser() }

    val sharedPref =
        this@ProfileEditActivity.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
    with(sharedPref.edit()) {
      putInt("settings", sharedPref.getInt("settings", -1) + 1)
      apply()
    }
    Toast.makeText(
            this@ProfileEditActivity,
            sharedPref.getInt("settings", -1).toString(),
            Toast.LENGTH_SHORT)
        .show()
  }

  private fun editUser() {
    ServerApiClass.update(
        binding.editName.text.toString(),
        binding.editEmail.text.toString(),
        "",
        binding.editGithub.text.toString(),
        binding.editInstagram.text.toString(),
        binding.editLinkedin.text.toString(),
        binding.editExplanation.text.toString(),
        object : Callback<UpdateData> {
          override fun onResponse(call: Call<UpdateData>, response: Response<UpdateData>) {
            if (response.isSuccessful) {
              val data = response.body()!!
              Log.i("Retrofit2", data.toString())
              Toast.makeText(this@ProfileEditActivity, "프로필 수정 완료", Toast.LENGTH_SHORT).show()
            } else {
              Log.i("Retrofit2 - editUser", response.toString())
              Log.i("Retrofit2 - editUser", response.errorBody().toString())
              Toast.makeText(this@ProfileEditActivity, "Edit User Error2", Toast.LENGTH_SHORT)
                  .show()
            }
          }

          override fun onFailure(call: Call<UpdateData>, t: Throwable) {
            Toast.makeText(this@ProfileEditActivity, "Edit User Error", Toast.LENGTH_SHORT).show()
          }
        })
  }
}
