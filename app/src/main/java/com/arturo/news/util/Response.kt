package com.arturo.news.util

sealed class Response<T>(
    val data: T? = null,
    val errorMessage: String? = null
) {
    class Success<T>(data: T) : Response<T>(data)
    class Error<T>(errorMessage: String, data: T? = null) : Response<T>(data, errorMessage)
    class Loading<T> : Response<T>()
}