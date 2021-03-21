package com.arturo.news.ui.viewmodels

import androidx.lifecycle.ViewModel

import com.arturo.news.repositories.NewsRepository

class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {
}