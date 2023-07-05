package com.madcamp.week1

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.madcamp.week1.databinding.ActivityGalleryEditBinding
import com.madcamp.week1.profile.UserProfile

class GalleryEditActivity : AppCompatActivity() {
  private lateinit var binding: ActivityGalleryEditBinding
  private lateinit var userProfile: UserProfile
  private var photoUri: Uri? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityGalleryEditBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)
  }
  //
  //  private fun editUser(photoPath: String) {
  //    val sharedPref =
  //        this@ProfileEditActivity.getSharedPreferences(
  //            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
  //    val password = sharedPref.getString("password", "")!!
  //    ServerApiClass.update(
  //        binding.editName.text.toString(),
  //        password,
  //        binding.editEmail.text.toString(),
  //        photoPath,
  //        binding.editGithub.text.toString(),
  //        binding.editInstagram.text.toString(),
  //        binding.editLinkedin.text.toString(),
  //        binding.editExplanation.text.toString(),
  //        object : Callback<UserProfile> {
  //          override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {
  //            if (response.isSuccessful) {
  //              val userProfile = response.body()!!
  //              val intent = Intent(this@ProfileEditActivity, MainActivity::class.java)
  //              intent.putExtra("profile", userProfile)
  //              setResult(RESULT_OK, intent)
  //              Log.e("PLZPLZPLZ2", response.errorBody().toString())
  //              finish()
  //              Toast.makeText(this@ProfileEditActivity, "프로필 수정 완료", Toast.LENGTH_SHORT).show()
  //            } else {
  //              Log.e("PLZPLZPLZ", response.errorBody()!!.string())
  //              Toast.makeText(this@ProfileEditActivity, R.string.onSemiFailure,
  // Toast.LENGTH_SHORT)
  //                  .show()
  //            }
  //          }
  //
  //          override fun onFailure(call: Call<UserProfile>, t: Throwable) {
  //            Toast.makeText(this@ProfileEditActivity, R.string.onFailure,
  // Toast.LENGTH_SHORT).show()
  //          }
  //        })
  //  }
  //
  //  fun setUserProfile(userProfile: UserProfile) {
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
  //
  //  fun uploadImageAndEditUser() {
  //    val mimetype =
  //        MimeTypeMap.getSingleton()
  //            .getExtensionFromMimeType(
  //                this@ProfileEditActivity.contentResolver.getType(photoUri!!))!!
  //    ServerApiClass.uploadImage(
  //        this@ProfileEditActivity,
  //        photoUri!!,
  //        mimetype,
  //        object : Callback<ImageUploadData> {
  //          override fun onResponse(
  //              call: Call<ImageUploadData>,
  //              response: Response<ImageUploadData>
  //          ) {
  //            if (response.isSuccessful) {
  //              val photoPath =
  //
  // "https://community-nest.s3.ap-northeast-2.amazonaws.com/${response.body()?.key}"
  //              editUser(photoPath)
  //              Log.e("WOW", photoPath)
  //            } else {
  //              Toast.makeText(
  //                      this@ProfileEditActivity,
  //                      "이미지 업로드 실패 Semi\n${R.string.onSemiFailure}",
  //                      Toast.LENGTH_LONG)
  //                  .show()
  //            }
  //            Log.e("WOW2", response.body()?.toString()!!)
  //            Log.e("WOW3", response.errorBody()?.string()!!)
  //          }
  //
  //          override fun onFailure(call: Call<ImageUploadData>, t: Throwable) {
  //            // 현 상황
  //            Log.e("UPLOAD", t.message!!)
  //            Toast.makeText(
  //                    this@ProfileEditActivity,
  //                    "이미지 업로드 실패\n${R.string.onFailure}",
  //                    Toast.LENGTH_LONG)
  //                .show()
  //          }
  //        })
  //  }
}
