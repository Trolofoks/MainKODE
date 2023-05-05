package com.honey.mainkode.model


data class People(
    val avatarURL : String = "https://randomuser.me/api/portraits/men/75.jpg",
    val firstName: String,
    val lastName: String,
    val userTag: String,
    val department : Department,
    val position: String,
    val dob: String,
    val phone: String
)