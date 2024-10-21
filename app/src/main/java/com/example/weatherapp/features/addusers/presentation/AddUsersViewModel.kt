package com.example.weatherapp.features.addusers.presentation

import androidx.lifecycle.viewModelScope
import com.example.weatherapp.core.appdb.UserDetails
import com.example.weatherapp.core.utils.customesnackbar.SnackBarController
import com.example.weatherapp.core.utils.customesnackbar.SnackBarEvent
import com.example.weatherapp.core.utils.isValidEmail
import com.example.weatherapp.features.BaseViewModel
import com.example.weatherapp.features.addusers.data.local.AddUsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddUsersViewModel @Inject constructor(
    private val addUsersRepository: AddUsersRepository
) : BaseViewModel() {

    private val _addUserDetailsState = MutableStateFlow(AddUserDetailState())
    val addUserDetails = _addUserDetailsState.asStateFlow()

    fun onEvent(addUserEvents: AddUserEvents) {
        when (addUserEvents) {
            AddUserEvents.OnAddUserDetails -> {
                onAddUserDetails()
            }

            is AddUserEvents.OnEmailEntered -> {
                _addUserDetailsState.update { it.copy(emailId = addUserEvents.email) }
            }

            is AddUserEvents.OnUserFirstNameEntered -> {
                _addUserDetailsState.update { it.copy(firstName = addUserEvents.firstName) }

            }

            is AddUserEvents.OnUserSecondNameEntered -> {
                _addUserDetailsState.update { it.copy(lastName = addUserEvents.secondName) }
            }

            is AddUserEvents.OnCancel -> {
                viewModelScope.launch {
                    appNavigator.navigateBack()
                }
            }

            is AddUserEvents.OnBackPressed -> {
                viewModelScope.launch {
                    appNavigator.navigateBack()
                }
            }
        }
    }

    private fun onAddUserDetails() {
        viewModelScope.launch {
            _addUserDetailsState.update { it.copy(isLoading = true) }
            if(isUserDetailsValid()){
                addUsersRepository.addUser(
                    userDetails = UserDetails(
                        userName = "${addUserDetails.value.firstName} ${addUserDetails.value.lastName}",
                        userEmail = addUserDetails.value.emailId
                    )
                )
                delay(500)
                appNavigator.navigateBack()
                SnackBarController.sendEvent(event = SnackBarEvent(message = "New User Added.."))
            } else {
                SnackBarController.sendEvent(event = SnackBarEvent(message = "Please Enter Valid Details.."))
            }
            _addUserDetailsState.update { it.copy(isLoading = true) }
        }
    }

    private fun isUserDetailsValid() : Boolean{
        return addUserDetails.value.firstName.isNotEmpty() && addUserDetails.value.lastName.isNotEmpty() && addUserDetails.value.emailId.isValidEmail()
    }
}

data class AddUserDetailState(
    val isLoading: Boolean = false,
    val firstName: String = "",
    val lastName: String = "",
    val emailId: String = ""
)