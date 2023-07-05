package com.madcamp.week1

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.madcamp.week1.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {
  private lateinit var binding: FragmentGalleryBinding

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View {
    binding = FragmentGalleryBinding.inflate(inflater, container, false)
    binding.topAppBar.setOnMenuItemClickListener { menuItem ->
      when (menuItem.itemId) {
        R.id.refresh -> {
          fetchFeed()
          true
        }
        R.id.add -> {
          val intent = Intent(this.context, GalleryCreateActivity::class.java)
          intent.putExtra("profile", "")
          startActivity(intent)
          true
        }
        else -> false
      }
    }
    return binding.root
  }

  fun fetchFeed() {
    return
  }
}
