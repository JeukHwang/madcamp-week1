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
          val intent = Intent(this.context, ProfileShareActivity::class.java)
          //                    val bundle = Bundle()
          //                    bundle.putString("info_photo", info.photo)
          //                    bundle.putString("info_id", info.id)
          //                    bundle.putString("info_email", info.email)
          //                    intent.putExtra("bundle_key", bundle)
          startActivity(intent)
          true
        }

        else -> false
      }
    }
    return binding.root
  }
}
