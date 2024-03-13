package vn.tutorial.todolist.ui.screen.user

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ShareCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.launch
import vn.tutorial.todolist.R
import vn.tutorial.todolist.ui.AppViewModelProvider
import vn.tutorial.todolist.ui.navigation.NavigationDestination
import vn.tutorial.todolist.ui.theme.Shapes


object SettingScreen : NavigationDestination {
    override val route = "setting"
    override val titleRes = R.string.setting_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(
    navigateToHome: () -> Unit,
    navigateToAdd: () -> Unit,
    navigateToSetting: () -> Unit,
    navigateToAbout: () -> Unit,
    navigateToProfile: () -> Unit,
    viewModel: SettingScreenViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val user by viewModel.user.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            vn.tutorial.todolist.ui.screen.home.BottomAppBar(
                currentScreen = SettingScreen.route,
                navigateToHome = navigateToHome,
                navigateToAdd = navigateToAdd,
                navigateToSetting = navigateToSetting
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
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

            Text(
                text = user.fullName,
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp)
                )


            Button(onClick =  navigateToProfile) {
                Text(
                    text = "Edit profile",
                    modifier = Modifier.padding(end = 16.dp)
                )

                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier
                        .size(16.dp)
                )
            }

            Divider(
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )

            Settings(
                navigateToAbout = navigateToAbout,
                uiState = uiState,
                onSelectedThemeChange = {
                    v ->
                    viewModel.selectTheme(v)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp)
            )

//            Card(onClick = {
//                coroutineScope.launch {
//                    viewModel.dataStoreManager.clearDatastore()
//                }
//            }) {
//                Text(text = "Clear datastore")
//            }
        }
    }
}


@Composable
fun Settings(
    navigateToAbout: () -> Unit,
    uiState: SettingUiState,
    onSelectedThemeChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    var showDialog by rememberSaveable {
        mutableStateOf(false)
    }

    Column {
        Card (
            modifier = modifier,
        ){
            SwitchSettingItem(
                icon = painterResource(id = R.drawable.baseline_dark_mode_24),
                title = stringResource(id = R.string.night_mode),
                checked = uiState.isDarkTheme,
                onCheckedChange = onSelectedThemeChange,
                onClick = {
                    showDialog = true
                }
            )

        }



        Card(
            modifier = modifier
        ) {
            GotoSettingItem(
                icon = painterResource(id = R.drawable.mail_24),
                title = stringResource(id = R.string.send_us_a_message),
                navigate = {
                      ShareCompat.IntentBuilder(context)
                          .setType("message/rfc822")
                          .addEmailTo("pvhung181@gmail.com")
                          .setSubject("Report about TodoApp")
                          .setText("")
                          .setChooserTitle("Choose an app to send email")
                          .startChooser();
                },
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )

            GotoSettingItem(
                icon = painterResource(id = R.drawable.info_24),
                title = stringResource(id = R.string.about_us),
                navigate = navigateToAbout,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )
        }

        Card(
            modifier = modifier
        ) {
            GotoSettingItem(
                icon = painterResource(id = R.drawable.logout_24),
                title = stringResource(id = R.string.log_out),
                navigate = {},
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)

            )
        }
    }

    if(showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = { 
                TextButton(onClick = { showDialog = false }) {
                    Text(text = "Ok")
                }
            },
            title = {
                Text(text = "Inform")
            },
            text = {
                Text(text = "The mode will be applied the next time you open")
            }
        )
    }
}

@Composable
fun SwitchSettingItem(
    onClick: () -> Unit,
    icon: Painter,
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {


    Row (
        modifier = modifier
            .clickable {
                onCheckedChange(!checked)
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
        )

        Text(text = title)

        Spacer(modifier = Modifier.weight(1f))



        Box(
            contentAlignment = Alignment.CenterEnd
        ) {

            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                modifier = modifier
                    .padding(start = 8.dp, end = 8.dp)
            )

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clickable {
                    onCheckedChange(!checked)
                    onClick()
                })
        }

    }
}

@Composable
fun GotoSettingItem(
    icon: Painter,
    title: String,
    navigate: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable { navigate() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
        )

        Text(text = title)

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .padding(end = 8.dp)
        )
    }
}

@Composable
@Preview(
    showBackground = true,
    showSystemUi = true
)
fun UserScreenPreview(
    modifier: Modifier = Modifier
) {
    UserScreen(
        {},{},{},{},{}
    )
}