package com.example.taskjobs.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.taskjobs.FIRST_NAME_COLUMN
import com.example.taskjobs.JOB_TABLE
import com.example.taskjobs.LAST_NAME_COLUMN

@Entity(tableName = JOB_TABLE)
data class Jobdb(
    @PrimaryKey val uid: String,
    @ColumnInfo(name = FIRST_NAME_COLUMN) val title: String?,
    @ColumnInfo(name = LAST_NAME_COLUMN) val company: String?
)