package com.example.estore.repositories

import com.example.estore.data.model.User
import com.example.estore.data.services.UserService
import javax.inject.Inject

class UserRepository @Inject constructor(private val userService: UserService) {
    suspend fun login(user: User) = userService.login(user)
}