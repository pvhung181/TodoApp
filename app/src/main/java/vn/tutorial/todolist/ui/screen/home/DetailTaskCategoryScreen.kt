package vn.tutorial.todolist.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import vn.tutorial.todolist.R
import vn.tutorial.todolist.model.Task
import vn.tutorial.todolist.ui.AppViewModelProvider
import vn.tutorial.todolist.ui.navigation.NavigationDestination
import vn.tutorial.todolist.ui.screen.add.TopAppBar
import vn.tutorial.todolist.util.localDateTimeToDate
import vn.tutorial.todolist.util.prettierLocalDateTime
import java.sql.Date
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


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

    val coroutineScope = rememberCoroutineScope()
    val personalUiState = viewModel.personalTasks.collectAsState()
    val workUiState = viewModel.workTasks.collectAsState()
    val shoppingUiState = viewModel.shoppingTasks.collectAsState()
    val allTasks = viewModel.allTasks.collectAsState()


    when (title) {
        CategoryTitle.TODAY.title -> TodayCategoryScreen(
            title = CategoryTitle.TODAY.title,
            tasks = allTasks.value.tasks.filter {
                val date = Date(System.currentTimeMillis())
                date.toString() == localDateTimeToDate(it.dateBegin).toString()
            }.sortedWith(compareBy {it.dateBegin} ),
            navigateBack = navigateBack,
            onDelete = { task ->
                coroutineScope.launch {
                    viewModel.deleteTask(task)
                }
            },
            onCheckChange = { v, t ->
                if(!t.dateEnd.isBefore(LocalDateTime.now())) {
                    coroutineScope.launch {
                        viewModel.updateTask(t.copy(isCompleted = v))


                    }
                }
            }

        )

        CategoryTitle.PLANNED.title -> CategoryDetailScreen(
            title = CategoryTitle.PLANNED.title,
            navigateBack = navigateBack,
            tasks = allTasks.value.tasks,
            onDelete = { task ->
                coroutineScope.launch {
                    viewModel.deleteTask(task)
                }
            },
            onCheckChange = { v, t ->
                if(!t.dateEnd.isBefore(LocalDateTime.now())) {
                    coroutineScope.launch {
                        viewModel.updateTask(t.copy(isCompleted = v))
                    }
                }
            }
        )

        else -> CategoryDetailScreen(
            title = title,
            tasks = when(title) {
                CategoryTitle.PERSONAL.title -> personalUiState.value.tasks
                CategoryTitle.WORK.title -> workUiState.value.tasks
                else -> shoppingUiState.value.tasks
            },
            navigateBack = navigateBack,
            onDelete = { task ->
                coroutineScope.launch {
                    viewModel.deleteTask(task)
                }
            },
            onCheckChange = { v, t ->
                if(!t.dateEnd.isBefore(LocalDateTime.of(LocalDate.now(), LocalTime.of(LocalTime.now().hour, LocalTime.now().minute)))) {
                    coroutineScope.launch {
                        viewModel.updateTask(t.copy(isCompleted = v))
                    }
                }
            }
        )
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDetailScreen(
    title: String,
    tasks: List<Task> = listOf(),
    onDelete: (Task) -> Unit,
    navigateBack: () -> Unit,
    onCheckChange: (Boolean, Task) -> Unit,
    modifier: Modifier = Modifier
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis()
    )
    var openDialog by remember {
        mutableStateOf(false)
    }
    var previousSelectedDate by remember {
        mutableLongStateOf(System.currentTimeMillis())
    }

    Scaffold(
        topBar = {
            TopAppBar(navigateBack = navigateBack, title = title)
        }
    ) {
        Column(
            modifier = modifier
                .padding(it)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .border(
                        shape = RoundedCornerShape(16.dp),
                        width = 1.dp,
                        color = Color.Gray
                    )
                    .padding(16.dp)
                    .clickable {
                        openDialog = true
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = Date(datePickerState.selectedDateMillis!!).toString()
                )

                Spacer(modifier = Modifier.padding(2.dp))

                Icon(imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = null)
            }

            if(tasks.none { item ->
                    Date(datePickerState.selectedDateMillis!!).toString() ==
                            localDateTimeToDate(item.dateBegin).toString()
                }) {
                Image(
                    painter = painterResource(id = R.drawable.no_tasks_background),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
            else {
                LazyColumn {
                    items(tasks.filter {item ->
                        Date(datePickerState.selectedDateMillis!!).toString() ==
                                localDateTimeToDate(item.dateBegin).toString()
                    }) { task ->
                        TaskItem(
                            task = task,
                            onDelete = {
                                onDelete(task)
                            },
                            onCheckChange = {v ->
                                onCheckChange(v, task)
                            }
                        )
                    }
                }
            }
        }

    }

    if(openDialog) {
        DatePickerDialog(
            onDismissRequest = {
                openDialog = false

            },
            confirmButton = {
                TextButton(onClick = {
                    openDialog = false
                    previousSelectedDate = datePickerState.selectedDateMillis!!
                }) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    openDialog = false
                    datePickerState.setSelection(previousSelectedDate)
                }) {
                    Text(text = "No")
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                showModeToggle = false
            )
        }
    }
}

@Composable
fun TodayCategoryScreen(
    title: String,
    tasks: List<Task> = listOf(),
    navigateBack: () -> Unit,
    onDelete: (Task) -> Unit,
    onCheckChange: (Boolean, Task) -> Unit,
    modifier: Modifier = Modifier
) {

    Scaffold(
        topBar = {
            TopAppBar(navigateBack = navigateBack, title = title)
        }
    ) {
        if(tasks.isEmpty()) {
            Image(
                painter = painterResource(id = R.drawable.no_tasks_background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
        else {
            LazyColumn(
                modifier = Modifier.padding(it)
            ) {
                items(tasks) { task ->
                    TaskItem(
                        task = task,
                        onDelete = {
                            onDelete(task)
                        },
                        onCheckChange = {v ->
                            onCheckChange(v, task)
                        }
                    )
                }
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
        Column(
            modifier = modifier.padding(it)
        ) {

            LazyColumn(
                modifier = Modifier.padding(it)
            ) {
                items(tasks) { task ->
                    TaskItem(
                        task = task,
                        onDelete = {}
                    )
                }
            }
        }

    }
}

@Composable
fun TaskItem(
    task: Task,
    onDelete: () -> Unit,
    onCheckChange: (Boolean) -> Unit = {},
    modifier: Modifier = Modifier
) {

    var openDialog by rememberSaveable {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(
                color = if(task.isCompleted) colorResource(id = R.color.little_green)
                else colorResource(id = R.color.little_red),
                width = 1.dp
            )
    ) {
        Row(
//            modifier = Modifier
//                .background(
//                    color = if(task.isCompleted) colorResource(id = R.color.little_green)
//                    else colorResource(id = R.color.little_red)
//                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = onCheckChange
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.displayMedium,
                    textDecoration = if(task.isCompleted) TextDecoration.LineThrough else null
                )
                Text(
                    text = task.description,
                    textDecoration = if(task.isCompleted) TextDecoration.LineThrough else null
                )
                Text(
                    text = "Duration : ${prettierLocalDateTime(task.dateBegin)} - ${
                        prettierLocalDateTime(
                            task.dateEnd
                        )
                    }",
                    textDecoration = if(task.isCompleted) TextDecoration.LineThrough else null
                )

            }

            //Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = {
                    openDialog = true
                }
            ) {
                Icon(
                    imageVector = Icons.TwoTone.Delete,
                    contentDescription = null,
                    tint = Color.Red
                )
            }

            if(openDialog) {
                AlertDialog(
                    onDismissRequest = { openDialog = false },
                    confirmButton = {
                        IconButton(onClick = {
                            onDelete()
                            openDialog = false
                        }) {
                            Text(text = "Yes")
                        }
                    },
                    dismissButton = {
                        IconButton(onClick = {openDialog = false}) {
                            Text(text = "No")
                        }
                    },
                    title = {
                        Text(text = "Do you want to delete ?")
                    },
                    icon = {
                        Icon(imageVector = Icons.Default.Warning, contentDescription = null)
                    }

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