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

    val tabLayout = binding.tabLayout
    tabLayout.addTab(tabLayout.newTab().setText("1"))
    tabLayout.addTab(tabLayout.newTab().setText("2"))

    viewPager = binding.viewPager
    viewPager.adapter = ScreenSliderPagerAdapter(this)
    TabLayoutMediator(tabLayout, viewPager) { tab, position -> tab.text = "OBJECT ${(position+1)}" }
        .attach()

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
    var fragments: MutableList<Fragment> = arrayListOf(GalleryFragment(), ProfileFragment())

    override fun getItemCount(): Int {
      return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
      return fragments[position]
    }
  }
}
