package com.example.estore.repositories


import com.example.estore.data.local.CartDao
import com.example.estore.data.ResourceRepository
import com.example.estore.data.model.Cart
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val dao: CartDao
) : ResourceRepository() {

    fun fetchCart() = dao.getCart()

    fun insertCart(cart: Cart) = dao.insertCart(cart)

    fun deleteCart(cart: Cart) = dao.deleteCart(cart)
}