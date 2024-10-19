package com.example.weatherapp.core.apiutils

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.google.gson.Gson
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import io.ktor.utils.io.InternalAPI
import io.ktor.utils.io.readText
import java.io.IOException
import javax.inject.Inject

open class BaseApiService @Inject constructor() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    @OptIn(InternalAPI::class)
    suspend inline fun <reified T> safeApiCall(
        crossinline apiToBeCalled: suspend () -> HttpResponse,
    ): Resource<T> {
        return try {
            val response: HttpResponse = apiToBeCalled()
            val cookies = response.headers.getAll(HttpHeaders.SetCookie)
            Log.e("isApiSuccess","${response.status.isSuccess()}")
            if (response.status.isSuccess()) {
                Resource.Success(data = response.body())
            } else {
                val errorResponse: CustomErrorResponse? = Gson().fromJson(
                    response.content.readBuffer.readText(),
                    CustomErrorResponse::class.java
                )
                Resource.Error(message = errorResponse?.exception ?: "Something went wrong")
            }
        } catch (e: RedirectResponseException) {
            val errorResponse: CustomErrorResponse? = Gson().fromJson(
                e.response.content.readBuffer.readText(),
                CustomErrorResponse::class.java
            )
            Resource.Error(message = errorResponse?.message ?: "Something went wrong")
        } catch (e: ClientRequestException) {
            val errorResponse: CustomErrorResponse? = Gson().fromJson(
                e.response.content.readBuffer.readText(),
                CustomErrorResponse::class.java
            )
            Resource.Error(message = errorResponse?.message ?: "Something went wrong")
        } catch (e: ServerResponseException) {
            val errorResponse: CustomErrorResponse? = Gson().fromJson(
                e.response.content.readBuffer.readText(),
                CustomErrorResponse::class.java
            )
            Resource.Error(message = errorResponse?.message ?: "Something went wrong")
        } catch (e: HttpException) {
            val errorResponse: CustomErrorResponse? =
                Gson().fromJson(e.message, CustomErrorResponse::class.java)
            Resource.Error(message = errorResponse?.message ?: "Something went wrong")
        } catch (e: IOException) {
            Resource.Error("Please check your network connection")
        } catch (e: Exception) {
            Resource.Error(message = e.message ?: "Something went wrong")
        }
    }
}