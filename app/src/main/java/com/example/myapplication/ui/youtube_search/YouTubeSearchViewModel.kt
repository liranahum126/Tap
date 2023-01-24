package com.example.myapplication.ui.youtube_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.app.utils.SingleLiveEvent
import com.example.myapplication.data.usecases.youtube.SearchYouTubeVideoUseCase
import com.example.myapplication.data.usecases.youtube.models.YouTubeVideoData
import com.example.myapplication.ui.youtube_search.events.UIEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class YouTubeSearchViewModel constructor(
    private val searchYouTubeVideoUseCase: SearchYouTubeVideoUseCase
) : ViewModel() {

    val searchResultChannel = searchYouTubeVideoUseCase.searchResultsChannel
    val openYoutubeURLEvent = SingleLiveEvent<String>()

    fun onUIEvent(uiEvent: UIEvent) {
        when (uiEvent) {
            is UIEvent.OnVideoSearched -> searchYouTubeVideo(uiEvent.searchedWord)
            is UIEvent.OnItemClicked -> openYoutubeVideo(uiEvent.youTubeVideoData)
        }
    }

    private fun openYoutubeVideo(youTubeVideoData: YouTubeVideoData) {
        openYoutubeURLEvent.value = youTubeVideoData.videoUrl
    }

    private fun searchYouTubeVideo(searchedWord: String) {
        viewModelScope.launch(Dispatchers.IO) {
            searchYouTubeVideoUseCase.search(searchedWord)
        }
    }
}