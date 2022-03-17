package com.esmaeel.challenge.common.base

import com.esmaeel.challenge.data.remote.models.ErrorModel
import com.esmaeel.challenge.di.ContextProvider
import com.esmaeel.challenge.di.ResourcesHandler
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.io.IOException

abstract class BaseRepository(
    private val contextProvider: ContextProvider,
    private var resourcesHandler: ResourcesHandler
) {

    fun <T> networkHandler(fetch: suspend () -> T) = flow {

        // trying to invoke the passed function
        // and emit it's value
        try {
            emit(fetch.invoke())
        }

        // there has been an exception
        // so we might need to respond to it differently
        catch (throwable: Throwable) {

            when (throwable) {

                // network timeout exception due to OkHttpClient timeout configurations
                is TimeoutCancellationException -> throw AppException(resourcesHandler.NETWORK_ERROR_TIMEOUT)

                // might be due to no wifi enabled or network.
                is IOException -> throw AppException(resourcesHandler.NETWORK_ERROR)

                // Server has responded and now we need to check for the status Code (404,401, .... )
                is HttpException -> handleNetworkException(throwable)

                // some other exception
                else -> throw AppException(throwable.message)
            }

        }

    }.flowOn(contextProvider.IO)

    private fun handleNetworkException(throwable: HttpException) {
        when (throwable.code()) {
            401 -> throw AuthException(getErrorFrom(throwable))
            else -> throw AppException(getErrorFrom(throwable))
        }
    }

    private fun getErrorFrom(throwable: HttpException): String {
        return try {
            // inject this instance later
            val jsonAdapter: JsonAdapter<ErrorModel> = Moshi.Builder()
                .add(MoshiConverterFactory.create())
                .build().adapter(ErrorModel::class.java)
            jsonAdapter.fromJson(throwable.response()?.errorBody()?.string() ?: "")?.message
                ?: resourcesHandler.UNKNOWN_ERROR
        } catch (exception: Exception) {
            Timber.e(exception)
            resourcesHandler.UNKNOWN_ERROR
        }
    }
}
