package vn.tutorial.todolist.ui.screen.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import vn.tutorial.todolist.R
import vn.tutorial.todolist.ui.navigation.NavigationDestination
import vn.tutorial.todolist.ui.theme.Shapes


object SettingScreen : NavigationDestination {
    override val route = "setting"
    override val titleRes = R.string.setting_title
}

@Composable
fun UserScreen(
    navigateToHome: () -> Unit,
    navigateToAdd: () -> Unit,
    navigateToSetting: () -> Unit,
    modifier: Modifier = Modifier
) {
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
            Image(
                painter = painterResource(id = R.drawable.sample_avatar),
                contentDescription = null,
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
                    .clip(shape = Shapes.small)
            )

            Text(
                text = "User Name",
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),

                )


            Button(onClick = { /*TODO*/ }) {
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp)
            )
        }
    }
}

@Composable
fun Settings(
    modifier: Modifier = Modifier
) {
    Column(

    ) {
        Card (
            modifier = modifier,
        ){
            SwitchSettingItem(
                icon = painterResource(id = R.drawable.baseline_dark_mode_24),
                title = stringResource(id = R.string.night_mode),
                onCheckedChange = {
                }
            )

            SwitchSettingItem(
                icon = painterResource(id = R.drawable.notifications_active_24),
                title = stringResource(id = R.string.notification),
                onCheckedChange = {}
            )

        }



        Card(
            modifier = modifier
        ) {
            GotoSettingItem(
                icon = painterResource(id = R.drawable.mail_24),
                title = stringResource(id = R.string.send_us_a_message),
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )

            GotoSettingItem(
                icon = painterResource(id = R.drawable.info_24),
                title = stringResource(id = R.string.about_us),
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )
        }

        Card(
            modifier = modifier
        ) {
            GotoSettingItem(
                icon = painterResource(id = R.drawable.logout_24),
                title = stringResource(id = R.string.log_out),
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)

            )
        }
    }
}

@Composable
fun SwitchSettingItem(
    icon: Painter,
    title: String,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val check = remember{
        mutableStateOf(false)
    }
    Row (
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
        )

        Text(text = title)

        Spacer(modifier = Modifier.weight(1f))

        Switch(
            checked = check.value,
            onCheckedChange = {
                check.value = !check.value
            },
            modifier = Modifier.padding(start = 8.dp, end = 8.dp))

    }
}

@Composable
fun GotoSettingItem(
    icon: Painter,
    title: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
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
        {},{},{}
    )
}