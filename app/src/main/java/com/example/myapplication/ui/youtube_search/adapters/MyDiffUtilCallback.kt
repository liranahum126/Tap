package com.example.myapplication.ui.youtube_search.adapters

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.data.usecases.youtube.models.YouTubeVideoData

class MyDiffUtilCallback(
    var newList: ArrayList<YouTubeVideoData>?,
    var oldList: ArrayList<YouTubeVideoData>?
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return if (oldList != null) oldList!!.size else 0
    }

    override fun getNewListSize(): Int {
        return if (newList != null) newList!!.size else 0
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return true
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newList!![newItemPosition].videoUrl != oldList!![oldItemPosition].videoUrl
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val (thumbnailUrl, videoUrl) = newList!![newItemPosition]
        val (thumbnailUrl1, videoUrl1) = oldList!![oldItemPosition]
        val diff = Bundle()
        if (videoUrl != videoUrl1) {
            diff.putString("video_url", videoUrl)
        }
        if (thumbnailUrl != thumbnailUrl1) {
            diff.putString("thumbnail_url", thumbnailUrl)
        }
        return if (diff.size() == 0) null else diff
    }
}