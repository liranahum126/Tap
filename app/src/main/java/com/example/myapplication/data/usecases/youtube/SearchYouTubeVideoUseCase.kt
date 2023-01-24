package com.example.myapplication.data.usecases.youtube

import android.util.Log
import com.example.myapplication.constants.Links
import com.example.myapplication.data.repositories.YouTubeRepository
import com.example.myapplication.data.usecases.youtube.models.YouTubeVideoData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext
import java.util.regex.Matcher
import java.util.regex.Pattern

class SearchYouTubeVideoUseCase(private val youTubeRepository: YouTubeRepository) {

    private val queryChannel = MutableSharedFlow<String>(0)
    private var searchedWord = ""

    @OptIn(FlowPreview::class)
    val searchResultsChannel = queryChannel
        .debounce { searchedWord ->
            when {
                (searchedWord.length == 1) -> 0
                (searchedWord.length % 3 == 0) -> 0
                else -> 300
            }
        }.mapLatest {
            val searchWord = it

            if (searchWord.isNotEmpty()) {
                val queryString = getQueryString(searchedWord)
                val response = youTubeRepository.query(queryString)
                getSearchResponseList(response)
            } else {
                listOf()
            }
        }.catch { cause ->
            Log.e("ERROR", "ERROR: " + cause.message)
        }

    private fun getSearchResponseList(response: String): List<YouTubeVideoData> {
        val youtubeDataList = arrayListOf<YouTubeVideoData>()
        val videoLinkPattern: Pattern = Pattern.compile("[^a-zA-Z]watch\\?v=([a-zA-Z0-9]){11}")
        val m: Matcher = videoLinkPattern.matcher(response)
        while (m.find()) {
            youtubeDataList.add(
                YouTubeVideoData(
                    thumbnailUrl = "",
                    videoUrl = Links.YOUTUBE_LINK + m.group()
                )
            )
        }
        return youtubeDataList
    }

/*    private fun getListFromRegex(pattern: String, string: String): List<String> {
        val responseList = arrayListOf<String>()
        val pattern: Pattern = Pattern.compile(pattern)
        val m: Matcher = pattern.matcher(string)
        while (m.find()) {
            responseList.add(m.group())
        }
        return responseList
    }*/


    suspend fun search(search: String) {
        withContext(Dispatchers.IO) {
            if (search == searchedWord) return@withContext

            searchedWord = search
            queryChannel.emit(search)
        }
    }

    private fun getQueryString(searchedWord: String): String {
        val searchedWordsArray = searchedWord.split(" ")
        var searchedQuery = ""
        searchedWordsArray.forEach { word ->
            searchedQuery += "$word+"
        }
        searchedQuery = searchedQuery.removeSuffix("+")

        return searchedQuery
    }
}