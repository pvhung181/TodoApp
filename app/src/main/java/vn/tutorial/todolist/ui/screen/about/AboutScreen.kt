package vn.tutorial.todolist.ui.screen.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import vn.tutorial.todolist.R
import vn.tutorial.todolist.ui.navigation.NavigationDestination

object AboutScreen : NavigationDestination {
    override val route: String
        get() = "about"
    override val titleRes: Int
        get() = R.string.about_us

}


@Composable
fun AboutScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = navigateBack) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }

                Text(text = stringResource(id = R.string.about_us))
            }
        }
    ) {
        Column(
            modifier = modifier
                .padding(it)
                .padding(8.dp)
        ) {
            Text(text = "Author : Pham Viet Hung")
            Text(text = "Email : pvhung181@gmail.com")
        }
    }
}

@Composable
@Preview(
    showBackground = true
)
fun AboutScreenPreview() {
    AboutScreen(navigateBack = { /*TODO*/ })
}