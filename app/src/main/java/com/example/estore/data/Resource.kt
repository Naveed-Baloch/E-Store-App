package com.example.estore.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

sealed class Result<T>(
    val data: T? = null,
    val cause: Throwable? = null,
    val progress: Any? = null
) {
    class Success<T>(data: T?) : Result<T>(data)
    class Loading<T>(progress: Any? = null) : Result<T>(progress = progress)
    class Error<T>(cause: Throwable? = null, data: T? = null) : Result<T>(data, cause)
}

abstract class ResourceRepository {
    fun <T> loadResourceFlow(coroutineContext: CoroutineContext = Dispatchers.IO, query: suspend () -> T): Flow<Result<T>> = flow {
        emit(Result.Loading())
        try {
            emit(Result.Success(query()))
        } catch (exception: Exception) {
            emit(Result.Error(exception))
        }
    }.flowOn(coroutineContext)

}

