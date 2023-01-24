package com.example.myapplication.data.repositories

import android.util.Log
import com.example.myapplication.data.network.retrofit.YouTubeService
import com.example.myapplication.data.repositories.base.BaseRepository

class YouTubeRepository constructor(private val youTubeService: YouTubeService) : BaseRepository() {

    suspend fun query(query: String): String {
        return getResult {
            youTubeService.search(query)
        }.data ?: ""
    }
}