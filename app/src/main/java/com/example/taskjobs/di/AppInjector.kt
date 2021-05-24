package com.example.taskjobs.di

import com.example.taskjobs.App
import com.example.taskjobs.di.module.appModule
import com.example.taskjobs.di.module.dataModule
import com.example.taskjobs.di.module.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

@SuppressWarnings("unchecked")
object AppInjector {

    fun init(app: App) {
        startKoin {
            androidLogger(Level.NONE)
            androidContext(app)
            modules(listOf(appModule, dataModule, viewModule))
        }
    }

}