package com.honey.usecase

import com.honey.model.People
import com.honey.model.SortBy
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class SortPeoplesUseCase {
    fun invoke(
        peopleList: List<People>,
        sort: SortBy,
        today: LocalDate
    ) : List<People>{
        val newList : List<People> = when(sort){
            SortBy.Alphabet -> {
                peopleList.sortedBy { it.firstName }
            }
            SortBy.Dob -> {
                dobSort(peopleList, today)
            }
        }
        return newList
    }

    private fun dobSort(peopleList: List<People>,  today: LocalDate) : List<People>{
        val sortedList = peopleList.sortedWith(compareBy
        { person ->
            val dob = LocalDate.parse(person.dob)
            val nextBirthday = if (dob.withYear(today.year).isBefore(today)) {
                dob.withYear(today.year + 1)
            } else {
                dob.withYear(today.year)
            }
            ChronoUnit.DAYS.between(today, nextBirthday)
        })
        return sortedList
    }
}