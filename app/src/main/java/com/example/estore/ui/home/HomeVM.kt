package com.example.estore.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.estore.data.model.Product
import com.example.estore.repositories.ProductRepository
import com.example.estore.storage.UserStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    private val repository: ProductRepository,
    private val userStorage: UserStorage
) : ViewModel() {

    suspend fun fetchProducts() = repository.fetchProducts()
    fun addFavoriteProduct(product: Product) =
        viewModelScope.launch { repository.addFavoriteProduct(product) }

    fun deleteFavoriteProduct(product: Product) =
        viewModelScope.launch { repository.deleteFavoriteProduct(product) }

    fun getAllFavoriteProducts() = repository.getAllFavoriteProducts()
}