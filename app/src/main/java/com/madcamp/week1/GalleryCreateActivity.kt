package com.madcamp.week1

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.madcamp.week1.databinding.ActivityGalleryCreateBinding
import com.madcamp.week1.profile.FeedProfile
import com.madcamp.week1.profile.ImageUploadData
import com.madcamp.week1.profile.ServerApiClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GalleryCreateActivity : AppCompatActivity() {
  private lateinit var binding: ActivityGalleryCreateBinding
  private lateinit var feedProfile: FeedProfile
  private var photoUri: Uri? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityGalleryCreateBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)

    binding.toolbar.setNavigationOnClickListener {
      MaterialAlertDialogBuilder(this@GalleryCreateActivity)
          .setTitle(resources.getString(R.string.title3))
          .setMessage(resources.getString(R.string.supporting_text3))
          .setNegativeButton(resources.getString(R.string.decline)) { dialog, which -> }
          .setPositiveButton(resources.getString(R.string.accept2)) { dialog, which ->
            this@GalleryCreateActivity.finish()
          }
          .show()
    }
    binding.filledButton.setOnClickListener {
      if (photoUri === null) {
        Toast.makeText(this@GalleryCreateActivity, "사진을 골라주세요", Toast.LENGTH_SHORT).show()
      } else {
        uploadImageAndEditUser()
      }
    }
    val getResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
          if (it.resultCode == RESULT_OK) {
            photoUri = it.data?.data
            binding.profilePhotoEdit.load(photoUri) {
              placeholder(R.drawable.baseline_insert_photo_24)
              error(R.drawable.baseline_insert_photo_24)
              crossfade(500)
            }
          }
        }
    binding.profilePhotoEdit.setOnClickListener {
      val intent = Intent(Intent.ACTION_GET_CONTENT)
      intent.type = "image/*"
      getResult.launch(intent)
    }
  }

  private fun editUser(photoPath: String) {
    val sharedPref =
        this@GalleryCreateActivity.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
    val name = sharedPref.getString("name", "")!!
    val password = sharedPref.getString("password", "")!!
    val users = binding.editClass.text.toString().split(" ").toTypedArray()
    ServerApiClass.createFeed(
        name,
        password,
        photoPath,
        binding.editContent.text.toString(),
        users,
        object : Callback<FeedProfile> {
          override fun onResponse(call: Call<FeedProfile>, response: Response<FeedProfile>) {
            if (response.isSuccessful) {
              val feedProfile = response.body()!!
              val intent = Intent(this@GalleryCreateActivity, MainActivity::class.java)
              //              intent.putExtra("profile", userProfile)
              //              setResult(RESULT_OK, intent)
              //              Log.e("PLZPLZPLZ2", response.errorBody().toString())
              finish()
              Toast.makeText(this@GalleryCreateActivity, "피드 작성 완료", Toast.LENGTH_SHORT).show()
            } else {
              //              Log.e("PLZPLZPLZ", response.errorBody()!!.string())
              Toast.makeText(this@GalleryCreateActivity, R.string.onSemiFailure, Toast.LENGTH_SHORT)
                  .show()
            }
          }

          override fun onFailure(call: Call<FeedProfile>, t: Throwable) {
            Toast.makeText(this@GalleryCreateActivity, R.string.onFailure, Toast.LENGTH_SHORT)
                .show()
          }
        })
  }
  //
  //  fun setFeedProfile(userProfile: UserProfile) {
  //    binding.profilePhotoEdit.load(userProfile.profilePhoto) {
  //      placeholder(R.drawable.baseline_person_24)
  //      error(R.drawable.baseline_person_24)
  //      transformations(CircleCropTransformation())
  //      crossfade(500)
  //    }
  //    binding.textFieldName.editText?.setText(userProfile.name)
  //    binding.textFieldClass.editText?.setText("${userProfile.classNum}분반")
  //    binding.textFieldExplanation.editText?.setText(userProfile.explanation)
  //    binding.textFieldEmail.editText?.setText(userProfile.email)
  //    binding.textFieldGithubId.editText?.setText(userProfile.githubId)
  //    binding.textFieldInstagramId.editText?.setText(userProfile.instagramId)
  //    binding.textFieldLinkedinId.editText?.setText(userProfile.linkedInId)
  //  }

  fun uploadImageAndEditUser() {
    val mimetype =
        MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(
                this@GalleryCreateActivity.contentResolver.getType(photoUri!!))!!
    ServerApiClass.uploadImage(
        this@GalleryCreateActivity,
        photoUri!!,
        mimetype,
        object : Callback<ImageUploadData> {
          override fun onResponse(
              call: Call<ImageUploadData>,
              response: Response<ImageUploadData>
          ) {
            if (response.isSuccessful) {
              val photoPath =
                  "https://community-nest.s3.ap-northeast-2.amazonaws.com/${response.body()?.key}"
              editUser(photoPath)
              Log.e("WOW", photoPath)
            } else {
              Toast.makeText(
                      this@GalleryCreateActivity,
                      "이미지 업로드 실패 Semi\n${R.string.onSemiFailure}",
                      Toast.LENGTH_LONG)
                  .show()
            }
            //            Log.e("WOW2", response.body()?.toString()!!)
            //            Log.e("WOW3", response.errorBody()?.string()!!)
          }

          override fun onFailure(call: Call<ImageUploadData>, t: Throwable) {
            // 현 상황
            Log.e("UPLOAD", t.message!!)
            Toast.makeText(
                    this@GalleryCreateActivity,
                    "이미지 업로드 실패\n${R.string.onFailure}",
                    Toast.LENGTH_LONG)
                .show()
          }
        })
  }
}
