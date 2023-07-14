package com.example.estore.ui.search

import androidx.lifecycle.ViewModel
import com.example.estore.data.Result
import com.example.estore.data.model.Product
import com.example.estore.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchVM @Inject constructor(private val productRepository: ProductRepository) : ViewModel() {

    val filteredProducts = MutableStateFlow(listOf<Product>())
    val searchQuery = MutableStateFlow("")

    suspend fun fetchProducts() = productRepository.fetchProducts().onEach { result ->
        if (result is Result.Success && !result.data.isNullOrEmpty()) {
            filteredProducts.value = result.data
        }
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchResults = searchQuery.filter {
        it.isNotBlank()
    }.debounce(500L).flatMapLatest { keyword ->
        //TODO Replace with the search function
            productRepository.fetchProducts().onEach { result ->
                if (result is Result.Success && !result.data.isNullOrEmpty()) {
                    filteredProducts.value = result.data
                }
            }
        }
}