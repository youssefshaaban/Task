package com.example.taskjobs.di.module

import com.example.taskjobs.data.repositery.JobDataSourceReopRepositery
import org.koin.dsl.module

val dataModule = module {
  single { JobDataSourceReopRepositery(get(),get(),get()) }
}