package com.example.taskjobs.di.module

import com.example.taskjobs.ui.jobFragment.JobsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
val viewModule= module {
   viewModel { JobsViewModel(get()) }
}
