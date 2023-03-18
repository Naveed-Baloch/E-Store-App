package com.example.estore.ui.cart

import androidx.lifecycle.ViewModel
import com.example.estore.data.model.Cart
import com.example.estore.repositories.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartVM @Inject constructor(private val cartRepo: CartRepository) : ViewModel() {
    fun getCart() = cartRepo.fetchCart()
    fun insertCart(cart: Cart) = cartRepo.insertCart(cart)
}