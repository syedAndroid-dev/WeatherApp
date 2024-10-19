package com.example.weatherapp.core.utils.customesnackbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class ModifiedSnackBarState {

    private val _message = mutableStateOf<String?>(null)
    val message : State<String?> = _message

    var updateState by mutableStateOf(false)
        private set

    fun showSnackBar(message : String){
        _message.value = message
        updateState = !updateState
    }

    fun isNotEmpty(): Boolean{
        return _message.value != null
    }
}

@Composable
fun rememberComposeModifiedSnackBarState() : ModifiedSnackBarState {
    return remember { ModifiedSnackBarState() }
}