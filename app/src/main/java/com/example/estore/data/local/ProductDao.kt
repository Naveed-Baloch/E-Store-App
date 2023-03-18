package com.example.estore.data.local

import androidx.room.*
import androidx.room.Dao
import com.example.estore.data.model.Product

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(Product: Product)

    @Delete
    suspend fun deleteProduct(Product: Product)

    @Query("Select * from product")
    fun getAllProducts(): List<Product>

    @Query("Select * from product where id=:id")
    suspend fun getProductById(id: Int): Product

    @Update
    suspend fun updateProduct(Product: Product)
}