package com.madcamp.week1

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.madcamp.week1.databinding.ActivityMainBinding

class MainActivity : FragmentActivity() {
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)
  }
}
