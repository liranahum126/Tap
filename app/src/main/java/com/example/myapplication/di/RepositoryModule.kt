package com.example.myapplication.di

import com.example.myapplication.data.repositories.YouTubeRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { YouTubeRepository(get()) }
}