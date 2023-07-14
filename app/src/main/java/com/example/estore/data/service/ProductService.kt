package com.example.estore.data.service

import com.example.estore.common.Result
import com.example.estore.data.model.Product
import javax.inject.Inject

class ProductService @Inject constructor(private val eStoreApiService: EStoreApiService) : ApiService() {

    suspend fun fetchProducts() = eStoreApiService.fetchProducts().unwrap()
    suspend fun searchProducts(query: String)= eStoreApiService.searchProducts().unwrap()

}