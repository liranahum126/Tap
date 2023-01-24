package com.example.myapplication.app

import android.app.Application
import com.example.myapplication.di.appModules
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    companion object {
        val applicationScope = CoroutineScope(SupervisorJob())
        lateinit var instance: MainApplication
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        startKoin {
            //androidLogger()
            androidContext(applicationContext)
            modules(appModules)
        }
    }
}