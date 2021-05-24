package com.example.taskjobs.di.module

import com.example.taskjobs.App
import com.example.taskjobs.data.ServiceGenerator
import com.example.taskjobs.data.db.AppDatabase
import com.example.taskjobs.data.remote.RemoteDataJobImp
import com.example.taskjobs.utils.Network
import com.example.taskjobs.utils.NetworkConnectivity
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext


val appModule = module {
    fun provideCoroutineContext(): CoroutineContext {
        return Dispatchers.IO
    }

    fun provideNetworkConnectivity(): NetworkConnectivity {
        return Network(App.context)
    }

    single { ServiceGenerator() }
    single { provideCoroutineContext() }
    single { provideNetworkConnectivity() }

    single {RemoteDataJobImp(get(),get())  }
    single { AppDatabase(App.context) }

}