package vn.tutorial.todolist.ui.testui
//
//import android.content.Intent
//import android.net.Uri
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.PickVisualMediaRequest
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.DateRange
//import androidx.compose.material.icons.filled.Edit
//import androidx.compose.material.icons.filled.Email
//import androidx.compose.material.icons.filled.LocationOn
//import androidx.compose.material.icons.filled.Person
//import androidx.compose.material.icons.filled.ShoppingCart
//import androidx.compose.material3.Button
//import androidx.compose.material3.Divider
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.modifier.modifierLocalConsumer
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.style.TextDecoration
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import coil.compose.AsyncImage
//import coil.request.ImageRequest
//import vn.tutorial.todolist.R
//import vn.tutorial.todolist.model.Task
//import vn.tutorial.todolist.model.User
//import vn.tutorial.todolist.ui.screen.home.TaskItem
//import vn.tutorial.todolist.ui.screen.start.OutlinedTextFieldWithLeadingIcons
//import vn.tutorial.todolist.ui.screen.user.ProfileScreen
//import vn.tutorial.todolist.ui.theme.Shapes
//import java.sql.Date
//import java.time.LocalDateTime
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun Test(
//
//) {
//
//    Scaffold(
//        topBar = {
//            Text(
//                text = "User Information",
//                style = MaterialTheme.typography.displayMedium
//            )
//        }
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(it)
//                .padding(8.dp)
//        ) {
//
//
////            AsyncImage(
////                model = "",
////                contentDescription = null,
////                placeholder = painterResource(id = R.drawable.default_avatar)
////            )
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.Center
//            ) {
//                Box {
//                    Image(
//                        painter = painterResource(id = R.drawable.default_avatar),
//                        contentDescription = null,
//                        modifier = Modifier
//                            .width(100.dp)
//                            .height(100.dp)
//                            .clip(shape = Shapes.small),
//                        contentScale = ContentScale.Crop
//                    )
//
//                }
//            }
//
//
//
//
//            OutlinedTextFieldWithLeadingIcons(
//                onValueChange = {},
//                leadingIcons = {
//                    Icon(
//                        imageVector = Icons.Default.Person ,
//                        contentDescription = null,
//                        modifier = Modifier.size(36.dp)
//                    )
//                },
//                label = {
//                    Text(text = "Username")
//                },
//                modifier = Modifier.padding(8.dp)
//            )
//
//            OutlinedTextFieldWithLeadingIcons(
//                onValueChange = {},
//                leadingIcons = {
//                    Icon(
//                        painter = painterResource(id = R.drawable.baseline_cake_24) ,
//                        contentDescription = null,
//                        modifier = Modifier.size(36.dp)
//                    )
//                },
//                label = {
//                    Text(text = "Birth Day")
//                },
//                trailingIcon = {
//                    Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
//                },
//                modifier = Modifier.padding(8.dp),
//                isMandatory = true
//            )
//
//            OutlinedTextFieldWithLeadingIcons(
//                onValueChange = {},
//                leadingIcons = {
//                    Icon(
//                        imageVector = Icons.Default.Email ,
//                        contentDescription = null,
//                        modifier = Modifier.size(36.dp)
//                    )
//                },
//                label = {
//                    Text(text = "Email")
//                },
//                modifier = Modifier.padding(8.dp)
//            )
//
//            OutlinedTextFieldWithLeadingIcons(
//                onValueChange = {},
//                leadingIcons = {
//                    Icon(
//                        imageVector = Icons.Default.LocationOn ,
//                        contentDescription = null,
//                        modifier = Modifier.size(36.dp)
//                    )
//                },
//                label = {
//                    Text(text = "Address")
//                },
//                modifier = Modifier.padding(8.dp)
//            )
//
//            OutlinedTextFieldWithLeadingIcons(
//                onValueChange = {},
//                leadingIcons = {
//                    Icon(
//                        imageVector = Icons.Default.Email ,
//                        contentDescription = null,
//                        modifier = Modifier.size(36.dp),
//                        tint = Color.White
//                    )
//                },
//                label = {
//                    Text(text = "City")
//                },
//                modifier = Modifier.padding(8.dp)
//            )
//
//            Spacer(modifier = Modifier.weight(1f))
//
//            Button(
//                onClick = {},
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text(text = "Done")
//            }
//        }
//    }
//
//}
//
//@Composable
//fun TestNoTask() {
//    Column {
//        Text(text = "00:00 am")
//        TaskItem(
//            task = Task(
//                1,
//                "Nothing",
//                1,
//                1,
//                "nothing",
//                false,
//                Date.valueOf("2023-1-1"),
//                LocalDateTime.now(), LocalDateTime.now()
//            )
//            , onDelete = { /*TODO*/ })
//    }
//}
//
//@Preview(
//    showSystemUi = true
//)
//@Composable
//fun TestNoTaskPreview() {
//    TestNoTask()
//}
//
////@Composable
////fun TestProfile(
////    modifier: Modifier = Modifier
////) {
////    Column(
////        modifier = modifier
////            .padding(8.dp)
////            .fillMaxWidth()
////    ) {
////        Column(
////            horizontalAlignment = Alignment.CenterHorizontally,
////            modifier = modifier.fillMaxWidth()
////        ) {
////            Image(
////                painter = painterResource(id = R.drawable.sample_avatar),
////                contentDescription = null,
////                modifier = Modifier
////                    .width(100.dp)
////                    .height(100.dp)
////                    .clip(shape = Shapes.small),
////                contentScale = ContentScale.Crop
////            )
////
////        }
////        Divider(
////            modifier = Modifier.padding(8.dp)
////        )
////
////
////    }
////}
//
////@Composable
////@Preview(
////    showBackground = true,
////    showSystemUi = true
////)
////fun TestProfilePreview() {
////    TestProfile()
////}
//
//@Composable
//@Preview(
//    showBackground = true,
//    showSystemUi = true
//)
//fun TestPreview() {
//    Test()
//}
//
//
//
//@Composable
//fun TestProfile(
//    modifier: Modifier = Modifier
//) {
//
//    val user = User(
//        1, "Pvhung", "", "", Date.valueOf("2021-2-2"), 2,2,2
//    )
//
//    val context = LocalContext.current
//
//    val photoPicker = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.PickVisualMedia()
//    ) {
//        if(it != null) {
//            context.contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
//
////            viewModel.updateUiState(
////                uiState.userInfo.copy(
////                    avatar = it.toString()
////                )
////            )
//        }
//    }
//
//
//    Scaffold(
//        topBar = {
//            vn.tutorial.todolist.ui.screen.add.TopAppBar(
//                navigateBack = {},
//                title = stringResource(id = ProfileScreen.titleRes)
//            )
//        }
//    ) {
//        Column(
//            modifier = modifier
//                .padding(it)
//                .padding(8.dp)
//                .fillMaxWidth()
//        ) {
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally,
//                modifier = modifier.fillMaxWidth()
//            ) {
//                Row(
//                    horizontalArrangement = Arrangement.Center,
//                    modifier =  modifier.fillMaxWidth()
//                ) {
//                    Text(
//                        text = user.fullName,
//                        style = MaterialTheme.typography.labelLarge
//                    )
//                }
//
//                AsyncImage(
//                    model = ImageRequest.Builder(LocalContext.current)
//                        .data(Uri.parse(user.avatar))
//                        .placeholder(R.drawable.default_avatar)
//                        .error(R.drawable.default_avatar)
//                        .crossfade(true)
//                        .build(),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .width(100.dp)
//                        .height(100.dp)
//                        .clip(shape = Shapes.small),
//                    contentScale = ContentScale.Crop,
//                    placeholder = painterResource(id = R.drawable.default_avatar)
//                )
//            }
//
//            Row(
//                horizontalArrangement = Arrangement.Center,
//                modifier =  modifier.fillMaxWidth()
//            ) {
//                Text(
//                    text = "Change avatar",
//                    textDecoration = TextDecoration.Underline,
//                    color = Color.Blue,
//                    modifier = Modifier
//                        .clickable {
//                            photoPicker.launch(
//                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
//                            )
//                        }
//                )
//            }
//
//
//            Divider(
//                modifier = Modifier.padding(8.dp)
//            )
//
//            Column {
//                Text(text = stringResource(id = R.string.total_tasks, 1))
//                Text(text = stringResource(id = R.string.task_completed, 2))
//                Text(text = stringResource(id = R.string.task_coming, 3))
//            }
//
//            OutlinedTextFieldWithLeadingIcons(
//                value = user.fullName,
//                onValueChange = {
//
//                },
//                label = {
//                    Text(text = "Username")
//                },
//                leadingIcons = {
//                    Icon(imageVector = Icons.Default.Person, contentDescription = null)
//                },
//                trailingIcon = {
//                    Icon(
//                        imageVector = Icons.Default.Edit,
//                        contentDescription = null,
//                        modifier = Modifier.clickable {
//
//                        }
//                    )
//                }
//            )
//
//            OutlinedTextFieldWithLeadingIcons(
//                value = user.birthDay.toString(),
//                onValueChange = {},
//                label = {
//                    Text(text = "Birth day")
//                },
//                leadingIcons = {
//                    Icon(painter = painterResource(id = R.drawable.baseline_cake_24), contentDescription = null)
//                },
//                enable = false
//            )
//
//            OutlinedTextFieldWithLeadingIcons(
//                value = "",
//                onValueChange = {},
//                label = {
//                    Text(text = "Email")
//                },
//                leadingIcons = {
//                    Icon(imageVector = Icons.Default.Email, contentDescription = null)
//                },
//                enable = false
//            )
//
//            Spacer(modifier = Modifier.weight(1f))
//
//            Button(
//                onClick = {
//
//                },
//                modifier = modifier.fillMaxWidth()
//            ) {
//                Text(text = "Save change")
//            }
//        }
//    }
//}
//
//@Preview
//@Composable
//fun TestProfilePreview() {
//    TestProfile()
//}