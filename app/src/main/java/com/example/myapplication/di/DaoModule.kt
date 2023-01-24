package com.example.myapplication.di

import androidx.room.Room
import com.example.myapplication.data.db.MyDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val daoModule = module {
    single {
        Room.databaseBuilder(androidApplication(), MyDatabase::class.java, "my-db")
            .build()
    }
    single { get<MyDatabase>().sampleDao() }
}