package com.madcamp.week1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.madcamp.week1.databinding.FragmentSlideTabBinding

class SlideTabFragment : Fragment() {
  private lateinit var binding: FragmentSlideTabBinding
  private lateinit var viewPager: ViewPager2

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View {
    binding = FragmentSlideTabBinding.inflate(inflater, container, false)

    viewPager = binding.viewPager
    val pagerAdapter = ScreenSliderPagerAdapter(this)
    pagerAdapter.addTabInfo(DevelopFragment(), "Develop", R.drawable.baseline_person_24)
    pagerAdapter.addTabInfo(
        ContactsFragment(), "Contacts", R.drawable.baseline_perm_contact_calendar_24)
    pagerAdapter.addTabInfo(GalleryFragment(), "Gallery", R.drawable.baseline_insert_photo_24)
    pagerAdapter.addTabInfo(ProfileFragment(), "Profile", R.drawable.baseline_person_24)
    viewPager.adapter = pagerAdapter

    val tabLayout = binding.tabLayout
    for (i in 1..pagerAdapter.itemCount) {
      tabLayout.addTab(tabLayout.newTab())
    }
    // https://developer.android.com/guide/navigation/navigation-swipe-view-2?hl=ko
    TabLayoutMediator(tabLayout, viewPager) { tab, position ->
          val (_, title, icon) = pagerAdapter.getTabInfo(position)
          tab.text = title
          tab.setIcon(icon)
        }
        .attach()

    // Custom onBackPressed
    // https://developer.android.com/guide/navigation/navigation-custom-back?hl=ko
    val callback =
        object : OnBackPressedCallback(true) {
          override fun handleOnBackPressed() {
            if (viewPager.currentItem == 0) {
              // If the user is currently looking at the first step, allow the system to handle
              // the Back button. This calls finish() on this activity and pops the back stack.
              this@SlideTabFragment.requireActivity().finish()
            } else {
              // Otherwise, select the previous step.
              viewPager.currentItem = viewPager.currentItem - 1
            }
          }
        }
    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

    return binding.root
  }

  inner class ScreenSliderPagerAdapter(fm: Fragment) : FragmentStateAdapter(fm) {
    private var data: ArrayList<Triple<Fragment, String, Int>> = ArrayList()

    fun addTabInfo(fragment: Fragment, title: String, icon: Int) {
      data.add(Triple(fragment, title, icon))
    }

    fun getTabInfo(position: Int): Triple<Fragment, String, Int> {
      return data[position]
    }

    override fun getItemCount(): Int {
      return data.size
    }

    override fun createFragment(position: Int): Fragment {
      return data[position].first
    }
  }
}
