package com.example.estore.data.local

import androidx.room.*
import com.example.estore.data.model.Cart

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCart(cart: Cart)

    @Delete
    fun deleteCart(cart: Cart)

    @Query("Select * from cart LIMIT 1")
    fun getCart(): Cart?
}