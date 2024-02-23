package vn.tutorial.todolist.ui.screen.add

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.sharp.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import vn.tutorial.todolist.R
import vn.tutorial.todolist.data.getCategory
import vn.tutorial.todolist.model.Category
import vn.tutorial.todolist.ui.AppViewModelProvider
import vn.tutorial.todolist.ui.navigation.NavigationDestination
import vn.tutorial.todolist.util.dateFormatter
import vn.tutorial.todolist.util.miliToLocalDate
import vn.tutorial.todolist.util.prettierTime
import java.sql.Date
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

object AddScreen : NavigationDestination {
    override val route = "add"
    override val titleRes = R.string.add_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    navigateBack: () -> Unit,
    viewModel: AddTaskViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = AppViewModelProvider.Factory
    ),
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val taskUiState = viewModel.taskUiState
    val user by viewModel.user.collectAsState()

    val startDatePickerState: DatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis()
    )
    val startTimePickerState = rememberTimePickerState(
        initialHour = 0, initialMinute = 0, is24Hour = true
    )
    val endDatePickerState: DatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis()
    )
    val endTimePickerState = rememberTimePickerState(
        initialHour = 0, initialMinute = 0, is24Hour = true
    )

    Scaffold(topBar = {
        TopAppBar(
            navigateBack = navigateBack, title = stringResource(id = AddScreen.titleRes)
        )
    }) {

        val categories = getCategory()

        Column(
            modifier = Modifier
                .padding(it)
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            TitleOfTask(label = "Title",
                isSingleLine = true,
                placeholder = "Enter a title for your task",
                imeAction = ImeAction.Next,
                text = taskUiState.taskDetails.title,
                onChangeValue = { v ->
                    viewModel.updateUiState(
                        taskUiState.taskDetails.copy(title = v)
                    )
                })
            TitleOfTask(label = "Description",
                placeholder = "Enter a description for your task",
                imeAction = ImeAction.Default,
                maxLines = 5,
                modifier = Modifier.height(120.dp),
                text = taskUiState.taskDetails.description,
                onChangeValue = { v ->
                    viewModel.updateUiState(taskUiState.taskDetails.copy(description = v))
                })
            CategoryOfTasks(
                selectedCategory = taskUiState.taskDetails.categoryId, onClick = { v ->
                    viewModel.updateUiState(taskUiState.taskDetails.copy(categoryId = v))
                },
                list = categories
            )

            Text(
                text = "Start Time",
                style = MaterialTheme.typography.displayMedium,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            DateTimePickerUi(
                labelForCLock = "Start with",
                taskDetail = taskUiState.taskDetails,
                onDismissRequest = {
                    val dateBegin = Instant.ofEpochMilli(startDatePickerState.selectedDateMillis!!).atZone(ZoneId.systemDefault()).toLocalDate();
                    viewModel.updateUiState(
                        taskUiState.taskDetails.copy(
                            dateBegin = LocalDateTime.of(dateBegin.year, dateBegin.monthValue, dateBegin.dayOfMonth,
                                startTimePickerState.hour, startTimePickerState.minute
                            )
                        )
                    )
                },
                datePickerState = startDatePickerState,
                timePickerState = startTimePickerState
            )

            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                text = "Deadline",
                style = MaterialTheme.typography.displayMedium,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            DateTimePickerUi(
                labelForCLock = "End with",
                taskDetail = taskUiState.taskDetails,
                onDismissRequest = {
                    val dateEnd = Instant.ofEpochMilli(endDatePickerState.selectedDateMillis!!).atZone(ZoneId.systemDefault()).toLocalDate();
                    viewModel.updateUiState(
                        taskUiState.taskDetails.copy(
                            dateEnd = LocalDateTime.of(
                                dateEnd.year, dateEnd.monthValue, dateEnd.dayOfMonth,
                                endTimePickerState.hour, endTimePickerState.minute
                            )
                        )
                    )
                },
                datePickerState = endDatePickerState,
                timePickerState = endTimePickerState,

            )

            Spacer(modifier = Modifier.padding(16.dp))


            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveTask(taskUiState.taskDetails.toTask())
                        viewModel.updateUser(
                            user.copy(
                                totalTasks = user.totalTasks + 1,
                                comingTasks = user.comingTasks + 1
                            )
                        )
                        navigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = viewModel.validateDate(
                    startDate = miliToLocalDate(startDatePickerState.selectedDateMillis!!),
                    endDate = miliToLocalDate(endDatePickerState.selectedDateMillis!!),
                    startTime = LocalTime.of(startTimePickerState.hour, startTimePickerState.minute),
                    endTime = LocalTime.of(endTimePickerState.hour, endTimePickerState.minute)
                ) && viewModel.isValid()
            ) {
                Text(text = "Save task")
            }

//            if(t) {
//                AlertDialog(onDismissRequest = { t = false },
//                    modifier = Modifier.background(Color.White)) {
//                    Text(text = taskUiState.taskDetails.toString())
//                }
//            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePickerUi(
    taskDetail: TaskDetails,
    labelForCLock: String,
    onDismissRequest: () -> Unit,
    labelForDay: String = "Select a day",
    modifier: Modifier = Modifier,
    datePickerState: DatePickerState,
    timePickerState: TimePickerState
) {


    var showDatePicker by remember {
        mutableStateOf(false)
    }

    var openTimePicker by remember {
        mutableStateOf(false)
    }


    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = {
                showDatePicker = false
                onDismissRequest()
            },
            confirmButton = {
                TextButton(onClick = {
                    showDatePicker = false
                    onDismissRequest()
                }) {
                    Text(text = "Yes")
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                showModeToggle = false
            )
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .border(
                    width = 2.dp, color = Color.Gray, shape = RoundedCornerShape(4.dp)
                )
                .padding(8.dp)
                .clickable {
                    showDatePicker = true
                }, verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Sharp.DateRange, contentDescription = null)

            Column(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            ) {
                Text(text = labelForDay)
                Text(
                    text = dateFormatter.format(
                        Date(
                            datePickerState.selectedDateMillis ?: System.currentTimeMillis()
                        )
                    )
                )
            }

            Spacer(modifier = Modifier.padding(16.dp))

            Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)
        }



        if (openTimePicker) {
            TimePickerDialog(onDismissRequest = {
                openTimePicker = false
                onDismissRequest()
            }, confirmButton = {
                TextButton(onClick = {
                    openTimePicker = false
                    onDismissRequest()
                }) {
                    Text(text = "Yes")
                }
            }, dismissButton = {}) {
                TimePicker(state = timePickerState)
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .border(
                width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
            .clickable {
                openTimePicker = true
            }) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_access_time_24),
                contentDescription = null
            )

            Column(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            ) {
                Text(text = labelForCLock)
                Text(text = timePickerState.let {
                    prettierTime(it.hour.toString(), it.minute.toString())
                })
            }

            Icon(
                imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null
            )
        }
    }
}

@Composable
fun CategoryOfTasks(
    selectedCategory: Int,
    onClick: (Int) -> Unit,
    list: List<Category>,
    modifier: Modifier = Modifier
) {
    Text(
        text = "Category",
        style = MaterialTheme.typography.displayMedium,
        textDecoration = TextDecoration.Underline
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 3), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items(list) {
            OutlinedCard(modifier = Modifier
                .padding(16.dp)
                .size(68.dp)
                .clickable {
                    onClick(it.id)
                }) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = if (it.id == selectedCategory) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.secondary
                        ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = it.title, color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
        }
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = confirmButton,
        dismissButton = dismissButton,
        text = content
    )
}

//@Composable
//@Preview
//fun TimePickerDialogPreview() {
//    TimePickerDialog()
//}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//@Preview(
//    showBackground = true
//)
//fun DatePickerUiPreview() {
//    DatePickerUi(
//        labelForCLock = "",
//
//    )
//}

@Composable
fun TitleOfTask(
    label: String,
    placeholder: String,
    isSingleLine: Boolean = false,
    imeAction: ImeAction,
    maxLines: Int = 1,
    modifier: Modifier = Modifier,
    onChangeValue: (String) -> Unit,
    text: String
) {
    Column {
        Text(
            text = label,
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            style = MaterialTheme.typography.displayMedium,
            textDecoration = TextDecoration.Underline
        )

        OutlinedTextField(
            value = text,
            onValueChange = onChangeValue,
            singleLine = isSingleLine,
            modifier = modifier.fillMaxWidth(),
            placeholder = {
                Text(text = placeholder)
            },
            keyboardOptions = KeyboardOptions(imeAction = imeAction),
            maxLines = maxLines
        )
    }


}


@Composable
fun TopAppBar(
    navigateBack: () -> Unit, title: String, modifier: Modifier = Modifier
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

) {
    AddScreen({})
}