package com.honey.usecase

import com.honey.model.People
import com.honey.repository.PeoplesRepository

class LoadPeoplesUseCase(private val repository: PeoplesRepository) {
    suspend fun invoke() : List<People>{
        try {
            val peoples = repository.getAllPeoples()
            return peoples
        } catch (e: Exception){
            throw e
        }
    }
}