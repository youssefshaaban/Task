package com.example.taskjobs.data.repositery


import androidx.lifecycle.LiveData
import com.example.taskjobs.data.Resource
import com.example.taskjobs.data.model.Job
import com.example.taskjobs.data.model.Jobdb
import kotlinx.coroutines.flow.Flow

interface JobDataSource {
    suspend fun getJobs(): Flow<Resource<List<Job>>>
    suspend fun getJobsIdDb(): List<String>
    suspend fun addFavourite(job:Jobdb)
    suspend fun deleteFavourite(job:Jobdb)

}