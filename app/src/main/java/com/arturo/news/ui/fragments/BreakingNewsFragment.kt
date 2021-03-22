package com.arturo.news.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration

import androidx.recyclerview.widget.LinearLayoutManager

import com.arturo.news.R
import com.arturo.news.adapters.NewsAdapter
import com.arturo.news.extensions.hide
import com.arturo.news.extensions.show
import com.arturo.news.util.Resource

import kotlinx.android.synthetic.main.fragment_breaking_news.*

class BreakingNewsFragment : BaseFragment(R.layout.fragment_breaking_news) {

    private val TAG = "BreakingNewsFragment"

    private lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        subscribeOnBreakingNewsResource()
    }

    private fun setUpRecyclerView() {
        newsAdapter = NewsAdapter()
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

    private fun subscribeOnBreakingNewsResource() {
        viewModel.breakingNews.observe(viewLifecycleOwner, { resource ->
            when (resource) {
                is Resource.Success -> {
                    hideProgressBar()
                    resource.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    resource.errorMessage?.let { errorMessage ->
                        Log.e(TAG, "An error occurred: $errorMessage")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun showProgressBar() {
        paginationProgressBar.show()
    }

    private fun hideProgressBar() {
        paginationProgressBar.hide()
    }
}