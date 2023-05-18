package com.honey.repository

import android.util.Log
import com.honey.extencion.toDomainPeoples
import com.honey.model.People
import com.honey.sourse.external.KodeApi

class PeopleRepositoryImpl(
    private val api: KodeApi
)  : PeoplesRepository {

    override suspend fun getAllPeoples(): List<People> {
        val result = api.getRawPeoples().items
        Log.d("MyLogDataModule","Here a result of api call -> $result")
        return result.toDomainPeoples()
    }
}