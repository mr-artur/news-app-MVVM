package com.arturo.news.ui

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController

import com.arturo.news.R
import com.arturo.news.db.ArticleDatabase
import com.arturo.news.repositories.NewsRepository
import com.arturo.news.ui.viewmodels.NewsViewModel
import com.arturo.news.ui.viewmodels.NewsViewModelProviderFactory

import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity(R.layout.activity_news) {

    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)
            .get(NewsViewModel::class.java)

        bottomNavigationView.setupWithNavController(navHostFragment.findNavController())
    }
}