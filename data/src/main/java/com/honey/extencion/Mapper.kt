package com.honey.extencion

import com.honey.model.Department
import com.honey.model.People
import com.honey.model.RawPeople

fun List<RawPeople>.toDomainPeoples(): List<People>{
    return map(){
        it.toDomainPeople()
    }
}

fun RawPeople.toDomainPeople(): People {
    return People(
        //Anyway this links, don't work now. I can make random avatars instead goose stub, but i think no need.
        avatarURL = "",
        firstName = firstName,
        lastName = lastName,
        userTag = userTag,
        department = departmentConverter(department),
        position = position,
        dob = birthday,
        phone = phone
    )
}

fun departmentConverter(department: String) : Department{
    return when (department){
        "ios" -> {Department.Ios}
        "design" -> {Department.Design}
        "management" -> {Department.Management}
        "qa" -> {Department.Qa}
        "back_office" -> {Department.BackOffice}
        "frontend" -> {Department.Frontend}
        "hr" -> {Department.Hr}
        "pr" -> {Department.Pr}
        "backend" -> {Department.Backend}
        "support" -> {Department.Support}
        "analytics" -> {Department.Analytics}
        else-> {Department.Android}

    }
}