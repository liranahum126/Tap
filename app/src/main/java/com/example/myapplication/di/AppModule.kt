package com.example.myapplication.di

import com.example.myapplication.ui.youtube_search.YouTubeSearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


// view models
val appModule = module {
    viewModel { YouTubeSearchViewModel(get()) }
}
