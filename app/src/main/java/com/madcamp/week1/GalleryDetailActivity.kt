package com.madcamp.week1

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.madcamp.week1.databinding.ActivityGalleryDetailBinding
import com.madcamp.week1.gallery.GridItem

class GalleryDetailActivity : FragmentActivity() {
  companion object {
    const val DATA = "data"
  }

  private lateinit var binding: ActivityGalleryDetailBinding
  private lateinit var item: GridItem
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityGalleryDetailBinding.inflate(layoutInflater)
    setContentView(R.layout.activity_gallery_detail)
    item =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
          intent.getParcelableExtra(DATA, GridItem::class.java)!!
        } else {
          intent.extras?.get(DATA) as GridItem
        }
  }
}
