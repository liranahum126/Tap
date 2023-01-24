package com.example.myapplication.ui.youtube_search.pagination

interface Paginator<Key, Item> {
    suspend fun loadNextItems()
    fun reset()
}