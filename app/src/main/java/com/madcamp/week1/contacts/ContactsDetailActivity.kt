package com.madcamp.week1.contacts

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.FragmentActivity
import coil.load
import coil.transform.CircleCropTransformation
import com.madcamp.week1.R
import com.madcamp.week1.databinding.ActivityContactsDetailBinding
import com.madcamp.week1.parcelable
import com.madcamp.week1.profile.UserProfile

class ContactsDetailActivity : FragmentActivity() {
  private lateinit var binding: ActivityContactsDetailBinding
  private lateinit var userProfile: UserProfile

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityContactsDetailBinding.inflate(layoutInflater)
    setContentView(binding.root)

    userProfile = intent.parcelable<UserProfile>("profile")!!
    updateProfile(userProfile)
    binding.toolbar.setNavigationOnClickListener { this@ContactsDetailActivity.finish() }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) {
      // to reverse the scene transition animation
      supportFinishAfterTransition()
    }
    return super.onOptionsItemSelected(item)
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
    binding.textFieldGithubId.editText?.setText(userProfile.githubId)
    binding.textFieldInstagramId.editText?.setText(userProfile.instagramId)
    binding.textFieldLinkedinId.editText?.setText(userProfile.linkedInId)
  }
}
