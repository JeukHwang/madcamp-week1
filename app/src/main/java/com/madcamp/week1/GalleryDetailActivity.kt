package com.madcamp.week1

import android.os.Build
import android.os.Bundle
import android.transition.Transition
import android.view.MenuItem
import androidx.fragment.app.FragmentActivity
import coil.load
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
    window.sharedElementEnterTransition.addListener(
        object : Transition.TransitionListener {
          override fun onTransitionEnd(transition: Transition?) {
            binding.imageView.load(item.photoUrl)
            transition?.removeListener(this)
          }

          override fun onTransitionResume(transition: Transition?) {
            // To change body of created functions use File | Settings | File Templates.
          }

          override fun onTransitionPause(transition: Transition?) {
            // To change body of created functions use File | Settings | File Templates.
          }

          override fun onTransitionCancel(transition: Transition?) {
            // To change body of created functions use File | Settings | File Templates.
          }

          override fun onTransitionStart(transition: Transition?) {
            // To change body of created functions use File | Settings | File Templates.
          }
        })
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) {
      // to reverse the scene transition animation
      supportFinishAfterTransition()
    }
    return super.onOptionsItemSelected(item)
  }
}
