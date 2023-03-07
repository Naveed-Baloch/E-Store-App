package com.example.estore.data.services

import com.example.estore.common.Result
import com.example.estore.data.model.Product
import com.example.estore.data.network.ApiService
import javax.inject.Inject

class ProductService @Inject constructor(private val apiService: ApiService) {

    suspend fun fetchProducts(): Result<List<Product>> {
        return try {
            val result = apiService.fetchProducts()
            Result.Success(result)
        } catch (error: Exception) {
            Result.Error(error)
        }
    }

    suspend fun searchProducts(query: String): Result<List<Product>> {
        return try {
            val result = apiService.searchProducts()
            Result.Success(result)
        } catch (error: Exception) {
            Result.Error(error)
        }
    }
}