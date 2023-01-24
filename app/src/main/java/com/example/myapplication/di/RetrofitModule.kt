package com.example.myapplication.di

import com.example.myapplication.data.network.retrofit.YouTubeService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

val networkModule = module {
    single { provideGson() }
    single { provideAppOkHttpClient() }
    single { provideYouTubeService(get(), get()) }
}

fun provideAppOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    })
    .build()

fun provideGson(): Gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setPrettyPrinting().setLenient().create()

fun provideYouTubeService(
    gson: Gson,
    okHttpClient: OkHttpClient
): YouTubeService =
    Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl("https://www.youtube.com/")
        .client(okHttpClient)
        .build()
        .create(YouTubeService::class.java)
