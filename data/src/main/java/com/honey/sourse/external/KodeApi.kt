package com.honey.sourse.external

import com.honey.model.RawPeopleResponse
import retrofit2.http.GET

interface KodeApi {
    @GET(Endpoints.BASE)
    suspend fun getRawPeoples() : RawPeopleResponse
}