package com.example.taskjobs.data.remote
import com.example.taskjobs.data.Resource
import com.example.taskjobs.data.model.Job


interface RemoteDataSourceJob {
    suspend fun getJobs(): Resource<List<Job>>




}