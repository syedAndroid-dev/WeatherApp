package com.example.weatherapp.features.auth.presentation

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherapp.R
import com.example.weatherapp.core.theme.DarkBlue96
import com.example.weatherapp.core.theme.Grey30
import com.example.weatherapp.core.theme.WeatherAppTheme
import com.example.weatherapp.core.theme.appDarkBackGround
import com.example.weatherapp.core.theme.appLightBackGround
import com.example.weatherapp.core.theme.darkBlue
import com.example.weatherapp.core.theme.lightGrey
import com.example.weatherapp.core.theme.redAccent100
import com.example.weatherapp.core.utils.CommonLoader

@Composable
fun LoginScreenRoute(
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    val loginState by loginViewModel.loginState.collectAsStateWithLifecycle()

    LoginScreen(
        isLoading = loginState.isLoading,
        emailId = loginState.emailId,
        password = loginState.password,
        isEmailIdValid = loginState.isEmailIdValid,
        isPasswordValid = loginState.isPasswordValid,
        onEvent = loginViewModel::onEvent,
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    WeatherAppTheme  {
        LoginScreen(
            isLoading = false,
            emailId = "syed@digivalsolutions.com",
            password = "123456789",
            isEmailIdValid = true,
            isPasswordValid = true,
            onEvent = {}
        )
    }
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    emailId: String,
    password: String,
    isEmailIdValid: Boolean,
    isPasswordValid: Boolean,
    onEvent: (LoginEvents) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = if (isSystemInDarkTheme()) appDarkBackGround else appLightBackGround),
    ) {
        CommonLoader(isLoading = isLoading)
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.statusBarsPadding())
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_logo),
                contentDescription = "Learn",
                modifier = Modifier
                    .padding(vertical = 50.dp)
                    .size(100.dp)
            )
            Spacer(modifier = modifier.padding(10.dp))
            LoginForm(
                emailId = emailId,
                password = password,
                isEmailIdValid = isEmailIdValid,
                isPasswordValid = isPasswordValid,
                onEvent = onEvent
            )
        }
    }
}


@Composable
fun LoginForm(
    modifier: Modifier = Modifier,
    emailId: String,
    password: String,
    isEmailIdValid: Boolean,
    isPasswordValid: Boolean,
    onEvent: (LoginEvents) -> Unit
) {
    Card(
        modifier = Modifier
            .wrapContentSize(),
        shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        elevation = CardDefaults.elevatedCardElevation(10.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppName()
            Spacer(modifier = modifier.padding(4.dp))
            EmailIdAndPasswordInputFields(
                emailId = emailId,
                password = password,
                onEvent = onEvent,
                isEmailIdValid = isEmailIdValid,
                isPasswordValid = isPasswordValid
            )
            Spacer(modifier = modifier.padding(4.dp))
            LoginButton(
                onEvent = onEvent,
            )
            Spacer(modifier = modifier.padding(4.dp))
        }
    }
}

@Preview
@Composable
private fun InstitutionChooseCardPreview() {
    AppName()
}

@Composable
fun AppName(
    modifier: Modifier = Modifier,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        border = BorderStroke(.5.dp, color = Color.LightGray),
        modifier = modifier.padding(horizontal = 10.dp, vertical = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.app_name),
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Preview
@Composable
private fun MailIdAndPasswordInputFieldsPreview() {
    EmailIdAndPasswordInputFields(
        emailId = "",
        password = "",
        onEvent = {},
        isEmailIdValid = true,
        isPasswordValid = false
    )
}

@Composable
fun EmailIdAndPasswordInputFields(
    modifier: Modifier = Modifier,
    emailId: String,
    password: String,
    isEmailIdValid: Boolean,
    isPasswordValid: Boolean,
    onEvent: (LoginEvents) -> Unit
) {
    val emailInteractionSource = remember { MutableInteractionSource() }
    val passwordInteractionSource = remember { MutableInteractionSource() }
    val keyboardController = LocalSoftwareKeyboardController.current
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth(),
            value = emailId,
            onValueChange = {
                onEvent(LoginEvents.OnEmailIdChanged(it))
            },
            interactionSource = emailInteractionSource,
            visualTransformation = VisualTransformation.None,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.str_email_placeholder),
                    fontSize = 12.sp,
                    color = lightGrey,
                )
            },
            prefix = {
                Icon(
                    Icons.Default.PersonOutline,
                    contentDescription = "search",
                    modifier = Modifier
                        .size(20.dp),
                    tint = Grey30
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
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = darkBlue,
                unfocusedBorderColor = Color.LightGray,
                errorBorderColor = redAccent100,
                errorCursorColor = redAccent100,
                focusedContainerColor = DarkBlue96,
                unfocusedContainerColor = DarkBlue96,
                errorContainerColor = Color.White,
                cursorColor = Color.Black
            ),
            shape = RoundedCornerShape(10.dp),
            textStyle = TextStyle(
                fontSize = 12.sp,
                color = Color.Black
            ),
            supportingText = {
                AnimatedVisibility(visible = !isEmailIdValid) {
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .offset(x = (-10).dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            Icons.Default.Warning,
                            contentDescription = "warning",
                            modifier = modifier
                                .size(10.dp),
                            tint = redAccent100
                        )
                        Text(
                            text = stringResource(id = R.string.str_email_error),
                            fontSize = 10.sp,
                            modifier = Modifier.padding(horizontal = 2.dp),
                            color = redAccent100
                        )
                    }
                }
            },
            isError = !isEmailIdValid
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth(),
            value = password,
            onValueChange = {
                onEvent(LoginEvents.OnPasswordChanged(it))
            },
            interactionSource = passwordInteractionSource,
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.str_password_placeholder),
                    fontSize = 12.sp,
                    color = lightGrey,
                )
            },
            prefix = {
                Icon(
                    Icons.Outlined.Lock,
                    contentDescription = "search",
                    modifier = Modifier
                        .size(20.dp),
                    tint = Grey30
                )
            },
            suffix = {
                IconButton(
                    onClick = {
                        isPasswordVisible = !isPasswordVisible
                    },
                    modifier = Modifier.size(22.dp)
                ) {
                    AnimatedContent(
                        targetState = isPasswordVisible,
                        label = "password"
                    ) { isShow ->
                        if (isShow) {
                            Icon(
                                Icons.Default.Visibility,
                                contentDescription = "showPassword",
                                tint = Color.Black
                            )
                        } else {
                            Icon(
                                Icons.Default.VisibilityOff,
                                contentDescription = "showPassword",
                                tint = Color.Black
                            )
                        }
                    }
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = darkBlue,
                unfocusedBorderColor = Color.LightGray,
                errorBorderColor = redAccent100,
                errorCursorColor = redAccent100,
                focusedContainerColor = DarkBlue96,
                unfocusedContainerColor = DarkBlue96,
                errorContainerColor = Color.White,
                cursorColor = Color.Black
            ),
            shape = RoundedCornerShape(10.dp),
            textStyle = TextStyle(
                fontSize = 12.sp,
                color = Color.Black
            ),
            isError = !isPasswordValid,
            supportingText = {
                AnimatedVisibility(visible = !isPasswordValid) {
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .offset(x = (-10).dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            Icons.Default.Warning,
                            contentDescription = "warning",
                            modifier = modifier
                                .size(10.dp),
                            tint = redAccent100
                        )
                        Text(
                            text = stringResource(id = R.string.str_password_error),
                            fontSize = 10.sp,
                            modifier = Modifier.padding(horizontal = 2.dp),
                            color = redAccent100
                        )
                    }
                }
            }
        )
    }
}

@Preview
@Composable
private fun LoginButtonPreview() {
    LoginButton(
        onEvent = {},
    )
}

@Composable
fun LoginButton(
    modifier: Modifier = Modifier,
    onEvent: (LoginEvents) -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary
        ),
        onClick = {
            onEvent(LoginEvents.OnSubmitClicked())
        },
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(
            text = stringResource(id = R.string.str_login),
            modifier = Modifier.padding(8.dp),
            fontSize = 14.sp,
            color = Color.White
        )
    }
}

