package com.example.taskjobs.data.api

import com.example.taskjobs.data.model.Job
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("positions.json?description=api")
    suspend fun getJObs(): Response<List<Job>>
}