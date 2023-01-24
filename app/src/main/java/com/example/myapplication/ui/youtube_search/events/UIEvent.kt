package com.example.myapplication.ui.youtube_search.events

import com.example.myapplication.data.usecases.youtube.models.YouTubeVideoData

sealed class UIEvent {
    data class OnItemClicked(val youTubeVideoData: YouTubeVideoData) : UIEvent()
    data class OnVideoSearched(val searchedWord: String) : UIEvent()
}