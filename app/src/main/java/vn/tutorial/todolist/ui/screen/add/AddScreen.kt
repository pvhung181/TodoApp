package vn.tutorial.todolist.ui.screen.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import vn.tutorial.todolist.R
import vn.tutorial.todolist.ui.navigation.NavigationDestination

object AddScreen : NavigationDestination {
    override val route = "add"
    override val titleRes = R.string.add_title
}

@Composable
fun AddScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigateBack = navigateBack
            )
        }
    ) {
        Row(
            modifier = Modifier.padding(it)
        ) {
            Text(text = "This is add screen")
        }
    }
}


@Composable
fun TopAppBar(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { navigateBack() }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
        }

        Text(text = stringResource(id = AddScreen.titleRes))
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