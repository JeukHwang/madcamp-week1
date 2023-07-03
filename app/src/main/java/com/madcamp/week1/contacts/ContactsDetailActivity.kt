package com.madcamp.week1.contacts

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.FragmentActivity
import com.madcamp.week1.R
import com.madcamp.week1.databinding.ActivityGalleryDetailBinding

class ContactsDetailActivity : FragmentActivity() {
  companion object {
    const val extraTitle: String = "title"
  }

  private lateinit var binding: ActivityGalleryDetailBinding
  lateinit var datas: String

  override fun onCreate(savedInstanceState: Bundle?) {

    super.onCreate(savedInstanceState)
    binding = ActivityGalleryDetailBinding.inflate(layoutInflater)
    setContentView(R.layout.activity_contacts_detail)

    // datas = intent.getStringExtra("data")!!
    // this@ContactsDetailActivity.runOnUiThread { binding.textView.text = title }

    // Glide.with(this).load(datas.img).into(img_profile)
    // tv_name.text = datas

    val data = intent.getStringExtra("data")!!
    // val data = intent.getParcelableExtra<ContactsInfo>("data")
    binding.textView.text = data

    //        binding.textView.text = data!!.category
    //        binding.detailTitle.text = data!!.title

    /*
    //    val title = intent.getStringExtra(extraTitle)
    //    val photoUrl = intent.getStringExtra(extraPhotoUrl)
    //    this@GalleryDetailActivity.runOnUiThread { binding.textView.text = title }

    //    binding.imageView.load(R.drawable.baseline_perm_contact_calendar_24) {
    //      placeholder(R.drawable.baseline_perm_contact_calendar_24)
    //      error(R.drawable.baseline_perm_contact_calendar_24)
    //      crossfade(true)
    //    }
    window.sharedElementEnterTransition.addListener(
        object : Transition.TransitionListener {
            override fun onTransitionEnd(transition: Transition?) {
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
     */
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) {
      // to reverse the scene transition animation
      supportFinishAfterTransition()
    }
    return super.onOptionsItemSelected(item)
  }
}
