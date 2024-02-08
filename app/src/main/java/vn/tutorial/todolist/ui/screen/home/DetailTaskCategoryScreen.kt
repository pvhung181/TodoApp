package vn.tutorial.todolist.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import vn.tutorial.todolist.model.Task
import vn.tutorial.todolist.ui.AppViewModelProvider
import vn.tutorial.todolist.ui.navigation.NavigationDestination
import vn.tutorial.todolist.ui.screen.add.TopAppBar
import vn.tutorial.todolist.uitilities.prettierLocalDateTime


object DetailTaskCategory : NavigationDestination {
    override val route: String
        get() = "detailTaskCategory/{categoryTitle}"
    override val titleRes: Int
        get() = TODO("Not yet implemented")
    const val root = "detailTaskCategory"
}

enum class CategoryTitle(val title: String) {
    TODAY("Today"),
    PLANNED("Planned"),
    WORK("Work"),
    PERSONAL("Personal"),
    SHOPPING("Shopping")
}

@Composable
fun DetailTaskCategoryScreen(
    navigateBack: () -> Unit,
    title: String,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {

    val personalUiState = viewModel.personalTasks.collectAsState()
    val workUiState = viewModel.workTasks.collectAsState()
    val shoppingUiState = viewModel.shoppingTasks.collectAsState()

    when (title) {
        CategoryTitle.TODAY.name -> TodayCategoryScreen(
            title = CategoryTitle.TODAY.title,
            tasks = workUiState.value.tasks,
            navigateBack = navigateBack
        )
        CategoryTitle.PLANNED.name -> PlannedCategoryScreen(
            title = CategoryTitle.PLANNED.title,
            navigateBack = navigateBack
        )
        CategoryTitle.PERSONAL.name -> CategoryDetailScreen(
            title = CategoryTitle.PERSONAL.title,
            tasks = personalUiState.value.tasks,
            navigateBack = navigateBack
        )
        CategoryTitle.WORK.name -> CategoryDetailScreen(
            title = CategoryTitle.WORK.title,
            tasks = workUiState.value.tasks,
            navigateBack = navigateBack
        )
        CategoryTitle.SHOPPING.name -> CategoryDetailScreen(
            title = CategoryTitle.SHOPPING.title,
            tasks = shoppingUiState.value.tasks,
            navigateBack = navigateBack
        )
    }

}

@Composable
fun TodayCategoryScreen(
    title: String,
    tasks: List<Task> = listOf(),
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(navigateBack = navigateBack, title = title)
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it)
        ) {
            items(tasks) {
                task ->
                TaskItem(task = task)
            }
        }
    }
}

@Composable
fun PlannedCategoryScreen(
    title: String,
    tasks: List<Task> = listOf(),
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(navigateBack = navigateBack, title = title)
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it)
        ) {
            items(tasks) {
                    task ->
                TaskItem(task = task)
            }
        }
    }
}

@Composable
fun CategoryDetailScreen(
    title: String,
    tasks: List<Task> = listOf(),
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(navigateBack = navigateBack, title = title)
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it)
        ) {
            items(tasks) {
                    task ->
                TaskItem(task = task)
            }
        }
    }
}


@Composable
fun TaskItem(
    task: Task,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = false, onCheckedChange = {})
            Column {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.displayMedium
                )
                Text(text = task.description)
                Text(text = "Duration : ${prettierLocalDateTime(task.dateBegin)} - ${prettierLocalDateTime(task.dateEnd)}")

            }

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    imageVector = Icons.TwoTone.Delete,
                    contentDescription = null,
                    tint = Color.Red
                )
            }
        }
    }
}

@Composable
@Preview(
    showBackground = true
)
fun DetailScreenPreview(

) {
    DetailTaskCategoryScreen(navigateBack = { /*TODO*/ }, title = "")
}