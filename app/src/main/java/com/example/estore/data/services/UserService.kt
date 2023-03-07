package com.example.estore.data.services

import com.example.estore.common.Result
import com.example.estore.data.model.Token
import com.example.estore.data.model.User
import com.example.estore.data.network.ApiService
import javax.inject.Inject

class UserService @Inject constructor(private val apiService: ApiService) {

    suspend fun login(user: User): Result<Token> {
        val userMap = HashMap<String, String>()
        userMap["username"] = user.username
        userMap["password"] = user.password
        return try {
            val result = apiService.login(userMap)
            Result.Success(result)
        } catch (error: Exception) {
            Result.Error(error)
        }
    }
}