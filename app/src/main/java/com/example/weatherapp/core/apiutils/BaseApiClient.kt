package com.example.weatherapp.core.apiutils

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.endpoint
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BaseApiClient @Inject constructor() {

    var baseUrl: String = "https://auth-staging.gcp.digivalitsolutions.com/api/v0/auth"//DEFAULT_BASEURL
    private var authToken : String = ""

    val apiClient = HttpClient(CIO) {
        expectSuccess = true

        engine {
            endpoint {
                keepAliveTime = TIME_OUT
                connectTimeout = TIME_OUT
                connectAttempts = CONNECTION_ATTEMPT
            }
        }

        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }

        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens(accessToken = authToken, refreshToken = null)
                }
            }
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.i(KTOR_LOGGER, message)
                }
            }
            level = LogLevel.ALL
        }

        install(ResponseObserver) {
            onResponse { response ->
                Log.i(KTOR_LOGGER_STATUS_CODE, "${response.status.value}")
            }
        }
    }

    fun updateBaseUrl(newBaseUrl: String) {
        baseUrl = newBaseUrl
    }

    fun updateAuthToken(newAuthToken : String){
        authToken = newAuthToken
    }

    suspend inline fun callGetRequest(
        endPoint: String,
        header: Map<String, String> = mapOf(),
        queryParams: Map<String, String> = mapOf(),
        body: Any? = null
    ): HttpResponse {
        return apiClient.get {
            url {
                takeFrom(baseUrl)
                appendPathSegments(endPoint)
                header.forEach { (key, value) ->
                    header(key, value)
                }
                queryParams.forEach { (key, value) ->
                    parameter(key, value)
                }
                if (body != null) {
                    setBody(body)
                }
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend inline fun callPutRequest(
        endPoint: String,
        header: Map<String, String> = mapOf(),
        queryParams: Map<String, String> = mapOf(),
        body: Any? = null
    ) : HttpResponse {
        return apiClient.put {
            url {
                takeFrom(baseUrl)
                appendPathSegments(endPoint)
                header.forEach { (key, value) ->
                    header(key, value)
                }
                queryParams.forEach { (key, value) ->
                    parameter(key, value)
                }
                if (body != null) {
                    setBody(body)
                }
                contentType(ContentType.Application.Json)
            }
        }
    }

    companion object {
        private const val TIME_OUT: Long = 10000
        private const val CONNECTION_ATTEMPT = 3
        private const val KTOR_LOGGER = "WeatherAppLogger"
        private const val KTOR_LOGGER_STATUS_CODE = "Ktor_STATUS_CODE"
    }
}