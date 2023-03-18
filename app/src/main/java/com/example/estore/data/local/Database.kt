package com.example.estore.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.estore.data.model.Cart
import com.example.estore.data.model.Product

@Database(entities = [Product::class, Cart::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract val dao: ProductDao
    abstract val cartDao: CartDao
}