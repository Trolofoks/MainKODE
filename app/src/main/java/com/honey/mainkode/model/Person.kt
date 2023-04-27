package com.honey.mainkode.model


data class Person(
    val avatarURL : String = "",
    val firstName: String,
    val lastName: String,
    val userTag: String,
    val department : Department,
    val position: String,
    val dob: String,
    val phone: String
)