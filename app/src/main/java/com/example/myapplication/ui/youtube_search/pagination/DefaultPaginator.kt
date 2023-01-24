package com.example.myapplication.ui.youtube_search.pagination

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DefaultPaginator<Key, Item, Res : DefaultPaginator.Response<Item>>(
    private val initialKey: Key,
    private inline val onLoadUpdated: (Boolean) -> Unit,
    private inline val onRequest: suspend (nextKey: Key) -> Res,
    private inline val getNextKey: suspend (List<Item>) -> Key,
    private inline val hasReachedMaxItems: suspend () -> Boolean,
//    private inline val onError: suspend (Throwable?) -> Unit,
    private inline val onSuccess: suspend (items: List<Item>, totalItems: Int, newKey: Key) -> Unit,
    private val scope: CoroutineScope
) : Paginator<Key, Item> {

    private var currentKey = initialKey
    private var isMakingRequest = false
    var totalItems = 0

    override suspend fun loadNextItems() {
        scope.launch(Dispatchers.IO) {
            if (isMakingRequest || hasReachedMaxItems()) {
                return@launch
            }

            isMakingRequest = true
            onLoadUpdated(true)

            val response = onRequest(currentKey)
            totalItems = response.getTotalItems()

            isMakingRequest = false

            currentKey = getNextKey(response.getList())
            onSuccess(response.getList(), totalItems, currentKey)
            onLoadUpdated(false)
        }
    }

    override fun reset() {
        currentKey = initialKey
    }

    interface Response<T> {
        fun getList(): List<T>
        fun getTotalItems(): Int
    }
}