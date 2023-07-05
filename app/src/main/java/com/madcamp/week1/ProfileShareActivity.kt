package com.madcamp.week1

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class ProfileShareActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setup the alert builder
        val builder = AlertDialog.Builder(this)
        builder.setTitle("공유 옵션을 선택하세요")

        // add a list
        val animals = arrayOf("Instagram 계정 공유", "Github 계정 공유", "텍스트 공유")
        builder.setItems(animals) { dialog, which ->
            when (which) {
                0 -> {
                    shareInstagram()
                }

                1 -> {
                    shareGithub()
                }

                2 -> {
                    shareText()
                }
            }
        }

        // create and show the alert dialog
        val dialog = builder.create()
        dialog.show()
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