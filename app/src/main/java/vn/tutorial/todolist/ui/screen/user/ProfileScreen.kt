package vn.tutorial.todolist.ui.screen.user

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import vn.tutorial.todolist.R
import vn.tutorial.todolist.ui.AppViewModelProvider
import vn.tutorial.todolist.ui.navigation.NavigationDestination
import vn.tutorial.todolist.ui.screen.start.OutlinedTextFieldWithLeadingIcons
import vn.tutorial.todolist.ui.theme.Shapes

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
    val user by viewModel.user.collectAsState()

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

            Divider(
                modifier = Modifier.padding(8.dp)
            )

            Column {
                Text(text = stringResource(id = R.string.total_tasks, 1))
                Text(text = stringResource(id = R.string.task_completed, 2))
                Text(text = stringResource(id = R.string.task_coming, 3))
            }

            OutlinedTextFieldWithLeadingIcons(
                value = user.fullName,
                onValueChange = {

                },
                label = {
                    Text(text = "Username")
                },
                leadingIcons = {
                    Icon(imageVector = Icons.Default.Person, contentDescription = null)
                },
                enable = false  
            )

            OutlinedTextFieldWithLeadingIcons(
                value = user.birthDay.toString(),
                onValueChange = {},
                label = {
                    Text(text = "Birth day")
                },
                leadingIcons = {
                    Icon(painter = painterResource(id = R.drawable.baseline_cake_24), contentDescription = null)
                },
                enable = false
                )

            OutlinedTextFieldWithLeadingIcons(
                value = "",
                onValueChange = {},
                label = {
                    Text(text = "Email")
                },
                leadingIcons = {
                    Icon(imageVector = Icons.Default.Email, contentDescription = null)
                },
                enable = false
                )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {

                },
                modifier = modifier.fillMaxWidth()
            ) {
                Text(text = "Save change")
            }
        }
    }
}

@Composable
@Preview
fun ProfilePreview() {
    ProfileScreen(navigateUp = {})
}