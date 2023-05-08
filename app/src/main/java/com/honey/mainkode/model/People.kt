package com.honey.mainkode.model

import java.io.Serializable


data class People(
    //"https://randomuser.me/api/portraits/men/75.jpg"
    val avatarURL : String = "",
    val firstName: String,
    val lastName: String,
    val userTag: String,
    val department : Department,
    val position: String,
    val dob: String,
    val phone: String
) : Serializable