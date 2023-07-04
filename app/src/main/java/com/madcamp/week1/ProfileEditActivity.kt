package com.madcamp.week1

import android.content.Context
import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.madcamp.week1.databinding.ActivityProfileEditBinding
import com.madcamp.week1.profile.ServerApiClass
import com.madcamp.week1.profile.UserProfile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// https://stackoverflow.com/questions/73019160/android-getparcelableextra-deprecated
inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? =
    when {
      SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
      else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
    }

class ProfileEditActivity : AppCompatActivity() {
  private lateinit var binding: ActivityProfileEditBinding
  private lateinit var userProfile: UserProfile

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityProfileEditBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)
    userProfile = intent.parcelable<UserProfile>("profile")!!
    setUserProfile(userProfile)
    binding.toolbar.setNavigationOnClickListener {
      MaterialAlertDialogBuilder(this@ProfileEditActivity)
          .setTitle(resources.getString(R.string.title2))
          .setMessage(resources.getString(R.string.supporting_text2))
          .setNegativeButton(resources.getString(R.string.decline)) { dialog, which -> }
          .setPositiveButton(resources.getString(R.string.accept2)) { dialog, which ->
            this@ProfileEditActivity.finish()
          }
          .show()
    }
    binding.filledButton.setOnClickListener { editUser() }
  }

  private fun editUser() {
    val sharedPref =
        this@ProfileEditActivity.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
    val password = sharedPref.getString("password", "")!!
    ServerApiClass.update(
        binding.editName.text.toString(),
        password,
        binding.editEmail.text.toString(),
        userProfile.profilePhoto,
        binding.editGithub.text.toString(),
        binding.editInstagram.text.toString(),
        binding.editLinkedin.text.toString(),
        binding.editExplanation.text.toString(),
        object : Callback<UserProfile> {
          override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {
            if (response.isSuccessful) {
              val userProfile = response.body()!!
              val intent = Intent(this@ProfileEditActivity, MainActivity::class.java)
              intent.putExtra("profile", userProfile)
              setResult(RESULT_OK, intent)
              Log.e("PLZPLZPLZ2", response.errorBody().toString())
              finish()
              Toast.makeText(this@ProfileEditActivity, "프로필 수정 완료", Toast.LENGTH_SHORT).show()
            } else {
              Log.e("PLZPLZPLZ", response.errorBody()!!.string())
              Toast.makeText(this@ProfileEditActivity, R.string.onSemiFailure, Toast.LENGTH_SHORT)
                  .show()
            }
          }

          override fun onFailure(call: Call<UserProfile>, t: Throwable) {
            Toast.makeText(this@ProfileEditActivity, R.string.onFailure, Toast.LENGTH_SHORT).show()
          }
        })
  }

  fun setUserProfile(userProfile: UserProfile) {
    binding.profilePhotoEdit.load(userProfile.profilePhoto) {
      placeholder(R.drawable.baseline_person_24)
      error(R.drawable.outline_error_outline_24)
      transformations(CircleCropTransformation())
      crossfade(500)
    }
    binding.textFieldName.editText?.setText(userProfile.name)
    binding.textFieldClass.editText?.setText("${userProfile.classNum}분반")
    binding.textFieldExplanation.editText?.setText(userProfile.explanation)
    binding.textFieldEmail.editText?.setText(userProfile.email)
    binding.textFieldGithubId.editText?.setText(userProfile.githubId)
    binding.textFieldInstagramId.editText?.setText(userProfile.instagramId)
    binding.textFieldLinkedinId.editText?.setText(userProfile.linkedInId)
  }
}
