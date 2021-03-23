package com.arturo.news.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View

import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.arturo.news.Constants.SEARCH_NEWS_TIME_DELAY_MS
import com.arturo.news.R
import com.arturo.news.adapters.NewsAdapter
import com.arturo.news.extensions.hide
import com.arturo.news.extensions.show
import com.arturo.news.util.Resource

import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.*

class SearchNewsFragment : BaseFragment(R.layout.fragment_search_news) {

    private val TAG = "SearchNewsFragment"

    private lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        setUpRecyclerView()
        subscribeOnEtSearchTextChanges()
        subscribeOnSearchNewsResource()
    }

    private fun setUpAdapter() {
        newsAdapter = NewsAdapter()
        newsAdapter.setOnItemClickListener {
            val action = SearchNewsFragmentDirections.fromSearchNewsToArticle(it)
            findNavController().navigate(action)
        }
    }

    private fun setUpRecyclerView() {
        val dividerDrawable =
            ResourcesCompat.getDrawable(resources, R.drawable.news_middle_divider, null)
        rvSearchNews.apply {
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

    private fun subscribeOnEtSearchTextChanges() {
        var job: Job? = null
        etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = mainScope.launch {
                delay(SEARCH_NEWS_TIME_DELAY_MS)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.searchNews(editable.toString())
                    }
                }
            }
        }
    }

    private fun subscribeOnSearchNewsResource() {
        viewModel.searchNews.observe(viewLifecycleOwner, { resource ->
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