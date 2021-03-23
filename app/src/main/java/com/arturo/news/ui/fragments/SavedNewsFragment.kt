package com.arturo.news.ui.fragments

import android.os.Bundle
import android.view.View

import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.arturo.news.R
import com.arturo.news.adapters.NewsAdapter

import kotlinx.android.synthetic.main.fragment_breaking_news.*

class SavedNewsFragment : BaseFragment(R.layout.fragment_saved_news) {

    private lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        setUpRecyclerView()
    }

    private fun setUpAdapter() {
        newsAdapter = NewsAdapter()
        newsAdapter.setOnItemClickListener {
            val action = SavedNewsFragmentDirections.fromSavedNewsToArticle(it)
            findNavController().navigate(action)
        }
    }

    private fun setUpRecyclerView() {
        val dividerDrawable =
            ResourcesCompat.getDrawable(resources, R.drawable.news_middle_divider, null)
        rvBreakingNews.apply {
            adapter = newsAdapter
            addItemDecoration(
                DividerItemDecoration(
                    activity,
                    DividerItemDecoration.VERTICAL
                ).apply {
                    setDrawable(dividerDrawable!!)
                }
            )
            layoutManager = LinearLayoutManager(activity)
        }
    }
}