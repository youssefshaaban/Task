package com.example.taskjobs.data.dao

import androidx.room.*
import com.example.taskjobs.data.model.Job
import com.example.taskjobs.data.model.Jobdb

@Dao
interface JobDoa {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(job:Jobdb)

    @Delete
    suspend fun delete(job:Jobdb)

    @Query("SELECT * FROM job_table")
    suspend fun getJob(): List<Jobdb>
}