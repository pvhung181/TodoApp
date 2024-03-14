package vn.tutorial.todolist.ui.screen.user

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import vn.tutorial.todolist.R
import vn.tutorial.todolist.constants.USERNAME_LIMIT_CHARACTER
import vn.tutorial.todolist.ui.AppViewModelProvider
import vn.tutorial.todolist.ui.navigation.NavigationDestination
import vn.tutorial.todolist.ui.screen.start.OutlinedTextFieldWithLeadingIcons
import vn.tutorial.todolist.ui.theme.Shapes

object ProfileScreen : NavigationDestination {
    override val route: String
        get() = "profile"
    override val titleRes: Int
        get() = R.string.profile

}

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    viewModel: SettingScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = AppViewModelProvider.Factory)
) {

    when(viewModel.state.value) {
        ProfileState.LOADING -> LoadingScreen()
        else -> {
            val user = viewModel.userInfo.value
            val userTracking by viewModel.user.collectAsState()

            val coroutineScope = rememberCoroutineScope()

            val context = LocalContext.current

            val photoPicker = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickVisualMedia()
            ) {
                if (it != null) {
                    context.contentResolver.takePersistableUriPermission(
                        it,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )

                    viewModel.updateUserInfor(user.copy(avatar = it.toString()))
                }
            }


            Scaffold(
                topBar = {
                    vn.tutorial.todolist.ui.screen.add.TopAppBar(
                        navigateBack = navigateUp,
                        title = stringResource(id = ProfileScreen.titleRes)
                    )
                }
            ) {
                Column(
                    modifier = modifier
                        .padding(it)
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                        ) {
                            Text(
                                text = userTracking.fullName,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }

                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(Uri.parse(user.avatar))
                                .placeholder(R.drawable.default_avatar)
                                .error(R.drawable.default_avatar)
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp)
                                .clip(shape = Shapes.small),
                            contentScale = ContentScale.Crop,
                            placeholder = painterResource(id = R.drawable.default_avatar)
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Change avatar",
                            textDecoration = TextDecoration.Underline,
                            color = Blue,
                            modifier = Modifier
                                .clickable {
                                    photoPicker.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                }
                        )
                    }


                    Divider(
                        modifier = Modifier.padding(8.dp)
                    )

                    Column(
                        modifier = Modifier.padding(8.dp)
                    ) {

                        Text(
                            text = "* Tasks will be updated at midnight",
                            fontSize = 12.sp,
                            fontStyle = FontStyle.Italic
                        )
                        PieChart(
                            data = mapOf(
                                Pair("Completed", user.completedTasks),
                                Pair("Not Completed", user.totalTasks - user.comingTasks - user.completedTasks),
                                Pair("Coming", user.comingTasks),
                            )
                        )
                    }


                    Divider(
                        modifier = Modifier.padding(8.dp)
                    )

                    OutlinedTextFieldWithLeadingIcons(
                        value = user.fullName,
                        onValueChange = {name ->
                            if(name.length <= USERNAME_LIMIT_CHARACTER)
                                viewModel.updateUserInfor(user.copy(fullName = name))
                        },
                        label = {
                            Text(text = "Username")
                        },
                        leadingIcons = {
                            Icon(imageVector = Icons.Default.Person, contentDescription = null)
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        trailingIcon = {
                            Text(text = "${user.fullName.length}/$USERNAME_LIMIT_CHARACTER")
                        }
                    )

                    OutlinedTextFieldWithLeadingIcons(
                        value = user.birthDay.toString(),
                        onValueChange = {},
                        label = {
                            Text(text = "Birth day")
                        },
                        leadingIcons = {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_cake_24),
                                contentDescription = null
                            )
                        },
                        enable = false
                    )

                    OutlinedTextFieldWithLeadingIcons(
                        value = user.email,
                        onValueChange = {email ->
                            viewModel.updateUserInfor(user.copy(email = email))
                        },
                        label = {
                            Text(text = "Email")
                        },
                        leadingIcons = {
                            Icon(imageVector = Icons.Default.Email, contentDescription = null)
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = {
                            coroutineScope.launch {
                                    viewModel.updateUser(user)
                            }

                            navigateUp()
                        },
                        modifier = modifier.fillMaxWidth(),
                        enabled = (userTracking.avatar != user.avatar || userTracking.fullName != user.fullName || userTracking.email != user.email)
                    ) {
                        Text(text = "Save change")
                    }
                }
            }
        }
    }


}


@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {

}

//references medium.com : @developerchunk
@Composable
fun PieChart(
    data: Map<String, Int>,
    radiusOuter: Dp = 30.dp,
    chartBarWidth: Dp = 10.dp,
    animDuration: Int = 1000,
) {

    val totalSum = data.values.sum()
    val floatValue = mutableListOf<Float>()

    // To set the value of each Arc according to
    // the value given in the data, we have used a simple formula.
    // For a detailed explanation check out the Medium Article.
    // The link is in the about section and readme file of this GitHub Repository
    data.values.forEachIndexed { index, values ->
        floatValue.add(index, 360 * values.toFloat() / totalSum.toFloat())
    }

    // add the colors as per the number of data(no. of pie chart entries)
    // so that each data will get a color
    val colors = listOf(
        Green,
        Red,
        colorResource(id = R.color.purple_500)
    )

    var animationPlayed by remember { mutableStateOf(false) }

    var lastValue = 0f

    // it is the diameter value of the Pie
    val animateSize by animateFloatAsState(
        targetValue = if (animationPlayed) radiusOuter.value * 2f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        ), label = ""
    )

    // if you want to stabilize the Pie Chart you can use value -90f
    // 90f is used to complete 1/4 of the rotation
    val animateRotation by animateFloatAsState(
        targetValue = if (animationPlayed) 90f * 11f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        ), label = ""
    )

    // to play the animation only once when the function is Created or Recomposed
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Pie Chart using Canvas Arc
        Box(
            modifier = Modifier.size(animateSize.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .size(radiusOuter * 2f)
                    .rotate(animateRotation)
            ) {
                // draw each Arc for each data entry in Pie Chart
                floatValue.forEachIndexed { index, value ->
                    drawArc(
                        color = colors[index],
                        lastValue,
                        value,
                        useCenter = false,
                        style = Stroke(chartBarWidth.toPx(), cap = StrokeCap.Butt)
                    )
                    lastValue += value
                }
            }
        }

        // To see the data in more structured way
        // Compose Function in which Items are showing data
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // create the data items
            data.values.forEachIndexed { index, value ->
                DetailsPieChartItem(
                    data = Pair(data.keys.elementAt(index), value),
                    color = colors[index]
                )
            }

        }

    }

}

@Composable
fun DetailsPieChartItem(
    data: Pair<String, Int>,
    height: Dp = 20.dp,
    color: Color
) {

    Surface(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 40.dp),
        color = Color.Transparent
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .background(
                        color = color,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .size(height)
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.padding(start = 15.dp),
                    text = data.first,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Text(
                    modifier = Modifier.padding(start = 15.dp),
                    text = data.second.toString(),
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

        }

    }

}