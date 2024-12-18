package com.udit.todoit.ui.home

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.udit.todoit.R
import com.udit.todoit.entry_point.main_activity.ui.theme.TodoStatusColors
import com.udit.todoit.env.ENV
import com.udit.todoit.notification.NotificationHelper
import com.udit.todoit.room.entity.TodoStatus
import com.udit.todoit.room.entity.TodoType
import com.udit.todoit.ui.add_todo_type.AddTodoType
import com.udit.todoit.ui.add_todo_type.AddTodoTypeViewModel
import com.udit.todoit.ui.add_todo_type.models.TodoTypeCount
import com.udit.todoit.ui.add_todo_type.models.TodoTypeView
import com.udit.todoit.ui.common_composables.CardText
import com.udit.todoit.ui.common_composables.CardTextWithText
import com.udit.todoit.ui.common_composables.GradientButton
import com.udit.todoit.ui.home.model.TodoView
import com.udit.todoit.utils.DateUtils
import com.udit.todoit.utils.Utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


fun test(context: Context? = null) {
//    context?.let {
//        var builder = NotificationCompat.Builder(it, CHANNEL_ID)
//            .setSmallIcon(R.drawable.todoit_logo)
//            .setContentTitle("My notification")
//            .setContentText("Much longer text that cannot fit one line...")
//            .setStyle(
//                NotificationCompat.BigTextStyle()
//                    .bigText("Much longer text that cannot fit one line...")
//            )
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//    }
    context?.let {
        val largeIconBitmap = BitmapFactory.decodeResource(it.resources, R.drawable.todoit_logo)
        var builder = NotificationCompat.Builder(it, NotificationHelper.CHANNEL_ID_TARGET_DATE_TIME_MISSED)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)
            .setLargeIcon(largeIconBitmap)
            .setContentTitle("Missed!!!")
            .setContentText("Your Todo Target Date time has been missed.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(it)) {
            if(ActivityCompat.checkSelfPermission(it, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
//               request permission
            }
            notify(1, builder.build())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    todoTypeViewModel: AddTodoTypeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val todoList = viewModel.todos.collectAsStateWithLifecycle()

    val selectedTodoType = viewModel.selectedTodoType.collectAsStateWithLifecycle()
    val todoTypeList = viewModel.todoTypes.collectAsStateWithLifecycle()

    val todoStatusList = viewModel.todoStatusList.collectAsStateWithLifecycle()


    val selectedFilterBy = viewModel.selectedFilterBy.collectAsStateWithLifecycle()
    val filterByList = viewModel.filterByList.collectAsStateWithLifecycle()

    val progressTemporary by remember { mutableStateOf(0.6f) }

    val showAddTodoTypeAlert = todoTypeViewModel.showTodoType.collectAsStateWithLifecycle()
    val colorRed = Color.Red

//    viewModel.logger(colorRed.toString())

    Log.d("STATUS_LIST", todoStatusList.value.toString())

    LaunchedEffect(key1 = "") {
        scope.launch(Dispatchers.Main) {
            viewModel.errorFlow.collectLatest { errMsg: String? ->
                Log.d("TOAST", errMsg ?: "HIT")
                errMsg?.let {
//                    Utils.showToast(context, it)
//                    Log.d("TOAST", errMsg)
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

//    fun openTodoTypeEdit(todoTypeId: Int) {
//        AddTodoType(
//            viewModel = addTodoTypeViewModel,
//            todoTypeId = typeItem.typeId
//        )
//    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(viewModel.userData.value?.Name?.let { "Dear, $it" } ?: "Guest",
                    modifier = Modifier.padding(16.dp))
                HorizontalDivider()
                NavigationDrawerItem(
                    label = { Text(text = "Log Out") },
                    selected = false,
                    icon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = null,
                            tint = Color.Red
                        )
                    },
                    onClick = { viewModel.logOut() }
                )
                // ...other drawer items
            }
        }
    ) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Home")
                    },
                    navigationIcon = {
                        IconButton(
                            modifier = Modifier,
                            onClick = {
                                scope.launch {
                                    drawerState.apply {
                                        if (drawerState.isOpen) close() else open()
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = null
                            )
                        }
                    },
                    modifier = Modifier
                )
            },
            floatingActionButton = {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    if(ENV.isDebugging) {
                        Button(
                            modifier = Modifier.padding(end = 50.dp),
                            onClick = {
//                                Utils.showToast(
//                                    context = context,
//                                    msg = DateUtils.getCalenderDate()
//                                )
                                test(context = context)
                            }
                        ) {
                            Text("Test")
                        }
                    }
                    Button(
                        onClick = {
//                    viewModel.insertTodo()
                            viewModel.navigateToUpsertTodoScreen()
                        }
                    ) {
                        Text("Add")
                    }
                }
            }
//        modifier = Modifier.background(MaterialTheme.colorScheme.primary)
//        modifier = Modifier.background(Color.Gray)
        ) { innerPadding ->

            if (showAddTodoTypeAlert.value) {
                AddTodoType(todoTypeViewModel)
            }

            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(Color.Gray)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 2.dp)
                        .background(
                            color = Color(
                                red = 203,
                                green = 203,
                                blue = 203,
                                alpha = 123
                            )
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .padding(start = 10.dp)
//                        .background(Color.Red)
                            .fillMaxWidth(),
//                        .background(Color.Red),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CardText(
                            text = "Categories"
                        )
                        IconButton(
                            modifier = Modifier
                                .padding(end = 0.dp),
//                            .offset(x = 0.dp, y = -20.dp),
                            onClick = {
//                            viewModel.showAddTodoTypeAlert()
                                todoTypeViewModel.show()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.AddCircle,
                                contentDescription = "a",
                                modifier = Modifier
//                                .drawBehind {
//                                    drawCircle(
//                                        color = Color.Green,
//                                        radius = this.size.maxDimension
//                                    )
//                                },
                            )
                        }
                    }
                    LazyRow(
                        modifier = Modifier
                            .padding(top = 0.dp, start = 5.dp, end = 5.dp)
                            .fillMaxWidth()
//                        .background(Color.Red)
                    ) {
                        itemsIndexed(
                            items = todoTypeList.value,
                            key = { index: Int, item: TodoTypeView -> item.todoTypeId }
                        ) { index, typeItem ->
                            HeaderTypeCard(
                                typeItem = typeItem,
                                viewModel = viewModel,
//                                pendingTasksCount = 6,
//                                totalTasksCount = 10,
                                selectTodoType = { todoType ->
                                    viewModel.filterTodosByTodoType(todoType)
                                },
                                editTodoType = { todoType ->
                                    todoTypeViewModel.show(todoType.todoTypeId)
                                }
                            )
                        }

                    }


                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    filterByList.value.forEachIndexed { index, filter ->
                        val isSelected = selectedFilterBy.value == filter
                        FilterChip(
                            modifier = Modifier,
                            leadingIcon = {
                                Icon(imageVector = filter.icon, contentDescription = "")
                            },
                            border = BorderStroke(
                                color = if (isSelected) animateColorAsState(
                                    targetValue = Color.Black,
                                    label = "",
                                    animationSpec = tween(
                                        durationMillis = 2000,
                                        delayMillis = 2000,
                                        easing = EaseIn
                                    )
                                ).value
                                else
//                                Color.Transparent,
                                    filter.color,
                                width = if (isSelected) 1.dp else 2.dp,

                                ),
                            colors = SelectableChipColors(
                                containerColor = animateColorAsState(
                                    targetValue = Color.LightGray,
                                    label = "",
                                    animationSpec = tween(
                                        durationMillis = 2000,
                                        delayMillis = 2000,
                                        easing = EaseIn
                                    )
                                ).value,
                                leadingIconColor = filter.color,
                                trailingIconColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                disabledLeadingIconColor = Color.Transparent,
                                disabledTrailingIconColor = Color.Transparent,
                                disabledSelectedContainerColor = Color.Transparent,
                                disabledLabelColor = Color.Transparent,
                                labelColor = filter.color,
                                selectedLabelColor = if (filter.isLight) Color.Black else Color.White,
                                selectedLeadingIconColor = Color.Black,
                                selectedContainerColor = filter.color,
                                selectedTrailingIconColor = Color.Transparent
                            ),
                            onClick = {
                                viewModel.changeFilter(filter)
                            },
                            selected = selectedFilterBy.value == filter,
                            label = {
                                Text(filter.name)
                            }
                        )
                    }
                }
//            HorizontalDivider(
//                thickness = 2.dp,
//                color = Color.Black,
//                modifier = Modifier
////                    .padding(vertical = 0.dp, horizontal = 10.dp),
//            )
                LazyColumn(
                    modifier = Modifier
//                    .background(MaterialTheme.colorScheme.primary)
                        .fillMaxSize()
                        .padding(top = 0.dp)
                ) {

                    itemsIndexed(
                        items = todoList.value,
                        key = { index: Int, item: TodoView -> item.todoID }
                    ) { index, todoItem ->

                        TodoCard(todoItem, todoStatusList.value, viewModel)
                    }
                }

            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HeaderTypeCard(
    typeItem: TodoTypeView,
    viewModel: HomeViewModel,
    selectTodoType: (todoType: TodoTypeView) -> Unit,
    editTodoType: (todoType: TodoTypeView) -> Unit
) {
//    val progress by remember { mutableFloatStateOf((pendingTasksCount.toFloat() / totalTasksCount.toFloat()).toFloat()) }
//    val progress by remember { mutableFloatStateOf(0.6f) }
//    val todoTypeCount: MutableState<TodoTypeCount> = remember { mutableStateOf(TodoTypeCount(
//        todoTypeId = 0,
//        todoTypeName = "",
//        totalCount = 0,
//        pendingCount = 0,
//        laterCount = 0,
//        completedCount = 0
//    )) }

//    LaunchedEffect("") {
//        viewModel.getTodoTypeCount(todoTypeId = typeItem.todoTypeId, {
//            todoTypeCount.value = it
//            Log.d("TODO_COUNT", it.toString())
//        })
//    }
    val animationSpecForLinearProgression = remember {
        tween<Float>(
            easing = EaseIn,
            delayMillis = 500,
            durationMillis = 2500
        )
    }
    Log.d("HEADERTYPECARD", "RENDER")

    Card(
        modifier = Modifier
            .padding(10.dp)
            .combinedClickable(
                onClick = {
                    selectTodoType(typeItem)
                },
                onLongClick = {
                    editTodoType(typeItem)
                }
            ),
        border = BorderStroke(
            width = 2.dp,
            color = Color(value = typeItem.color.toULong())
        )
    ) {

        Column(
            modifier = Modifier
//                .width(150.dp)
                .background(
                    color = Color.Gray
//                    brush = Brush.linearGradient(
//                        colors = listOf(
////                            Color.Gray,
////                            Color.Blue,
//                            Color.LightGray,
//                            Color.Gray,
//                            Color.LightGray
//                        )
//                    )
                ),
//                              .height(100.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
//                        .background(Color.Red)

                ,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
//                Text(
//                    modifier = Modifier
//                        .padding(start = 15.dp, top = 10.dp),
//                    fontSize = TextUnit(value = 11f, type = TextUnitType.Sp),
////                                  .background(Color.Red),
//                    text = "${totalTasksCount} Tasks",
//                    color = Color.Black
//                )

                CardTextWithText(
                    cardColors = CardColors(
                        containerColor = Color(value = typeItem.color.toULong()),
//                    containerColor = Color.Red,
                        contentColor = Color.Green,
                        disabledContentColor = Color.Blue,
                        disabledContainerColor = Color.Green
                    ),
                    cardModifier = Modifier
                        .padding(start = 10.dp),
                    textComposable = {
                        Text(
                            fontSize = TextUnit(value = 18f, type = TextUnitType.Sp),
                            text = typeItem.todoTypeName,
                            modifier = Modifier
                                .padding(vertical = 0.dp, horizontal = 10.dp),
                            color = if (typeItem.isLight) {
                                Color.Black
                            } else {
                                Color.White
                            }
                        )
                    }

                )

                IconButton(
                    modifier = Modifier
//                        .background(Color.Red)
//                        .height(25.dp)
                        .clip(CircleShape)
//                        .background(Color.Red)
//                        .padding(0.dp)
//                        .padding(0.dp)
                    ,
                    onClick = {
                        editTodoType(typeItem)
                    }
                ) {
                    Icon(
                        modifier = Modifier
//                            .height(10.dp)
                        ,
                        imageVector = Icons.Filled.Edit,
                        contentDescription = null
                    )
                }
            }
//            CardTextWithText(
//                cardColors = CardColors(
//                    containerColor = Color(value = typeItem.color.toULong()),
////                    containerColor = Color.Red,
//                    contentColor = Color.Green,
//                    disabledContentColor = Color.Blue,
//                    disabledContainerColor = Color.Green
//                ),
//                cardModifier = Modifier
//                    .padding(start = 10.dp),
//                textComposable = {
//                    Text(
//                        fontSize = TextUnit(value = 18f, type = TextUnitType.Sp),
//                        text = typeItem.typename,
//                        modifier = Modifier
//                            .padding(vertical = 0.dp, horizontal = 10.dp),
//                        color = if (typeItem.isLight) {
//                            Color.Black
//                        } else {
//                            Color.White
//                        }
//                    )
//                }
//
//            )
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.End
//            ) {
//                Text(
//                    modifier = Modifier.padding(end = 15.dp),
//                    fontSize = TextUnit(value = 11f, type = TextUnitType.Sp),
//                    text = "$pendingTasksCount /$totalTasksCount",
//                    color = Color.Black
//
//                )
//            }

//            animateFloatAsState(
//                label = "",
//                targetValue = todoTypeCount.value.pendingCount!!.toFloat(),
//                animationSpec = tween(
//                    easing = EaseIn,
//                    delayMillis = 2000,
//                    durationMillis = 2000
//                )
//            ).value

            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                val color: Color = TodoStatusColors.colorPending
//                val floatValue: Float = if(typeItem.totalCount == 0) 0.0f else
//                    typeItem.pendingCount.toFloat()/typeItem.totalCount.toFloat()
                val floatValue = animateFloatAsState(
                    animationSpec = animationSpecForLinearProgression,
                    targetValue = if(typeItem.totalCount == 0) 0.0f else typeItem.pendingCount.toFloat()/typeItem.totalCount.toFloat(),
                    label = ""
                )
                val helpingCountText = "${typeItem.pendingCount}/${typeItem.totalCount}"
                LinearProgressIndicator(
                    modifier = Modifier
                        .height(7.dp)
                        .width(200.dp)
//                        .padding(top = 10.dp, bottom = 10.dp, start = 15.dp, end = 15.dp),
                        .padding(start = 15.dp, end = 15.dp),
                    color = color,
                    trackColor = Color.Black,
                    strokeCap = StrokeCap.Round,
                    progress = { floatValue.value }
                )

                Text(
                    modifier = Modifier.padding(end = 10.dp),
                    fontSize = TextUnit(value = 11f, type = TextUnitType.Sp),
                    text = helpingCountText,
                    color = Color.Black
                )
            }

            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                val color: Color = TodoStatusColors.colorCompleted
//                val floatValue: Float = if(typeItem.totalCount == 0) 0.0f else
//                    typeItem.completedCount.toFloat()/typeItem.totalCount.toFloat()
                val floatValue = animateFloatAsState(
                    animationSpec = animationSpecForLinearProgression,
                    targetValue = if(typeItem.totalCount == 0) 0.0f else typeItem.completedCount.toFloat()/typeItem.totalCount.toFloat(),
                    label = ""
                )
                val helpingCountText = "${typeItem.completedCount}/${typeItem.totalCount}"
                LinearProgressIndicator(
                    modifier = Modifier
                        .height(7.dp)
                        .width(200.dp)
//                        .padding(top = 10.dp, bottom = 10.dp, start = 15.dp, end = 15.dp),
                        .padding(start = 15.dp, end = 15.dp),
                    color = color,
                    trackColor = Color.Black,
                    strokeCap = StrokeCap.Round,
                    progress = { floatValue.value }
                )

                Text(
                    modifier = Modifier.padding(end = 10.dp),
                    fontSize = TextUnit(value = 11f, type = TextUnitType.Sp),
                    text = helpingCountText,
                    color = Color.Black
                )
            }

            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                val color: Color = TodoStatusColors.colorLater
//                val floatValue: Float = if(typeItem.totalCount == 0) 0.0f else
//                    typeItem.laterCount.toFloat()/typeItem.totalCount.toFloat()
                val floatValue = animateFloatAsState(
                    animationSpec = animationSpecForLinearProgression,
                    targetValue = if(typeItem.totalCount == 0) 0.0f else typeItem.laterCount.toFloat()/typeItem.totalCount.toFloat(),
                    label = ""
                )
                val helpingCountText = "${typeItem.laterCount}/${typeItem.totalCount}"
                LinearProgressIndicator(
                    modifier = Modifier
                        .height(7.dp)
                        .width(200.dp)
//                        .padding(top = 10.dp, bottom = 10.dp, start = 15.dp, end = 15.dp),
                        .padding(start = 15.dp, end = 15.dp),
                    color = color,
                    trackColor = Color.Black,
                    strokeCap = StrokeCap.Round,
                    progress = { floatValue.value }
                )

                Text(
                    modifier = Modifier.padding(end = 10.dp),
                    fontSize = TextUnit(value = 11f, type = TextUnitType.Sp),
                    text = helpingCountText,
                    color = Color.Black
                )
            }

        }
    }
}

@Composable
fun TodoCard(todoItem: TodoView, todoStatusList: List<TodoStatus>, viewModel: HomeViewModel) {
    val popupVisible = remember {
        mutableStateOf(false)
    }
    ListItem(
        modifier = Modifier.padding(vertical = 2.dp, horizontal = 0.dp),
        headlineContent = {
            Text(todoItem.title)

        },
        supportingContent = {
//            Text(todoItem.todoTypeName)
            Column {
                Text(todoItem.description)
                Text(DateUtils.viewDateTimeFromString(todoItem.createdOn))
                Text(DateUtils.viewDateTimeFromString(todoItem.target))
            }

        },
        leadingContent = {
            Text(
                color = Color(value = todoItem.todoTypeColor.toULong()),
                text = todoItem.todoTypeName
            )
        },
        trailingContent = {
            Column {

//                GradientButton(
//                    modifier = Modifier,
//                    text = "Edit",
//                    gradient = TodoCardColors.editButtonGradient,
//                    onClick = {
//                        viewModel.navigateToUpsertTodoScreen(todoId = todoItem.todoID)
//                    }
//                )

                Card(
                    modifier = Modifier,
                    colors = CardColors(
                        containerColor = Color.Black,
                        disabledContainerColor = Color.Transparent,
                        contentColor = Color.White,
                        disabledContentColor = Color.Transparent
                    ),
                    border = BorderStroke(
                        width = 1.dp,
//                        color = Color(value = todoItem.todoStatusColor.toULong())
                        color = Color.Gray
                    ),
                    onClick = {
                        viewModel.navigateToUpsertTodoScreen(todoId = todoItem.todoID)
//                        popupVisible.value = true
                    }
                ) {
//                    Text(
//                        modifier = Modifier.padding(5.dp),
//                        text = "Edit"
//                    )
                    Icon(
                        modifier = Modifier.padding(5.dp),
                        imageVector = Icons.Default.Edit,
                        contentDescription = ""
                    )
                }

                Card(
                    modifier = Modifier,
                    border = BorderStroke(
                        width = 2.dp,
                        color = Color(value = todoItem.todoStatusColor.toULong())
                    ),
                    onClick = {
                        popupVisible.value = true
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = todoItem.todoStatusName
                    )
                }
                if (popupVisible.value) {
                    DropdownMenu(
                        modifier = Modifier,
                        expanded = popupVisible.value,
                        onDismissRequest = {
                            popupVisible.value = false
                        }
                    ) {
                        todoStatusList.forEachIndexed { index, todoStatus ->
                            DropdownMenuItem(
                                modifier = Modifier.background(Color(value = todoStatus.statusColor.toULong())),
                                text = { Text(todoStatus.statusName) },
                                colors = MenuItemColors(

//                                    textColor = if (todoStatus.isColorLight) {
//                                        animateColorAsState(
//                                            targetValue = Color.Black,
//                                            animationSpec = tween(
//                                                delayMillis = 2000,
//                                                easing = EaseIn,
//                                                durationMillis = 2000
//                                            )
//                                        ).value
//                                    } else {
//                                        animateColorAsState(
//                                            targetValue = Color.White,
//                                            animationSpec = tween(
//                                                delayMillis = 2000,
//                                                easing = EaseIn,
//                                                durationMillis = 2000
//                                            )
//                                        ).value
//                                    },
                                    textColor = if (todoStatus.isColorLight) {
                                        Color.Black
                                    } else {
                                        Color.White
                                    },
                                    leadingIconColor = Color.Transparent,
                                    trailingIconColor = Color.Transparent,
                                    disabledTextColor = Color.Red,
                                    disabledLeadingIconColor = Color.Red,
                                    disabledTrailingIconColor = Color.Red
                                ),
                                onClick = {
                                    popupVisible.value = false
                                    todoItem.todoCompletionStatusID = todoStatus.statusID
                                    viewModel.updateTodo(todoItem)
                                }
                            )
                        }
                    }
                }
            }


        }

    )

}
//@Composable
//fun TodoCard(todoItem: TodoView) {
//    Card(
//        modifier = Modifier
////                            .background(Color.Gray)
//            .fillMaxWidth()
//            .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 0.dp)
//    ) {
//
//        Column(
//            modifier = Modifier.padding(10.dp)
//        ) {
//
//            Text(
//                text = todoItem.title,
//            )
//            Text(
//                text = todoItem.todoTypeName,
//            )
//            Text(
//                text = todoItem.description,
//                fontSize = TextUnit(value = 11f, type = TextUnitType.Sp),
//                modifier = Modifier.padding(start = 10.dp)
//
//            )
//            Text(
//                text = Utils.convertMillisToDate(todoItem.createdOn.toLong()),
//                fontSize = TextUnit(value = 11f, type = TextUnitType.Sp),
//                modifier = Modifier.padding(start = 10.dp)
//
//            )
//            Text(
//                text = Utils.convertMillisToDate(todoItem.target.toLong()),
//                fontSize = TextUnit(value = 11f, type = TextUnitType.Sp),
//                modifier = Modifier.padding(start = 10.dp)
//
//            )
//        }
//
//    }
//
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        verticalAlignment = Alignment.Top,
//        horizontalArrangement = Arrangement.End
//    ) {
//        IconButton(
//            modifier = Modifier
//                .padding(end = 20.dp)
//                .offset(x = 0.dp, y = -20.dp),
//            onClick = {
//
//            }
//        ) {
//            Icon(
//                imageVector = Icons.Default.Done,
//                contentDescription = "add",
//                modifier = Modifier
//                    .drawBehind {
//                        drawCircle(
//                            color = Color.Green,
//                            radius = this.size.maxDimension
//                        )
//                    },
//            )
//        }
//    }
//
//}
