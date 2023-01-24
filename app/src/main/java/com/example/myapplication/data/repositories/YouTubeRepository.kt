package com.example.myapplication.data.repositories

import android.util.Log
import com.example.myapplication.data.network.retrofit.YouTubeService
import com.example.myapplication.data.repositories.base.ApiResultBO
import com.example.myapplication.data.repositories.base.BaseRepository
import retrofit2.Response

class YouTubeRepository constructor(private val youTubeService: YouTubeService) : BaseRepository() {

    suspend fun query(query: String): String {
        Log.e("qwe", ":query = " + query)
        return getResult {
            youTubeService.search(query)
        }.data ?: ""
    }
}