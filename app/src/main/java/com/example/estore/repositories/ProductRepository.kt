package com.example.estore.repositories

import com.example.estore.data.ResourceRepository
import com.example.estore.data.local.ProductDao
import com.example.estore.data.model.Product
import com.example.estore.data.service.ProductService
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productService: ProductService,
    private val dao: ProductDao
) : ResourceRepository() {

    suspend fun fetchProducts() = loadResourceFlow { productService.fetchProducts() }

    suspend fun searchProducts(query: String) = loadResourceFlow { productService.searchProducts(query) }

    suspend fun addFavoriteProduct(product: Product) = dao.insertProduct(product)

    suspend fun deleteFavoriteProduct(product: Product) = dao.deleteProduct(product)

    fun getAllFavoriteProducts() =  dao.getAllProducts()
}