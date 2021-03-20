package com.arturo.news.api.interceptors

import com.arturo.news.BuildConfig

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()
        val newUrl = original.url.newBuilder().addQueryParameter("apiKey", BuildConfig.API_KEY).build()
        original = original.newBuilder().url(newUrl).build()
        return chain.proceed(original)
    }
}