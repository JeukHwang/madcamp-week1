package com.madcamp.week1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.madcamp.week1.databinding.FragmentDevelopBinding
import com.madcamp.week1.profile.ServerApiClass
import com.madcamp.week1.profile.UserProfile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class DevelopFragment : Fragment() {

  private lateinit var binding: FragmentDevelopBinding
  private lateinit var userProfile: UserProfile

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View {
    binding = FragmentDevelopBinding.inflate(inflater, container, false)
    binding.topAppBar.setOnMenuItemClickListener { menuItem ->
      when (menuItem.itemId) {
        R.id.share -> {
          shareText()
          true
        }

        R.id.refresh -> {
          fetchUser()
          true
        }

        R.id.edit -> {
          //          val resultLauncher =
          //
          // registerForActivityResult(ActivityResultContrer.launch(intent) val resultLauncher =
          ////
          // registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result
          ////                ->
          ////                if (result.resultCode == RESULT_OK) {
          ////                  userProfile = result?.data?.parcelable<UserProfile>("profile")!!
          ////                  updateProfile(userProfile)
          ////                  fetchUser()
          ////                }
          ////              }
          ////          val intent = Intent(this.context, ProfileEditActivity::class.java)
          ////          intent.putExtra("profile", userProfile)
          ////          resultLauncher.launch(intent)
          val intent = Intent(this.context, ProfileEditActivity::class.java)
          intent.putExtra("profile", userProfile)
          startActivity(intent)
          true
        }
        R.id.logout -> {
          MaterialAlertDialogBuilder(requireActivity())
              .setTitle(resources.getString(R.string.title))
              .setMessage(resources.getString(R.string.supporting_text))
              .setNegativeButton(resources.getString(R.string.decline)) { dialog, which -> }
              .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
                val sharedPref =
                    activity?.getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE)!!
                with(sharedPref.edit()) {
                  putString("name", "")
                  putString("password", "")
                  apply()
                }
                val intent = Intent(this.context, LoginActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
              }
              .show()
          true
        }
        else -> false
      }
    }
    fetchUser()
    return binding.root
  }

  fun fetchUser() {
    val sharedPref =
        requireActivity()
            .getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
    val name = sharedPref.getString("name", "")!!
    ServerApiClass.getOne(
        name,
        object : Callback<UserProfile> {
          override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {
            if (response.isSuccessful) {
              userProfile = response.body()!!
              Log.e("PLZ", userProfile.toString())
              updateProfile(userProfile)
            } else {
              Toast.makeText(activity, R.string.onSemiFailure, Toast.LENGTH_SHORT).show()
            }
          }

          override fun onFailure(call: Call<UserProfile>, t: Throwable) {
            Toast.makeText(activity, R.string.onFailure, Toast.LENGTH_SHORT).show()
          }
        })
  }

  fun updateProfile(userProfile: UserProfile) {
    binding.profilePhoto.load(userProfile.profilePhoto) {
      placeholder(R.drawable.baseline_person_24)
      error(R.drawable.baseline_person_24)
      transformations(CircleCropTransformation())
      crossfade(500)
    }
    binding.textFieldName.editText?.setText(userProfile.name)
    binding.textFieldClass.editText?.setText("${userProfile.classNum}분반")
    binding.textFieldExplanation.editText?.setText(userProfile.explanation)
    binding.textFieldEmail.editText?.setText(userProfile.email)
    //binding.textFieldGithubId.editText?.setText(userProfile.githubId)
    //binding.textFieldInstagramId.editText?.setText(userProfile.instagramId)
    //binding.textFieldLinkedinId.editText?.setText(userProfile.linkedInId)

    // Make links in the EditText clickable
    binding.textFieldGithubId.editText?.movementMethod = LinkMovementMethod.getInstance()
    binding.textFieldInstagramId.editText?.movementMethod = LinkMovementMethod.getInstance()
    binding.textFieldLinkedinId.editText?.movementMethod = LinkMovementMethod.getInstance()

    // Setup my Spannable with clickable URLs
    val spannable_github: Spannable = SpannableString("https://github.com/" + userProfile.githubId)
    Linkify.addLinks(spannable_github, Linkify.WEB_URLS)

    val spannable_instagram: Spannable =
      SpannableString("https://m.instagram.com/" + userProfile.instagramId)
    Linkify.addLinks(spannable_instagram, Linkify.WEB_URLS)

    val spannable_linkedin: Spannable =
      SpannableString("https://www.linkedin.com/in/" + userProfile.linkedInId)
    Linkify.addLinks(spannable_linkedin, Linkify.WEB_URLS)

    // The fix: Append a zero-width space to the Spannable
    val text_github = TextUtils.concat(spannable_github, "\u200B")
    val text_instagram = TextUtils.concat(spannable_instagram, "\u200B")
    val text_linkedin = TextUtils.concat(spannable_linkedin, "\u200B")

    // Use it!
    binding.textFieldGithubId.editText?.setText(text_github)
    binding.textFieldInstagramId.editText?.setText(text_instagram)
    binding.textFieldLinkedinId.editText?.setText(text_linkedin)
  }

  private fun shareText() {
    val sendIntent: Intent = Intent().apply {
      action = Intent.ACTION_SEND
      putExtra(Intent.EXTRA_TEXT, "몰입메이트 앱에서 함께 프로필을 공유해 보아요.")
      type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    startActivity(shareIntent)
  }
}
