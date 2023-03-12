package com.example.estore.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.estore.data.model.Product

@Database(entities = [Product::class], version = 2)
abstract class Database :RoomDatabase() {
    abstract val dao: Dao
}