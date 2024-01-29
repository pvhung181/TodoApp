package vn.tutorial.todolist.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import vn.tutorial.todolist.R
import vn.tutorial.todolist.enums.Screens
import vn.tutorial.todolist.ui.theme.Shapes

@Composable
fun HomeScreen (
    modifier: Modifier = Modifier,
    paddingValue: PaddingValues
) {
    Box(modifier = Modifier) {

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(360.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(
                        bottomStart = 80.dp,
                        bottomEnd = 80.dp
                    )
                )
        )

        Column {
            UserInformation()
        }
    }
}

@Composable
fun BottomAppBar(
    modifier: Modifier = Modifier,
    currentScreen: String = Screens.Home.name
) {
    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ){
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = null,
                modifier = Modifier.size(
                    dimensionResource(id = R.dimen.size_of_icon)
                ),
                tint = if(currentScreen == Screens.Home.name) MaterialTheme.colorScheme.primary else Color.Black
            )
        }

        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .size(72.dp)
                .padding(12.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.medium
                )
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }



        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = Modifier.size(dimensionResource(id = R.dimen.size_of_icon)),
                tint = if(currentScreen == Screens.User.name) MaterialTheme.colorScheme.secondary else Color.Black
            )
        }
    }
}


@Composable
fun UserInformation(
    modifier: Modifier = Modifier
) {
    Row (
        modifier = Modifier.fillMaxWidth()
            .padding(top = 48.dp),
        horizontalArrangement = Arrangement.Absolute.SpaceAround
    ){
        Column {
            Text(
                text = stringResource(id = R.string.hello_user, "User"),
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(bottom = 6.dp)
            )

            Text(
                text = stringResource(id = R.string.tasks_today, 2),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        Image(
            painter = painterResource(id = R.drawable.sample_avatar),
            contentDescription = null,
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .clip(shape = Shapes.small),
            contentScale = ContentScale.Crop
        )

    }
}

@Composable
@Preview(
    showBackground = true
)
fun BottomAppBarPreview() {
    BottomAppBar()
}

@Composable
@Preview(
    showBackground = true,
    showSystemUi = true
)
fun HomeScreenPreview() {
    Scaffold(
        topBar = {

        },
        bottomBar = {
            BottomAppBar()
        }
    ) {
        HomeScreen(
            paddingValue = it
        )
    }
}
