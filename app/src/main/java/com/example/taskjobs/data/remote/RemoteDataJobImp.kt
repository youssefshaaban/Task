package com.example.taskjobs.data.remote

import com.example.taskjobs.data.NO_INTERNET_CONNECTION
import com.example.taskjobs.data.Resource
import com.example.taskjobs.data.ServiceGenerator
import com.example.taskjobs.data.model.Job
import com.example.taskjobs.data.api.ApiService
import com.example.taskjobs.utils.NetworkConnectivity
import java.io.IOException


class RemoteDataJobImp
    (
    private val serviceGenerator: ServiceGenerator,
    private val networkConnectivity: NetworkConnectivity
) :RemoteDataSourceJob {
    override suspend fun getJobs(): Resource<List<Job>> {
        val apiService = serviceGenerator.createService(ApiService::class.java)

        if (!networkConnectivity.isConnected()) {
            return Resource.DataError(errorCode = NO_INTERNET_CONNECTION)
        }
        return try {
            val response = apiService.getJObs()
            if (response.isSuccessful) {
                Resource.Success(response.body() as List<Job>)
            } else {
                val error = response.errorBody()?.string()
                Resource.DataError(error=error)

            }
        } catch (e: IOException) {
            Resource.DataError(error = e.toString())
        }
    }


}
