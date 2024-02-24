package vn.tutorial.todolist.ui.screen.start

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import vn.tutorial.todolist.R
import vn.tutorial.todolist.data.getCategory
import vn.tutorial.todolist.ui.AppViewModelProvider
import vn.tutorial.todolist.ui.navigation.NavigationDestination
import vn.tutorial.todolist.ui.theme.Shapes
import vn.tutorial.todolist.util.miliToLocalDate
import java.sql.Date

object UserCollectionScreen : NavigationDestination {
    override val route: String
        get() = "userInfo"
    override val titleRes: Int
        get() = R.string.user_info
}

object StartScreen : NavigationDestination {
    override val route: String
        get() = "start"
    override val titleRes: Int
        get() = R.string.start

}


@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    navigateToUserInfoCollection: () -> Unit
) {
    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(top = 128.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.welcome),
                contentDescription = null
            )

            Spacer(modifier = Modifier.padding(32.dp))

            Text(
                text = "Welcome",
                style = MaterialTheme.typography.displayMedium
            )

            Text(text = "Build a to-do list for yourself ")

            Spacer(modifier = Modifier.padding(64.dp))


            Button(
                onClick = navigateToUserInfoCollection
            ) {
                Text(text = "Get Started")
                Spacer(modifier = Modifier.padding(start = 8.dp))
                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectUserInfoScreen(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit,
    navigateUp: () -> Unit,
    viewModel: StartViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = AppViewModelProvider.Factory)
) {

    val birthDayState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis()
    )
    val coroutineScope = rememberCoroutineScope()
    var openDialog by remember {
        mutableStateOf(false)
    }
    val uiState by viewModel.uiState
    var previousSelectedDate by remember {
        mutableLongStateOf(System.currentTimeMillis())
    }

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) {
        if(it != null) {
            viewModel.updateUiState(
                uiState.userInfo.copy(
                    avatar = it.toString()
                )
            )
        }
    }

    Scaffold(
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = navigateUp) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }

                Text(
                    text = "User Information",
                    style = MaterialTheme.typography.displayMedium
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
                .padding(8.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(Uri.parse(uiState.userInfo.avatar))
                        .placeholder(R.drawable.default_avatar)
                        .error(R.drawable.default_avatar)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .clip(shape = Shapes.small)
                        .clickable {
                            photoPicker.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        },
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.default_avatar)
                )
            }

            Text(text = uiState.userInfo.avatar)

            OutlinedTextFieldWithLeadingIcons(
                value = uiState.userInfo.userName,
                onValueChange = {name ->
                    if(name.length < StartViewModel.LIMIT_CHAR) {
                        viewModel.updateUiState(
                            uiState.userInfo.copy(
                                userName = name
                            )
                        )
                    }
                },
                leadingIcons = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(36.dp)
                    )
                },
                label = {
                    Text(text = "Username")
                },
                modifier = Modifier.padding(8.dp),
                isMandatory = true,
                trailingIcon = {
                    Text(text = "${uiState.userInfo.userName.length}/${StartViewModel.LIMIT_CHAR}")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            OutlinedTextFieldWithLeadingIcons(
                value = Date(birthDayState.selectedDateMillis!!).toString(),
                onValueChange = {},
                leadingIcons = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_cake_24),
                        contentDescription = null,
                        modifier = Modifier.size(36.dp)
                    )
                },
                label = {
                    Text(text = "Birth Day")
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            openDialog = true
                        }
                    )
                },
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        openDialog = true
                    },
                isReadonly = true,
                isMandatory = true,
                enable = false
            )

            OutlinedTextFieldWithLeadingIcons(
                value = uiState.userInfo.email,
                onValueChange = {mail ->
                    viewModel.updateUiState(
                        uiState.userInfo.copy(
                            email = mail
                        )
                    )
                },
                leadingIcons = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null,
                        modifier = Modifier.size(36.dp)
                    )
                },
                label = {
                    Text(text = "Email")
                },
                modifier = Modifier.padding(8.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            OutlinedTextFieldWithLeadingIcons(
                value = uiState.userInfo.address,
                onValueChange = {address ->
                    viewModel.updateUiState(
                        uiState.userInfo.copy(
                            address = address
                        )
                    )

                },
                leadingIcons = {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(36.dp)
                    )
                },
                label = {
                    Text(text = "Address")
                },
                modifier = Modifier.padding(8.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            OutlinedTextFieldWithLeadingIcons(
                value = uiState.userInfo.city,
                onValueChange = {city ->
                    viewModel.updateUiState(
                        uiState.userInfo.copy(
                            city = city
                        )
                    )

                },
                leadingIcons = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null,
                        modifier = Modifier.size(36.dp),
                        tint = Color.White
                    )
                },
                label = {
                    Text(text = "City")
                },
                modifier = Modifier.padding(8.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState.isValid,
                onClick = {
                    runBlocking {
                        coroutineScope.launch {
                            getCategory().forEach { category ->
                                viewModel.insertCategories(category)
                            }
                        }

                        coroutineScope.launch {
                            viewModel.insertUser(uiState.userInfo.toUser())
                        }
                    }

                    viewModel.updateVisitedUser()

                    navigateToHome()
                }) {
                Text(text = "Done")
            }
        }
    }

    if (openDialog) {
        DatePickerDialog(
            onDismissRequest = {
                openDialog = false
                birthDayState.setSelection(previousSelectedDate)
                viewModel.updateUiState(
                    userInfo = uiState.userInfo.copy(
                        birthDay = miliToLocalDate(birthDayState.selectedDateMillis!!)
                    )
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    openDialog = false
                    previousSelectedDate = birthDayState.selectedDateMillis!!
                    viewModel.updateUiState(
                        userInfo = uiState.userInfo.copy(
                            birthDay = miliToLocalDate(birthDayState.selectedDateMillis!!)
                        )
                    )
                }) {
                    Text(text = "Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    openDialog = false
                    birthDayState.setSelection(previousSelectedDate)
                    viewModel.updateUiState(
                        userInfo = uiState.userInfo.copy(
                            birthDay = miliToLocalDate(birthDayState.selectedDateMillis!!)
                        )
                    )
                }) {
                    Text(text = "No")
                }
            }
        ) {
            DatePicker(
                state = birthDayState,
                showModeToggle = false
            )
        }
    }
}

@Composable
fun OutlinedTextFieldWithLeadingIcons(
    value: String = "",
    onValueChange: (String) -> Unit,
    leadingIcons: @Composable () -> Unit = {},
    trailingIcon: @Composable () -> Unit = {},
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    isReadonly: Boolean = false,
    isMandatory: Boolean = false,
    singleLine: Boolean = false,
    label: @Composable () -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Default)
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        leadingIcons()

        Spacer(modifier = Modifier.padding(start = 16.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = label,
            trailingIcon = trailingIcon,
            enabled = enable,
            readOnly = isReadonly,
            singleLine = singleLine,
            keyboardOptions = keyboardOptions
        )

        if(isMandatory) {
            Text(
                text = "*",
                color = Color.Red,
                modifier = Modifier
                    .size(36.dp)
                    .padding(start = 8.dp)
            )
        }
    }
}

@Composable
@Preview(
    showBackground = true,
    showSystemUi = true
)
fun CollectUserPreview() {
    CollectUserInfoScreen(
        navigateToHome = {},
        navigateUp = {}
    )
}