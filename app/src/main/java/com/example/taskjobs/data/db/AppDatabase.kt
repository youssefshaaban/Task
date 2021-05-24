package com.example.taskjobs.data.db

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.taskjobs.data.dao.JobDoa
import com.example.taskjobs.data.model.Job
import com.example.taskjobs.data.model.Jobdb

@Database(
    entities = [Jobdb::class],
    version = 1, exportSchema = false
)
@TypeConverters()
abstract class AppDatabase : RoomDatabase() {
    // For Singleton instantiation
    abstract fun todoDao(): JobDoa

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "job.db"
        )
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                }
            })
            .build()
    }

}