package com.madcamp.week1.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeedProfile(
    val id: String,
    val photo: String,
    val content: Int,
    val authorName: String,
    val taggedUserName: Array<String>,
) : Parcelable
