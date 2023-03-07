package com.example.estore.ui.home

import androidx.lifecycle.ViewModel
import com.example.estore.repositories.ProductRepository
import com.example.estore.storage.UserStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    private val repository: ProductRepository,
    private val userStorage: UserStorage
) : ViewModel() {

    suspend fun fetchProducts() = repository.fetchProducts()
}