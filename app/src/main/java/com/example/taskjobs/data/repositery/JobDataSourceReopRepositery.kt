package com.example.taskjobs.data.repositery


import androidx.lifecycle.LiveData
import com.example.taskjobs.data.Resource
import com.example.taskjobs.data.db.AppDatabase
import com.example.taskjobs.data.model.Job
import com.example.taskjobs.data.model.Jobdb
import com.example.taskjobs.data.remote.RemoteDataJobImp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

import kotlin.coroutines.CoroutineContext

class JobDataSourceReopRepositery (private val remoteData:RemoteDataJobImp, private val appDatabase: AppDatabase, private val ioDispatcher: CoroutineContext):
    JobDataSource {
    override suspend fun getJobs(): Flow<Resource<List<Job>>> {
        return flow {
            emit(remoteData.getJobs())
        }.flowOn(ioDispatcher)
    }

    override suspend fun getJobsIdDb(): List<String> {
        return appDatabase.todoDao().getJob().map { job->job.uid }.toList()
    }

    override suspend fun addFavourite(job: Jobdb) {
        appDatabase.todoDao().insert(job)
    }

    override suspend fun deleteFavourite(job: Jobdb) {
        appDatabase.todoDao().delete(job)
    }


}