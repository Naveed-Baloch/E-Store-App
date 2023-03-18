package com.example.estore.data.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProductListConverter {

    @TypeConverter
    fun listToJson(value: List<Product>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Product>::class.java).toList()
}

