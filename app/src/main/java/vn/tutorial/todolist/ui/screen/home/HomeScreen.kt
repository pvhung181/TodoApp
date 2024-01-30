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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
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

    val scroll = rememberScrollState()

    Box(modifier = Modifier
        .padding(paddingValue)
        .verticalScroll(scroll)
    ) {
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

            Spacer(modifier = Modifier.padding(24.dp))

            CategoryTaskItem(
                image = painterResource(id = R.drawable.sun_transparent),
                title = "Today",
                amountOfTasks = 1,
                modifier = Modifier.fillMaxWidth()
            )

            CategoryTaskItem(
                image = painterResource(id = R.drawable.calendar),
                title = "Planned",
                amountOfTasks = 1,
                modifier = Modifier.fillMaxWidth()
            )

            CategoryTaskItem(
                image = painterResource(id = R.drawable.person),
                title = "Personal",
                amountOfTasks = 1,
                modifier = Modifier.fillMaxWidth()
            )

            CategoryTaskItem(
                image = painterResource(id = R.drawable.document),
                title = "Work",
                amountOfTasks = 1,
                modifier = Modifier.fillMaxWidth()
            )

            CategoryTaskItem(
                image = painterResource(id = R.drawable.shopping),
                title = "Shopping",
                amountOfTasks = 1,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Composable
fun CategoryTaskItem(
    image: Painter,
    title: String,
    amountOfTasks: Int,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { /*TODO*/ },
        modifier = modifier
            .padding(16.dp)
            .height(dimensionResource(id = R.dimen.height_of_category_item)),
        shape = RoundedCornerShape(12.dp)
    ) {
            Row (
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Image(
                    painter = image,
                    contentDescription = null,
                    modifier = Modifier
                        .width(60.dp)
                        .padding(end = 16.dp)
                )

                Column (
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                ){
                    Text(
                        text = title,
                        style = MaterialTheme.typography.displayMedium
                    )

                    Text(
                        text = "$amountOfTasks tasks",
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                }

            }
    }
}

@Composable
fun UserInformation(
    modifier: Modifier = Modifier
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
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
fun BottomAppBar(
    modifier: Modifier = Modifier,
    currentScreen: String = Screens.Home.name
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .shadow(1.dp),
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
