package com.madcamp.week1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.madcamp.week1.databinding.ActivityProfileEditBinding

class ProfileEditActivity : AppCompatActivity() {
  private lateinit var binding: ActivityProfileEditBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityProfileEditBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)
  }
}
