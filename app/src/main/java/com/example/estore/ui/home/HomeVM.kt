package com.example.estore.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.estore.data.Result
import com.example.estore.data.model.Product
import com.example.estore.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(private val repository: ProductRepository) : ViewModel() {

    val products = MutableStateFlow(listOf<Product>())

    suspend fun fetchProducts() = repository.fetchProducts().onEach { result ->
        if (result is Result.Success && !result.data.isNullOrEmpty()) {
            products.value = result.data
        }
    }

    fun addFavoriteProduct(product: Product) = viewModelScope.launch { repository.addFavoriteProduct(product) }
    fun deleteFavoriteProduct(product: Product) = viewModelScope.launch { repository.deleteFavoriteProduct(product) }

    fun getAllFavoriteProducts() = repository.getAllFavoriteProducts()
}