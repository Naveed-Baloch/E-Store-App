package com.example.estore.data.network

import com.example.estore.data.model.Product
import com.example.estore.data.model.Token
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("products")
    suspend fun fetchProducts(): List<Product>

    @POST("auth/login")
    suspend fun login(
        @Body user: HashMap<String, String>
    ): Token
    @GET("products")
    suspend fun searchProducts(): List<Product>
}