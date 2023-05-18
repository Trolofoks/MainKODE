package com.honey.repository

import com.honey.model.People

interface PeoplesRepository {
    suspend fun getAllPeoples(): List<People>
}