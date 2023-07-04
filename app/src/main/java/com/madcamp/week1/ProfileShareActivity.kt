package com.madcamp.week1

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.madcamp.week1.databinding.ActivityProfileShareBinding


class ProfileShareActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileShareBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileShareBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.shareInstagramBtn.setOnClickListener { shareInstagram() }

        binding.shareGithubBtn.setOnClickListener { shareGithub() }

        binding.shareTextBtn.setOnClickListener { shareText() }

        val sharedPref =
            this@ProfileShareActivity.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE
            )
        with(sharedPref.edit()) {
            putInt("settings", sharedPref.getInt("settings", -1) + 1)
            apply()
        }
        Toast.makeText(
            this@ProfileShareActivity,
            sharedPref.getInt("settings", -1).toString(),
            Toast.LENGTH_SHORT
        )
            .show()
    }

    private fun shareInstagram() {
        val instagram_urlintent = Intent(Intent.ACTION_VIEW, Uri.parse("https://m.instagram.com/"))
        startActivity(instagram_urlintent)
    }

    private fun shareGithub() {
        val github_urlintent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/"))
        startActivity(github_urlintent)
    }

    private fun shareText() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "@@@ 앱을 통해 몰입캠프 참가 학생과 프로필을 공유해 보아요.")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}