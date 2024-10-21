package com.example.weatherapp.features.addusers.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PersonAddAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherapp.R
import com.example.weatherapp.core.theme.DarkBlue96
import com.example.weatherapp.core.theme.WeatherAppTheme
import com.example.weatherapp.core.theme.appLightBackGround
import com.example.weatherapp.core.theme.darkBlue
import com.example.weatherapp.core.theme.grey50
import com.example.weatherapp.core.theme.grey60
import com.example.weatherapp.core.theme.grey85
import com.example.weatherapp.core.theme.lightGrey
import com.example.weatherapp.core.utils.CommonLoader

@Preview(showBackground = true)
@Composable
private fun AddUsersScreenPreview() {
    WeatherAppTheme {
        AddUsersScreen(
            isLoading = false,
            firstName = "Syed",
            lastName = "Abuthahir",
            userEmailId = "syed@mail.com",
            onEvent = {}
        )
    }
}

@Composable
fun AddUsersRoute(
    addUsersViewModel: AddUsersViewModel = hiltViewModel()
) {
    val addUserDetailsState by addUsersViewModel.addUserDetails.collectAsStateWithLifecycle()

    AddUsersScreen(
        isLoading = addUserDetailsState.isLoading,
        firstName = addUserDetailsState.firstName,
        lastName = addUserDetailsState.lastName,
        userEmailId = addUserDetailsState.emailId,
        onEvent = addUsersViewModel::onEvent,
    )
}

@Composable
fun AddUsersScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    firstName: String,
    lastName: String,
    userEmailId: String,
    onEvent: (AddUserEvents) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val isPasswordVisible by remember { mutableStateOf(false) }

    CommonLoader(isLoading = isLoading)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = appLightBackGround),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (
            modifier = modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(bottom = 50.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            IconButton(
                onClick = {
                    onEvent(AddUserEvents.OnBackPressed)
                },
                modifier = modifier.size(30.dp)
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "backpressed"
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    Icons.Default.PersonAddAlt,
                    contentDescription = "adduser",
                    tint = Color.White,
                    modifier = modifier.size(40.dp)
                )
                Text(
                    text = stringResource(id = R.string.str_add_user),
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = modifier.padding(vertical = 10.dp, horizontal = 4.dp),
                )
            }
            Spacer(
                modifier = modifier.size(30.dp)
            )
        }

        Card(
            modifier = Modifier
                .fillMaxSize(),
            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.str_firstname),
                    color = grey85,
                    fontSize = 12.sp,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    textAlign = TextAlign.Start,
                )
                TextField(
                    modifier = modifier
                        .fillMaxWidth()
                        .border(
                            .5.dp,
                            lightGrey,
                            shape = RoundedCornerShape(10.dp)
                        ),
                    value = firstName,
                    onValueChange = {
                        onEvent(AddUserEvents.OnUserFirstNameEntered(it))
                    },
                    interactionSource = interactionSource,
                    visualTransformation = VisualTransformation.None,
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.str_type_here),
                            fontSize = 12.sp,
                            color = lightGrey,
                            fontWeight = FontWeight.SemiBold
                        )
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    ),
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        cursorColor = lightGrey,
                        focusedContainerColor = DarkBlue96,
                        unfocusedContainerColor = Color.White,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                        disabledContainerColor = grey50
                    ),
                    shape = RoundedCornerShape(10.dp),
                    textStyle = TextStyle(
                        fontSize = 12.sp
                    ),
                )
                Spacer(modifier = modifier.padding(5.dp))
                Text(
                    text = stringResource(id = R.string.str_lastname),
                    color = grey85,
                    fontSize = 12.sp,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    textAlign = TextAlign.Start,
                )
                TextField(
                    modifier = modifier
                        .fillMaxWidth()
                        .border(
                            .5.dp,
                            lightGrey,
                            shape = RoundedCornerShape(10.dp)
                        ),
                    value = lastName,
                    onValueChange = {
                        onEvent(AddUserEvents.OnUserSecondNameEntered(it))
                    },
                    interactionSource = interactionSource,
                    visualTransformation = VisualTransformation.None,
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.str_type_here),
                            fontSize = 12.sp,
                            color = lightGrey,
                            fontWeight = FontWeight.SemiBold
                        )
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    ),
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        cursorColor = lightGrey,
                        focusedContainerColor = DarkBlue96,
                        unfocusedContainerColor = Color.White,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                        disabledContainerColor = grey50
                    ),
                    shape = RoundedCornerShape(10.dp),
                    textStyle = TextStyle(
                        fontSize = 12.sp
                    ),
                )

                Text(
                    text = stringResource(id = R.string.str_email),
                    color = grey85,
                    fontSize = 12.sp,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    textAlign = TextAlign.Start,
                )
                TextField(
                    modifier = modifier
                        .fillMaxWidth()
                        .border(
                            .5.dp,
                            lightGrey,
                            shape = RoundedCornerShape(10.dp)
                        ),
                    value = userEmailId,
                    onValueChange = {
                        onEvent(AddUserEvents.OnEmailEntered(it))
                    },
                    interactionSource = interactionSource,
                    visualTransformation = VisualTransformation.None,
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.str_type_here),
                            fontSize = 12.sp,
                            color = lightGrey,
                            fontWeight = FontWeight.SemiBold
                        )
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    ),
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        cursorColor = lightGrey,
                        focusedContainerColor = DarkBlue96,
                        unfocusedContainerColor = Color.White,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                        disabledContainerColor = grey50
                    ),
                    shape = RoundedCornerShape(10.dp),
                    textStyle = TextStyle(
                        fontSize = 12.sp
                    ),
                )
                Row (
                    modifier = modifier.padding(vertical = 40.dp)
                ){
                    CancelButton (
                        modifier = modifier.weight(1f),
                        onEvent = {
                            keyboardController?.hide()
                            onEvent(it)
                        }
                    )
                    Spacer(modifier = modifier.padding(2.dp))
                    SaveButton(
                        modifier = modifier.weight(1f),
                        onEvent = {
                            keyboardController?.hide()
                            onEvent(it)
                        }
                    )
                }

                Spacer(modifier = Modifier.navigationBarsPadding())
            }
        }
    }
}

@Preview
@Composable
private fun LoginButtonPreview() {
    SaveButton(
        onEvent = {},
    )
}

@Composable
fun SaveButton(
    modifier: Modifier = Modifier,
    onEvent: (AddUserEvents) -> Unit
) {
    Button(
        modifier = modifier
            .padding(vertical = 10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor =  darkBlue
        ),
        onClick = {
            onEvent(AddUserEvents.OnAddUserDetails)
        },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(.5.dp, color = grey60)
    ) {
        Text(
            text = stringResource(id = R.string.str_save),
            modifier = Modifier.padding(8.dp),
            fontSize = 14.sp,
            color =  Color.White
        )
    }
}

@Composable
fun CancelButton(
    modifier: Modifier = Modifier,
    onEvent: (AddUserEvents) -> Unit
) {
    Button(
        modifier = modifier
            .padding(vertical = 10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor =  Color.White
        ),
        onClick = {
            onEvent(AddUserEvents.OnCancel)
        },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(.5.dp, color = grey60)
    ) {
        Text(
            text = stringResource(id = R.string.str_cancel),
            modifier = Modifier.padding(8.dp),
            fontSize = 14.sp,
            color = darkBlue
        )
    }
}