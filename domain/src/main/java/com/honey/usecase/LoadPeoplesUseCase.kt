package com.honey.usecase

import com.honey.model.Department
import com.honey.model.People

class LoadPeoplesUseCase {
    suspend fun invoke() : List<People>{
//        throw Exception("API Call failed")

        try {
            //TODO later there has been real api call by retrofit
            val peoples = listOf<People>(
                People(
                    firstName = "Bee",
                    lastName = "Chan",
                    userTag = "LK",
                    department = Department.BackOffice,
                    position = "Technician",
                    dob = "2004-01-10",
                    phone = "802-623-1785"
                )
            )
            return peoples
        } catch (e: Exception){
            throw e
        }
    }
}