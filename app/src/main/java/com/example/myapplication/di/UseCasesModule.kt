package com.example.myapplication.di

import com.example.myapplication.data.usecases.youtube.SearchYouTubeVideoUseCase
import org.koin.dsl.module

val useCasesModule = module {
    single { SearchYouTubeVideoUseCase(get()) }
}
