package com.example.hyperabhinav.ui.pages.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hyperabhinav.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen() {
    var message by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf<Pair<String, Boolean>>() } // Pair of message and isSentByUser
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed) // Drawer state
    val scope = rememberCoroutineScope()

    var drawerWidth by remember { mutableFloatStateOf(0f) }
    val offset = drawerState.currentOffset
    val percentOpen = if (drawerWidth == 0f) 0f else 1f - (offset / drawerWidth).coerceIn(0f, 1f)

    val contentScale = if (offset == -drawerWidth) 1f else 1f - 0.03f * (1f + offset / drawerWidth).coerceIn(0f, 1f)
    val contentPadding = (drawerWidth.dp * (1f - contentScale))

    ModalNavigationDrawer(
        scrimColor = DrawerDefaults.scrimColor,
        drawerState = drawerState,
        drawerContent = {
            Column(
                modifier = Modifier
                    .onGloballyPositioned { coordinates ->
                        drawerWidth = coordinates.size.width.toFloat()
                    }
                    .fillMaxHeight()
                    .background(Color.LightGray)
                    .padding(16.dp)
            ) {
                Text(text = "Menu Item 1", fontSize = 18.sp, modifier = Modifier.padding(vertical = 8.dp))
                Text(text = "Menu Item 2", fontSize = 18.sp, modifier = Modifier.padding(vertical = 8.dp))
                Text(text = "Menu Item 3", fontSize = 18.sp, modifier = Modifier.padding(vertical = 8.dp))
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = contentPadding)
                    .scale(contentScale)
            ) {
                // Custom Top Bar using Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Magenta)
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        scope.launch { drawerState.open() } // Open the drawer
                    }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color.Gray, CircleShape)
                    ) {
                        // Default profile image placeholder
                        Icon(
                            painter = painterResource(id = R.drawable.ic_launcher_background), // Replace with your default image resource
                            contentDescription = "Profile Picture",
                            tint = Color.White,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Username",
                        fontSize = 18.sp,
                        color = Color.White,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More Options",
                        tint = Color.White
                    )
                }

                // Chat Messages
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        messages.forEach { (text, isSentByUser) ->
                            Row(
                                horizontalArrangement = if (isSentByUser) Arrangement.End else Arrangement.Start,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(
                                            if (isSentByUser) Color.Green else Color.DarkGray,
                                            shape = MaterialTheme.shapes.medium
                                        )
                                        .padding(8.dp)
                                ) {
                                    Text(
                                        text = text,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }

                // Input Box
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    BasicTextField(
                        value = message,
                        onValueChange = { message = it },
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.LightGray, MaterialTheme.shapes.small)
                            .padding(8.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
                        keyboardActions = KeyboardActions(onSend = {
                            if (message.isNotBlank()) {
                                messages.add(message to true) // User's message
                                messages.add("This is a reply." to false) // Hardcoded reply
                                message = ""
                            }
                        })
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = {
                        if (message.isNotBlank()) {
                            messages.add(message to true) // User's message
                            messages.add("This is a reply." to false) // Hardcoded reply
                            message = ""
                        }
                    }) {
                        Icon(imageVector = Icons.Default.Send, contentDescription = "Send")
                    }
                }
            }
        }
    )
}