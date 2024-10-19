package com.digivalsolutions.digilearn.core.apiutils

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.digivalsolutions.digilearn.core.manager.datastoremanager.DataStoreManagerImpl.DataStoreKeys.KEY_IS_LOGGED_IN
import com.digivalsolutions.digilearn.core.manager.datastoremanager.DataStoreManagerImpl.DataStoreKeys.KEY_SID_COOKIE
import com.digivalsolutions.digilearn.core.manager.datastoremanager.DataStoreManagerImpl.DataStoreKeys.KEY_SID_EXPIRY
import com.digivalsolutions.digilearn.core.manager.datastoremanager.DataStorePreferenceManager
import com.digivalsolutions.digilearn.features.faceauthentication.model.StudentDescriptorResponseModel
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

open class BaseApiService @Inject constructor(
    val dataStoreManager: DataStorePreferenceManager
) {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    @OptIn(InternalAPI::class)
    suspend inline fun <reified T> safeApiCall(
        isLoginApi: Boolean = false,
        crossinline apiToBeCalled: suspend () -> HttpResponse,
    ): Resource<T> {
        return try {
            val response: HttpResponse = apiToBeCalled()
            val cookies = response.headers.getAll(HttpHeaders.SetCookie)
            Log.e("isApiSeccess","${response.status.isSuccess()}")
            if (response.status.isSuccess()) {
                if (isLoginApi) {
                    val sidCookie = cookies?.find { it.startsWith("sid=") }
                    if (sidCookie?.isNotEmpty() == true) {
                        val sidValue = sidCookie.substringAfter("sid=").substringBefore(";")
                        val expiresValue = sidCookie.substringAfter("Expires=", "").substringBefore(";")

                        dataStoreManager.apply {
                            putPreference(key = KEY_IS_LOGGED_IN, value = true)
                            putPreference(key = KEY_SID_COOKIE, value = sidValue)
                            putPreference(key = KEY_SID_EXPIRY, value = expiresValue)
                        }
                    }
                }
                Log.e("responseBody","response : ${response.body<StudentDescriptorResponseModel>()}")
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