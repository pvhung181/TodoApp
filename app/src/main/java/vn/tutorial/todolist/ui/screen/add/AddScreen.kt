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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import vn.tutorial.todolist.R
import vn.tutorial.todolist.data.getCategory
import vn.tutorial.todolist.model.Category
import vn.tutorial.todolist.ui.AppViewModelProvider
import vn.tutorial.todolist.ui.navigation.NavigationDestination
import vn.tutorial.todolist.util.dateFormatter
import vn.tutorial.todolist.util.prettierTime
import java.sql.Date

object AddScreen : NavigationDestination {
    override val route = "add"
    override val titleRes = R.string.add_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    navigateBack: () -> Unit,
    viewModel: AddTaskViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    modifier: Modifier = Modifier
) {

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                navigateBack = navigateBack,
                title = stringResource(id = AddScreen.titleRes)
            )
        }
    ) {

        val categories = getCategory()

        Column(
            modifier = Modifier
                .padding(it)
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            TitleOfTask(
                label = "Title",
                isSingleLine = true,
                placeholder = "Enter a title for your task",
                imeAction = ImeAction.Next
            )
            TitleOfTask(
                label = "Description",
                placeholder = "Enter a description for your task",
                imeAction = ImeAction.Default,
                maxLines = 5,
                modifier = Modifier.height(120.dp)
            )
            CategoryOfTasks(
                selectedCategory = viewModel.selectedCategory.intValue,
                onClick = {
                    v ->
                    viewModel.selectedCategory.intValue = v
                },
                list = categories
            )

            Text(
                text = "Start Time",
                style = MaterialTheme.typography.displayMedium,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            DatePickerUi(
                labelForCLock = "Start with"
            )
            
            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                text = "Deadline",
                style = MaterialTheme.typography.displayMedium,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            DatePickerUi(
                labelForCLock = "End with"
            )

            Spacer(modifier = Modifier.padding(16.dp))

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Save task")
            }
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
        columns = GridCells.Fixed(count = 3),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items(list) {
            OutlinedCard(
                modifier = Modifier
                    .padding(16.dp)
                    .size(68.dp)
                    .clickable {
                        onClick(it.id)
                    }
            ) {
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
                        text = it.title,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
        }
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerUi(
    labelForCLock: String,
    labelForDay: String = "Select a day",
    modifier: Modifier = Modifier
) {
    val datePickerState: DatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis()
    )


    var showDatePicker by remember {
        mutableStateOf(false)
    }

    var hour by remember {
        mutableStateOf("")
    }

    var minute by remember {
        mutableStateOf("")
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(text = "Cancel")
                }
            }

        ) {
            DatePicker(state = datePickerState)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(8.dp)
                .clickable {
                    showDatePicker = true
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Sharp.DateRange, contentDescription = null)

            Column(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            ) {
                Text(text = "Select a day")
                Text(
                    text = dateFormatter
                        .format(
                            Date(
                                datePickerState.selectedDateMillis
                                    ?: System.currentTimeMillis()
                            )
                        )
                )
            }

            Spacer(modifier = Modifier.padding(16.dp))

            Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)
        }

        SelectTime(
            title = "Start with"
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectTime(
    title: String,
    modifier: Modifier = Modifier
) {

    var openTimePicker by remember {
        mutableStateOf(false)
    }

    val timePickerState = rememberTimePickerState(
        initialHour = 0,
        initialMinute = 0,
        is24Hour = true
    )

    if(openTimePicker) {
        TimePickerDialog(
            onDismissRequest = {
                openTimePicker = false
            },
            confirmButton = {
                TextButton(onClick = { openTimePicker = false }) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { openTimePicker = false }) {
                    Text(text = "No")
                }
            }
        ) {
            TimePicker(state = timePickerState)
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
            .clickable {
                openTimePicker = true
            }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_access_time_24),
            contentDescription = null
        )

        Column(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
        ) {
            Text(text = title)
            Text(text = timePickerState.let {
                prettierTime(it.hour.toString(), it.minute.toString())
            })
        }

        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = null
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    content:  @Composable () -> Unit
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(
    showBackground = true
)
fun DatePickerUiPreview() {
    DatePickerUi(
        labelForCLock = ""
    )
}

@Composable
fun TitleOfTask(
    label: String,
    placeholder: String,
    isSingleLine: Boolean = false,
    imeAction: ImeAction,
    maxLines: Int = 1,
    modifier: Modifier = Modifier
) {
    Column {
        Text(
            text = label,
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp),
            style = MaterialTheme.typography.displayMedium,
            textDecoration = TextDecoration.Underline
        )

        OutlinedTextField(
            value = "",
            onValueChange = {

            },
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

) {
    AddScreen(
        {}
    )
}