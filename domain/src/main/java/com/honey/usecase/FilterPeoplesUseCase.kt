package com.honey.usecase

import com.honey.model.Department
import com.honey.model.People

class FilterPeoplesUseCase {
    fun invoke(
        peopleList : List<People>,
        searchField: String,
        department: Department
    ) : List<People>{
        val value = searchField.lowercase()
        val newList = peopleList.filter { people->
            (people.firstName.lowercase().contains(value) ||
                    people.lastName.lowercase().contains(value) ||
                    people.userTag.lowercase().contains(value) ||
                    people.position.lowercase().contains(value) ||
                    people.dob.lowercase().contains(value) ||
                    people.phone.lowercase().contains(value)) && (
                    people.department == department || department == Department.All
                    )
        }
        return newList
    }
}