package com.example.myapplication.ui.youtube_search

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.usecases.youtube.models.YouTubeVideoData
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.youtube_search.adapters.YoutubeListAdapter
import com.example.myapplication.ui.youtube_search.events.UIEvent
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class YouTubeSearchFragment : BaseFragment() {

    private val viewModel by viewModel<YouTubeSearchViewModel>()

    private lateinit var searchBox: AppCompatEditText
    private lateinit var resultRecyclerView: RecyclerView
    private val recyclerViewAdapter =
        YoutubeListAdapter(clickListener = object : YoutubeListAdapter.ItemClickListener {
            override fun onItemClicked(itemData: YouTubeVideoData) {
                onListItemClickedListener(itemData)
            }
        })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initViewModel()
    }

    private fun initViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchResultChannel.collect {
                    recyclerViewAdapter.setData(it)
                }
            }
        }
        viewModel.openYoutubeURLEvent.observe(viewLifecycleOwner) {
            openLink(it)
        }
    }

    private fun initViews() {
        initSearchBox()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        resultRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        resultRecyclerView.adapter = recyclerViewAdapter
    }

    private fun onListItemClickedListener(itemData: YouTubeVideoData) {
        viewModel.onUIEvent(UIEvent.OnItemClicked(itemData))
    }

    private fun initSearchBox() {
        searchBox.doOnTextChanged { text, start, before, count ->
            viewModel.onUIEvent(UIEvent.OnVideoSearched(text.toString()))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sample, container, false)
        searchBox = view.findViewById(R.id.youtube_search_box)
        resultRecyclerView = view.findViewById(R.id.results_recycler_view)
        return view
    }

    private fun openLink(link: String) {
        val urlIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(link)
        )
        startActivity(urlIntent)
    }

}