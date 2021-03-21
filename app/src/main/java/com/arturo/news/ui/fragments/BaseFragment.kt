package com.arturo.news.ui.fragments

import android.os.Bundle
import android.view.View

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

import com.arturo.news.ui.NewsActivity
import com.arturo.news.ui.viewmodels.NewsViewModel

abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    lateinit var viewModel: NewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as NewsActivity).viewModel
    }
}