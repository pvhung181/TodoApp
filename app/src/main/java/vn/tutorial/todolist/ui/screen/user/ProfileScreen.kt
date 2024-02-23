package vn.tutorial.todolist.ui.screen.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import vn.tutorial.todolist.R
import vn.tutorial.todolist.ui.AppViewModelProvider
import vn.tutorial.todolist.ui.navigation.NavigationDestination
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
               Image(
                   painter = painterResource(id = R.drawable.sample_avatar),
                   contentDescription = null,
                   modifier = Modifier
                       .width(100.dp)
                       .height(100.dp)
                       .clip(shape = Shapes.small),
                   contentScale = ContentScale.Crop
               )

               Text(
                   text = "Change avatar",
                   textDecoration = TextDecoration.Underline,
                   modifier = Modifier.padding(4.dp)
               )
           }

            Divider(
                modifier = Modifier.padding(8.dp)
            )

            Column {
                Text(text = stringResource(id = R.string.total_tasks, user.totalTasks))
                Text(text = stringResource(id = R.string.task_completed, user.completedTasks))
                Text(text = stringResource(id = R.string.task_coming, user.comingTasks))
            }

            Text(text = "User name")
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                OutlinedTextField(value = user.fullName, onValueChange = {})
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            }

            Text(text = "Birth day")
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                OutlinedTextField(value = user.birthDay.toString(), onValueChange = {})
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            }

            Button(
                onClick = { /*TODO*/ },
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