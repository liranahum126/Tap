package com.example.myapplication.ui.youtube_search.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.usecases.youtube.models.YouTubeVideoData

class YoutubeListAdapter(
    private var youtubeDataList: List<YouTubeVideoData> = listOf(),
    private val clickListener: ItemClickListener
) : RecyclerView.Adapter<YoutubeListAdapter.YouTubeItemViewHolder>() {

    fun setData(youtubeDataList: List<YouTubeVideoData>) {
        this.youtubeDataList = youtubeDataList
        // TODO:
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YouTubeItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_youtube, parent, false)
        return YouTubeItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: YouTubeItemViewHolder, position: Int) {
        val item = youtubeDataList[position]
        initLinkTextView(holder, item)
    }

    private fun initLinkTextView(holder: YouTubeItemViewHolder, item: YouTubeVideoData) {
        holder.linkTextView.text = item.videoUrl

        holder.linkTextView.setOnClickListener {
            clickListener.onItemClicked(item)
        }
    }

    override fun getItemCount() = youtubeDataList.size

    class YouTubeItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var linkTextView: TextView

        init {
            linkTextView = view.findViewById(R.id.youtube_link_text_view)
        }
    }

    interface ItemClickListener {
        fun onItemClicked(itemData: YouTubeVideoData)
    }
}
