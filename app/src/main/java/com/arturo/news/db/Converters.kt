package com.arturo.news.db

import androidx.room.TypeConverter

import com.arturo.news.models.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source): String = source.name

    @TypeConverter
    fun toSource(name: String): Source = Source(name, name)
}