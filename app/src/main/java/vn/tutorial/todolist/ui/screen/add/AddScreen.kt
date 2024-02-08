package vn.tutorial.todolist.ui.screen.add

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import vn.tutorial.todolist.R
import vn.tutorial.todolist.data.getCategory
import vn.tutorial.todolist.model.Category
import vn.tutorial.todolist.ui.navigation.NavigationDestination

object AddScreen : NavigationDestination {
    override val route = "add"
    override val titleRes = R.string.add_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigateBack = navigateBack,
                title = stringResource(id = AddScreen.titleRes)
            )
        }
    ) {
        val categories = getCategory()
        val tog = remember {
            mutableStateOf(false)
        }
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
        ) {
            TitleOfTask()
            TitleOfTask()
            CategoryOfTasks(
                list = categories
            )

            DatePickerDialog(
                onDismissRequest = {
                    tog.value = false
                },
                confirmButton = {
                    Button(onClick = {
                        tog.value = !tog.value
                    }) {
                        Text(text = if (!tog.value) "Calendar" else "Ok")
                    }


                }
            ) {
                if(tog.value) {
                    DatePicker(
                        state = rememberDatePickerState(),
                        headline = {
                            Text(text = "Select date start")
                        },
                        title = {
                            Text(text = "This is title")
                        }
                    )
                }

            }
        }
    }
}

@Composable
fun TitleOfTask(
    modifier: Modifier = Modifier
) {
    Column {
        Text(text = "Title")

        OutlinedTextField(value = "", onValueChange = {})
    }


}

@Composable
fun CategoryOfTasks(
    list: List<Category>,
    modifier: Modifier = Modifier
) {
    Text(text = "Category")

    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 3)
    ) {
        items(list) {
            Category(title = it.title)
        }
    }


}

@Composable
fun Category(
    title: String,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = Modifier
            .padding(16.dp)
            .size(68.dp)
            .clickable {

            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.secondary),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment =  Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}

@Composable
fun TopAppBar(
    navigateBack: () -> Unit,
    title: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { navigateBack() }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
        }

        Text(text = title)
    }
}

@Composable
@Preview(
    showBackground = true
)
fun AddScreenPreview(

){
    AddScreen(
        {}
    )
}