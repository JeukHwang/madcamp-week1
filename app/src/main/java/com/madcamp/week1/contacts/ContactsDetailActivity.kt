package com.madcamp.week1.contacts

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
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
}
