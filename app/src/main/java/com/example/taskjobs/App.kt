 package com.example.taskjobs

import android.content.Context
import android.util.Log
import androidx.multidex.MultiDexApplication
import com.example.taskjobs.di.AppInjector


open class App : MultiDexApplication(){

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        AppInjector.init(this)
    }


    companion object {
        lateinit var context: Context
    }

}
