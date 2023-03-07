package com.example.estore.repositories

import com.example.estore.data.ResourceRepository
import com.example.estore.data.services.ProductService
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productService: ProductService) :
    ResourceRepository() {

    suspend fun fetchProducts() = loadResource {
        productService.fetchProducts()
    }

    suspend fun searchProducts(query: String) = loadResource {
        productService.searchProducts(query)
    }
}