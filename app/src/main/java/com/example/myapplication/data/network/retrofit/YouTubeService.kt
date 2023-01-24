package com.example.myapplication.data.network.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YouTubeService {

    @GET("results")
    suspend fun search(@Query("search_query") query: String): Response<String>
}