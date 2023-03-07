package com.example.estore.common

/**
 * Result class when loading data.
 */

sealed class Result<T>(val data: T? = null, val error: Throwable?) {

    /**
     * When getting an instance of this class you've made a successful call.
     */
   open class Success<T>(data: T): Result<T>(data, null)

    /**
     * Error class when something went wrong.
     */
   open class Error<T>(error: Throwable): Result<T>(null, error)
}