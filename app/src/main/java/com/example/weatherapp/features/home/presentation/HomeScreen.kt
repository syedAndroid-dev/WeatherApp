package com.example.weatherapp.features.home.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherapp.core.theme.DarkBlue30
import com.example.weatherapp.core.theme.Green30
import com.example.weatherapp.core.utils.CommonLoader
import kotlinx.coroutines.delay

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        isLoading = true,
        users = arrayListOf(),
        onEvent = { }
    )
}

@Composable
fun HomeScreenRoute(
    homeScreenViewModel: HomeViewModel = hiltViewModel()
) {
    val homeScreenState by homeScreenViewModel.homeScreenState.collectAsStateWithLifecycle()

    HomeScreen(
        isLoading = homeScreenState.isLoading,
        users = homeScreenState.users,
        onEvent = homeScreenViewModel::onEvent
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    users: List<UserDetails>,
    onEvent: (HomeEvents) -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            Card(
                colors = CardDefaults.cardColors(containerColor = Green30),
                elevation = CardDefaults.cardElevation(20.dp)
            ) {
                IconButton(
                    onClick = {
                        onEvent(HomeEvents.OnAddUsers)
                    },
                    modifier = Modifier
                        .size(50.dp)
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "AddUser",
                        tint = Color.White
                    )
                }
            }
        }
    ) { scaffoldPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(bottom = scaffoldPadding.calculateBottomPadding())
        ) {
            CommonLoader(isLoading = isLoading)
            Card(
                modifier = modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = DarkBlue30,
                )
            ) {
                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    text = "User Details",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = modifier.padding(5.dp))
            LazyColumn(
                modifier = modifier.weight(1f)
            ) {
                items(
                    items = users,
                    key = { it.userId }
                    ) { user ->
                    SwipeToDeleteContainer(
                        item = user,
                        onDeleteItem = {
                            onEvent(HomeEvents.OnRemoveUser(userId = user.userId))
                        },
                        content = {
                            UserDetail(
                                userDetails = user,
                                onEvent = onEvent
                            )
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserDetailPreview(modifier: Modifier = Modifier) {
    UserDetail(
        userDetails = UserDetails(
            userId = 1,
            userName = "Syed",
            userEmail = "syedabuthahir304@gmail.com"
        ),
        onEvent = { }
    )
}

@Composable
fun UserDetail(
    modifier: Modifier = Modifier,
    userDetails: UserDetails,
    onEvent: (HomeEvents) -> Unit
) {
    Card(
        onClick = {
            onEvent(HomeEvents.OnGetWeatherDetails)
        },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(3.dp),
        modifier = modifier.padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                shape = CircleShape,
                colors = CardDefaults.cardColors(
                    containerColor = Color.LightGray
                )
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "user",
                    modifier = modifier.padding(10.dp)
                )
            }
            Spacer(modifier = modifier.padding(10.dp))
            Column {
                Text(
                    text = userDetails.userName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                    color = Color.Black
                )
                Text(
                    text = userDetails.userEmail,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W400,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun SwipeDeleteBackground(
    modifier: Modifier = Modifier,
    swipeDismiss: SwipeToDismissBoxState
) {
    val color = if (swipeDismiss.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
        Color.Red
    } else {
        Color.Transparent
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color)
            .padding(16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            Icons.Default.Delete,
            contentDescription = "delete"
        )
    }

}

@Composable
fun <T> SwipeToDeleteContainer(
    modifier: Modifier = Modifier,
    item : T,
    onDeleteItem : () -> Unit,
    content : @Composable (T) -> Unit
) {
    var isRemoved by remember { mutableStateOf(false) }
    val swappableState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if(value == SwipeToDismissBoxValue.EndToStart){
                isRemoved = true
                true
            } else {
                false
            }
        }
    )

    LaunchedEffect(key1 = isRemoved) {
        if (isRemoved){
            delay(500)
            onDeleteItem()
        }
    }
    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = 1000),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismissBox(
            state = swappableState,
            backgroundContent = {
                SwipeDeleteBackground(swipeDismiss = swappableState)
            },
            content = {
                content(item)
            },
            enableDismissFromEndToStart = true,
            enableDismissFromStartToEnd = false
        )
    }
}