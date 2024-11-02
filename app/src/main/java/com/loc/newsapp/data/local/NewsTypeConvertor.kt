package com.loc.newsapp.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.loc.newsapp.domain.model.Source

@ProvidedTypeConverter
class NewsTypeConvertor {
    @TypeConverter
    fun sourceToString(source: Source):String{
        return "${source.id},${source.name}"
    }
    @TypeConverter
    fun stringToSource(source: String):Source{
        val split = source.split(",")
        return Source(split[0],split[1])
    }
}