package com.example.weatherapp.features.auth.presentation

import androidx.lifecycle.viewModelScope
import com.example.weatherapp.core.navigation.Destination
import com.example.weatherapp.core.utils.Constants.CRED_EMAIL
import com.example.weatherapp.core.utils.Constants.CRED_PASSWORD
import com.example.weatherapp.core.utils.customesnackbar.SnackBarController
import com.example.weatherapp.core.utils.customesnackbar.SnackBarEvent
import com.example.weatherapp.core.utils.isValidEmail
import com.example.weatherapp.core.utils.isValidPassword
import com.example.weatherapp.features.BaseViewModel
import com.example.weatherapp.manager.datastoremanager.DataStoreManagerImpl.DataStoreKeys.KEY_IS_LOGGED_IN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(): BaseViewModel() {

    private val _loginState = MutableStateFlow(LoginState(isLoading = false))
    val loginState = _loginState.asStateFlow()

    fun onEvent(loginEvents: LoginEvents){
        when(loginEvents){
            is LoginEvents.OnEmailIdChanged -> {
                _loginState.update { it.copy(emailId = loginEvents.email) }
            }
            is LoginEvents.OnPasswordChanged -> {
                _loginState.update { it.copy(password = loginEvents.password) }
            }
            is LoginEvents.OnSubmitClicked -> {
                onSubmitClicked()
            }
        }
    }

    private fun onSubmitClicked(){
        viewModelScope.launch {
            _loginState.update { it.copy(isLoading = true) }
            if(isValidUserName(userEmail = loginState.value.emailId, password = loginState.value.password)){
                if(loginState.value.emailId == CRED_EMAIL && loginState.value.password == CRED_PASSWORD){
                    appNavigator.tryNavigateTo(
                        route = Destination.HomeScreen,
                        popUpToRoute = Destination.LoginScreen,
                        inclusive = true
                    )
                    SnackBarController.sendEvent(event = SnackBarEvent(message = "Login SuccessFully.."))
                    dataStorePreference.putPreference(key = KEY_IS_LOGGED_IN,true)
                } else {
                    SnackBarController.sendEvent(event = SnackBarEvent(message = "User Not Found"))
                }

            } else {
                SnackBarController.sendEvent(event = SnackBarEvent(message = "Please Enter Valid Credentials.."))
            }
            _loginState.update { it.copy(isLoading = false) }
        }
    }
    private fun isValidUserName(userEmail : String,password: String) : Boolean{
        return userEmail.isValidEmail() && password.isValidPassword()
    }
}

data class LoginState(
    val isLoading : Boolean,
    val emailId : String = "",
    val password : String = "",
    val isEmailIdValid : Boolean = true,
    val isPasswordValid : Boolean = true,
)