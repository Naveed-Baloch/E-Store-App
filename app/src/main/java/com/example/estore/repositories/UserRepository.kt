package com.example.estore.repositories

import com.example.estore.data.model.User
import com.example.estore.data.network.ApiService
import javax.inject.Inject

class UserRepository @Inject constructor(val apiService: ApiService) {
    suspend fun login(user: User) = apiService.login(user)

}