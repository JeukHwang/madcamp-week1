package com.madcamp.week1.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserProfile(
    val id: String,
    val name: String,
    val classNum: Int,
    val profilePhoto: String,
    val email: String?,
    val githubId: String?,
    val instagramId: String?,
    val linkedInId: String?,
    val facebookId: String?,
    val explanation: String?,
) : Parcelable
