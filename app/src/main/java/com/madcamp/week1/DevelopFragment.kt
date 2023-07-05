package com.madcamp.week1

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.madcamp.week1.databinding.FragmentDevelopBinding

class DevelopFragment : Fragment() {

  private lateinit var binding: FragmentDevelopBinding
  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View {
    binding = FragmentDevelopBinding.inflate(inflater, container, false)
    binding.topAppBar.setOnMenuItemClickListener { menuItem ->
      when (menuItem.itemId) {
        R.id.edit -> {
          val intent = Intent(this.context, ProfileEditActivity::class.java)
          //                    val bundle = Bundle()
          //                    bundle.putString("info_photo", info.photo)
          //                    bundle.putString("info_id", info.id)
          //                    bundle.putString("info_email", info.email)
          //                    intent.putExtra("bundle_key", bundle)
          startActivity(intent)
          true
        }

        R.id.share -> {
          shareText()
          true
        }

        else -> false
      }
    }
    return binding.root
  }

  /*
  private fun shareInstagram() {
    val instagram_urlintent = Intent(Intent.ACTION_VIEW, Uri.parse("https://m.instagram.com/"))
    startActivity(instagram_urlintent)
  }

  private fun shareGithub() {
    val github_urlintent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/"))
    startActivity(github_urlintent)
  }
  */

  private fun shareText() {
    val sendIntent: Intent = Intent().apply {
      action = Intent.ACTION_SEND
      putExtra(Intent.EXTRA_TEXT, "@@@ 앱에서 함께 프로필을 공유해 보아요.")
      type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    startActivity(shareIntent)
  }
}
