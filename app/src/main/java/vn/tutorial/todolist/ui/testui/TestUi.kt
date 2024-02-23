package vn.tutorial.todolist.ui.testui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import vn.tutorial.todolist.R
import vn.tutorial.todolist.ui.screen.start.OutlinedTextFieldWithLeadingIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Test(

) {

    Scaffold(
        topBar = {
            Text(
                text = "User Information",
                style = MaterialTheme.typography.displayMedium
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
                .padding(8.dp)
        ) {

            OutlinedTextFieldWithLeadingIcons(
                onValueChange = {},
                leadingIcons = {
                    Icon(
                        imageVector = Icons.Default.Person ,
                        contentDescription = null,
                        modifier = Modifier.size(36.dp)
                    )
                },
                label = {
                    Text(text = "Username")
                },
                modifier = Modifier.padding(8.dp)
            )

            OutlinedTextFieldWithLeadingIcons(
                onValueChange = {},
                leadingIcons = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_cake_24) ,
                        contentDescription = null,
                        modifier = Modifier.size(36.dp)
                    )
                },
                label = {
                    Text(text = "Birth Day")
                },
                trailingIcon = {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
                },
                modifier = Modifier.padding(8.dp),
                isMandatory = true
            )

            OutlinedTextFieldWithLeadingIcons(
                onValueChange = {},
                leadingIcons = {
                    Icon(
                        imageVector = Icons.Default.Email ,
                        contentDescription = null,
                        modifier = Modifier.size(36.dp)
                    )
                },
                label = {
                    Text(text = "Email")
                },
                modifier = Modifier.padding(8.dp)
            )

            OutlinedTextFieldWithLeadingIcons(
                onValueChange = {},
                leadingIcons = {
                    Icon(
                        imageVector = Icons.Default.LocationOn ,
                        contentDescription = null,
                        modifier = Modifier.size(36.dp)
                    )
                },
                label = {
                    Text(text = "Address")
                },
                modifier = Modifier.padding(8.dp)
            )

            OutlinedTextFieldWithLeadingIcons(
                onValueChange = {},
                leadingIcons = {
                    Icon(
                        imageVector = Icons.Default.Email ,
                        contentDescription = null,
                        modifier = Modifier.size(36.dp),
                        tint = Color.White
                    )
                },
                label = {
                    Text(text = "City")
                },
                modifier = Modifier.padding(8.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Done")
            }
        }
    }

}


@Composable
@Preview(
    showBackground = true,
    showSystemUi = true
)
fun TestPreview() {
    Test()
}