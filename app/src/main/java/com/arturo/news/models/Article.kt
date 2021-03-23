package com.arturo.news.models

import androidx.room.Entity
import androidx.room.PrimaryKey

import java.io.Serializable

@Entity(
    tableName = "articles"
)
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
) : Serializable {
    fun publishedAtDate(): String = publishedAt.substring(0, 10)
}