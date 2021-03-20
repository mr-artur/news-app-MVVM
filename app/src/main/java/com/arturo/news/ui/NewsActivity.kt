package com.arturo.news.ui

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController

import com.arturo.news.R

import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity(R.layout.activity_news) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bottomNavigationView.setupWithNavController(navHostFragment.findNavController())
    }
}