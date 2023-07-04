package com.madcamp.week1.profile

data class UpdateData(
    val name: String,
    val email: String?,
    val profilePhoto: String?,
    val githubId: String?,
    val instagramId: String?,
    val linkedInId: String?,
    val explanation: String?,
)
