package com.example.estore.ui.search

import androidx.lifecycle.ViewModel
import com.example.estore.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class SearchVM @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {

    val searchQuery = MutableStateFlow("")

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchResults = searchQuery.filter {
        it.isNotBlank()
    }.debounce(500L)
        .flatMapLatest { keyword ->
            productRepository.searchProducts(keyword)
        }
}