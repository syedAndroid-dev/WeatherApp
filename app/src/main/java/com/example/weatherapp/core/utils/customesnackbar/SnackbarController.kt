package com.example.weatherapp.core.utils.customesnackbar

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow


data class SnackBarEvent(
    val message : String ,
    val action: SnackBarAction? = null
)

data class SnackBarAction(
    val name : String,
    val action : () -> Unit
)

object SnackBarController {
    private val _events = Channel<SnackBarEvent>()

    val event = _events.receiveAsFlow()

    suspend fun sendEvent(event: SnackBarEvent){
        _events.send(event)
    }
}