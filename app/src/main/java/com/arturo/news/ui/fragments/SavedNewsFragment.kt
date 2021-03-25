package com.arturo.news.ui.fragments

import android.os.Bundle
import android.view.View

import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.arturo.news.R
import com.arturo.news.adapters.NewsAdapter

import com.google.android.material.snackbar.Snackbar

import kotlinx.android.synthetic.main.fragment_saved_news.*

class SavedNewsFragment : BaseFragment(R.layout.fragment_saved_news) {

    private lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        subscribeOnSavedNews();
        setUpRecyclerView()
    }

    private fun setUpAdapter() {
        newsAdapter = NewsAdapter()
        newsAdapter.setOnItemClickListener {
            val action = SavedNewsFragmentDirections.fromSavedNewsToArticle(it)
            findNavController().navigate(action)
        }
    }

    private fun subscribeOnSavedNews() {
        viewModel.getSavedNews().observe(viewLifecycleOwner, { articles ->
            newsAdapter.differ.submitList(articles)
        })
    }

    private fun setUpRecyclerView() {
        val dividerDrawable =
            ResourcesCompat.getDrawable(resources, R.drawable.news_middle_divider, null)
        rvSavedNews.apply {
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
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rvSavedNews)
        }
    }

    private val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val article = newsAdapter.differ.currentList[position]
            viewModel.deleteArticle(article)
            Snackbar.make(view!!, "Successfully deleted article", Snackbar.LENGTH_LONG).apply {
                setAction(getString(R.string.action_undo)) {
                    viewModel.saveArticle(article)
                }
                show()
            }
        }
    }
}