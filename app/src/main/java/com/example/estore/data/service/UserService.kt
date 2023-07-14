package com.example.estore.data.service

import com.example.estore.common.Result
import com.example.estore.data.model.Token
import com.example.estore.data.model.User
import javax.inject.Inject

class UserService @Inject constructor(private val EStoreApiService: EStoreApiService) {

    suspend fun login(user: User): Result<Token> {
        val userMap = HashMap<String, String>()
        userMap["username"] = user.username
        userMap["password"] = user.password
        return try {
            val result = EStoreApiService.login(userMap)
            Result.Success(result)
        } catch (error: Exception) {
            Result.Error(error)
        }
    }
}