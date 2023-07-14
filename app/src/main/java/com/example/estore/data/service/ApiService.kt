package com.example.estore.data.service

import com.google.gson.JsonParseException

abstract class ApiService {
    fun <T : Any> retrofit2.Response<T>.unwrap(): T {
        return when {
            isSuccessful -> body() as T
            else -> throw parseApiException()
        }
    }

    private fun <T : Any> retrofit2.Response<T>.parseApiException(): Exception {
        var errorRsp: String? = null
        try {
            val errorMsg = errorBody()?.string()
            if (!errorMsg.isNullOrEmpty()) errorRsp = errorMsg
        } catch (_: JsonParseException) { }
        return Exception(errorRsp)
    }
}