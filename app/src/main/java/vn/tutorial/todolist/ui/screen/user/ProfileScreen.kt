package vn.tutorial.todolist.ui.screen.user

import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract.CommonDataKinds.Email
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
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import vn.tutorial.todolist.R
import vn.tutorial.todolist.model.User
import vn.tutorial.todolist.ui.AppViewModelProvider
import vn.tutorial.todolist.ui.navigation.NavigationDestination
import vn.tutorial.todolist.ui.screen.start.OutlinedTextFieldWithLeadingIcons
import vn.tutorial.todolist.ui.screen.start.UserInfo
import vn.tutorial.todolist.ui.screen.start.toUser
import vn.tutorial.todolist.ui.screen.start.toUserInfor
import vn.tutorial.todolist.ui.theme.Shapes
import java.sql.Date

object ProfileScreen : NavigationDestination {
    override val route: String
        get() = "profile"
    override val titleRes: Int
        get() = R.string.profile

}

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    viewModel: SettingScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = AppViewModelProvider.Factory)
) {

    when(viewModel.state.value) {
        ProfileState.LOADING -> LoadingScreen()
        else -> {
            val user = viewModel.userInfo.value

            val coroutineScope = rememberCoroutineScope()

            val context = LocalContext.current

            val photoPicker = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickVisualMedia()
            ) {
                if (it != null) {
                    context.contentResolver.takePersistableUriPermission(
                        it,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )

                    viewModel.updateUserInfor(user.copy(avatar = it.toString()))
                }
            }


            Scaffold(
                topBar = {
                    vn.tutorial.todolist.ui.screen.add.TopAppBar(
                        navigateBack = navigateUp,
                        title = stringResource(id = ProfileScreen.titleRes)
                    )
                }
            ) {
                Column(
                    modifier = modifier
                        .padding(it)
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = user.fullName,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }

                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(Uri.parse(user.avatar))
                                .placeholder(R.drawable.default_avatar)
                                .error(R.drawable.default_avatar)
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp)
                                .clip(shape = Shapes.small),
                            contentScale = ContentScale.Crop,
                            placeholder = painterResource(id = R.drawable.default_avatar)
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Change avatar",
                            textDecoration = TextDecoration.Underline,
                            color = Color.Blue,
                            modifier = Modifier
                                .clickable {
                                    photoPicker.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                }
                        )
                    }


                    Divider(
                        modifier = Modifier.padding(8.dp)
                    )

                    Column(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(
                            text = "* Tasks will be updated at midnight",
                            fontSize = 12.sp,
                            fontStyle = FontStyle.Italic
                        )
                        Text(text = stringResource(id = R.string.total_tasks, user.totalTasks))
                        Text(text = stringResource(id = R.string.task_completed, user.completedTasks))
                        Text(text = stringResource(id = R.string.task_coming, user.comingTasks))
                    }

                    OutlinedTextFieldWithLeadingIcons(
                        value = user.fullName,
                        onValueChange = {name -> viewModel.updateUserInfor(user.copy(fullName = name))},
                        label = {
                            Text(text = "Username")
                        },
                        leadingIcons = {
                            Icon(imageVector = Icons.Default.Person, contentDescription = null)
                        }
                    )

                    OutlinedTextFieldWithLeadingIcons(
                        value = user.birthDay.toString(),
                        onValueChange = {},
                        label = {
                            Text(text = "Birth day")
                        },
                        leadingIcons = {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_cake_24),
                                contentDescription = null
                            )
                        },
                        enable = false
                    )

                    OutlinedTextFieldWithLeadingIcons(
                        value = user.email,
                        onValueChange = {email ->
                            viewModel.updateUserInfor(user.copy(email = email))
                        },
                        label = {
                            Text(text = "Email")
                        },
                        leadingIcons = {
                            Icon(imageVector = Icons.Default.Email, contentDescription = null)
                        }
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = {
                            coroutineScope.launch {
                                with(Dispatchers.IO) {
                                    viewModel.updateUser(user)
                                }
                            }

                            navigateUp()
                        },
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Text(text = "Save change")
                    }
                }
            }
        }
    }


}


@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {

}

@Composable
@Preview
fun ProfilePreview() {
    ProfileScreen(navigateUp = {})
}