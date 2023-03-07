package com.example.estore.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn

sealed class Resource<T>(
    val data: T? = null,
    val cause: Throwable? = null,
    val progress: Any? = null
) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Loading<T>(progress: Any? = null) : Resource<T>(progress = progress)
    class Error<T>(cause: Throwable? = null, data: T? = null) : Resource<T>(data, cause)
}

abstract class ResourceRepository {
    fun <T> loadResource(query: suspend () -> T?): Flow<Resource<T>> = channelFlow {
        send(Resource.Loading())
        try {
            val result = query()
            send(Resource.Success(result))
        } catch (exception: Exception) {
            send(Resource.Error(exception))
        }
        close()
    }.flowOn(Dispatchers.IO)
}

