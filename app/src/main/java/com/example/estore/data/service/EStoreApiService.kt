package com.example.estore.data.service

import com.example.estore.data.model.Product
import com.example.estore.data.model.Token
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface EStoreApiService {
    @GET("products")
    suspend fun fetchProducts(): Response<List<Product>>

    @POST("auth/login")
    suspend fun login(
        @Body user: HashMap<String, String>
    ): Token
    @GET("products")
    suspend fun searchProducts(): Response<List<Product>>
}