package com.example.hyperabhinav.ui.pages.chat

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hyperabhinav.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.reversed

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ChatScreen() {
//    var message by remember { mutableStateOf("") }
//    val messages = remember { mutableStateListOf<Triple<String, Boolean, Long>>() } // Triple: message, isSentByUser, timestamp
//    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed) // Drawer state
//    val scope = rememberCoroutineScope()
//
//    var drawerWidth by remember { mutableFloatStateOf(0f) }
//    val offset = drawerState.currentOffset
//    val percentOpen = if (drawerWidth == 0f) 0f else 1f - (offset / drawerWidth).coerceIn(0f, 1f)
//
//    val contentScale = if (offset == -drawerWidth) 1f else 1f - 0.03f * (1f + offset / drawerWidth).coerceIn(0f, 1f)
//    val contentPadding = (drawerWidth.dp * (1f - contentScale))
//
//    val listState = rememberLazyListState() // For scrolling to bottom
//    var isBotTyping by remember { mutableStateOf(false) } // For typing indicator
//    var lastSeenIndex by remember { mutableStateOf(-1) } // For "seen" indicator
//
//    fun formatTime(ts: Long): String {
//        return SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(ts))
//    }
//
//    LaunchedEffect(messages.size) {
//        if (messages.isNotEmpty()) {
//            listState.animateScrollToItem(messages.size - 1)
//        }
//    }
//
//    ModalNavigationDrawer(
//        scrimColor = DrawerDefaults.scrimColor,
//        drawerState = drawerState,
//        drawerContent = {
//            Column(
//                modifier = Modifier
//                    .onGloballyPositioned { coordinates ->
//                        drawerWidth = coordinates.size.width.toFloat()
//                    }
//                    .fillMaxHeight()
//                    .background(Color.LightGray)
//                    .padding(16.dp)
//            ) {
//                Text(text = "Menu Item 1", fontSize = 18.sp, modifier = Modifier.padding(vertical = 8.dp))
//                Text(text = "Menu Item 2", fontSize = 18.sp, modifier = Modifier.padding(vertical = 8.dp))
//                Text(text = "Menu Item 3", fontSize = 18.sp, modifier = Modifier.padding(vertical = 8.dp))
//            }
//        },
//        content = {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(start = contentPadding)
//                    .scale(contentScale)
//            ) {
//                // Custom Top Bar using Row
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .background(Color.Magenta)
//                        .padding(horizontal = 8.dp, vertical = 4.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    IconButton(onClick = {
//                        scope.launch { drawerState.open() } // Open the drawer
//                    }) {
//                        Icon(
//                            imageVector = Icons.Default.Menu,
//                            contentDescription = "Menu",
//                            tint = Color.White
//                        )
//                    }
//
//                    Spacer(modifier = Modifier.width(8.dp))
//
//                    Box(
//                        modifier = Modifier
//                            .size(40.dp)
//                            .background(Color.Gray, CircleShape)
//                    ) {
//                        // Default profile image placeholder
//                        Icon(
//                            painter = painterResource(id = R.drawable.ic_launcher_background), // Replace with your default image resource
//                            contentDescription = "Profile Picture",
//                            tint = Color.White,
//                            modifier = Modifier.align(Alignment.Center)
//                        )
//                    }
//
//                    Spacer(modifier = Modifier.width(8.dp))
//
//                    Text(
//                        text = "Username",
//                        fontSize = 18.sp,
//                        color = Color.White,
//                        modifier = Modifier.weight(1f)
//                    )
//                    Icon(
//                        imageVector = Icons.Default.MoreVert,
//                        contentDescription = "More Options",
//                        tint = Color.White
//                    )
//                }
//
//                // Chat Messages (Scrollable, with timestamps, seen, and typing indicator)
//                Box(
//                    modifier = Modifier
//                        .weight(1f)
//                        .fillMaxWidth()
//                        .padding(8.dp)
//                ) {
//                    LazyColumn(
//                        state = listState,
//                        modifier = Modifier.fillMaxSize(),
//                        verticalArrangement = Arrangement.spacedBy(8.dp)
//                    ) {
//                        itemsIndexed(messages) { idx, (text, isSentByUser, timestamp) ->
//                            Row(
//                                horizontalArrangement = if (isSentByUser) Arrangement.End else Arrangement.Start,
//                                modifier = Modifier.fillMaxWidth()
//                            ) {
//                                Column(
//                                    horizontalAlignment = if (isSentByUser) Alignment.End else Alignment.Start
//                                ) {
//                                    Box(
//                                        modifier = Modifier
//                                            .background(
//                                                if (isSentByUser) Color(0xFFDCF8C6) else Color(0xFFEDEDED),
//                                                shape = MaterialTheme.shapes.medium
//                                            )
//                                            .padding(12.dp)
//                                    ) {
//                                        Text(
//                                            text = text,
//                                            color = Color.Black,
//                                            fontSize = 16.sp
//                                        )
//                                    }
//                                    Row(
//                                        verticalAlignment = Alignment.CenterVertically
//                                    ) {
//                                        Text(
//                                            text = formatTime(timestamp),
//                                            fontSize = 11.sp,
//                                            color = Color.Gray,
//                                            modifier = Modifier.padding(top = 2.dp, end = 4.dp)
//                                        )
//                                        if (isSentByUser && idx == messages.lastIndex) {
//                                            // Seen indicator for last user message
//                                            Text(
//                                                text = if (lastSeenIndex == idx) "Seen" else "Sent",
//                                                fontSize = 11.sp,
//                                                color = if (lastSeenIndex == idx) Color(0xFF34B7F1) else Color.Gray
//                                            )
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                        if (isBotTyping) {
//                            item {
//                                Row(
//                                    horizontalArrangement = Arrangement.Start,
//                                    modifier = Modifier.fillMaxWidth()
//                                ) {
//                                    Box(
//                                        modifier = Modifier
//                                            .background(Color(0xFFEDEDED), shape = MaterialTheme.shapes.medium)
//                                            .padding(12.dp)
//                                    ) {
//                                        // Simple animated dots for typing
//                                        TypingIndicator()
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//
//                // Input Box with attachment icon
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(8.dp)
//                ) {
//                    IconButton(onClick = { /* TODO: Attachment logic */ }) {
//                        Icon(
//                            imageVector = Icons.Default.Add,
//                            contentDescription = "Attach",
//                            tint = Color.Gray
//                        )
//                    }
//                    Spacer(modifier = Modifier.width(4.dp))
//                    BasicTextField(
//                        value = message,
//                        onValueChange = { message = it },
//                        modifier = Modifier
//                            .weight(1f)
//                            .background(Color.LightGray, MaterialTheme.shapes.small)
//                            .padding(8.dp),
//                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
//                        keyboardActions = KeyboardActions(onSend = {
//                            if (message.isNotBlank()) {
//                                val now = System.currentTimeMillis()
//                                messages.add(Triple(message, true, now))
//                                lastSeenIndex = messages.lastIndex
//                                message = ""
//                                // Simulate bot typing
//                                scope.launch {
//                                    isBotTyping = true
//                                    delay(1200)
//                                    isBotTyping = false
//                                    messages.add(Triple("This is a reply.", false, System.currentTimeMillis()))
//                                    lastSeenIndex = messages.lastIndex
//                                }
//                            }
//                        })
//                    )
//                    Spacer(modifier = Modifier.width(8.dp))
//                    IconButton(onClick = {
//                        if (message.isNotBlank()) {
//                            val now = System.currentTimeMillis()
//                            messages.add(Triple(message, true, now))
//                            lastSeenIndex = messages.lastIndex
//                            message = ""
//                            // Simulate bot typing
//                            scope.launch {
//                                isBotTyping = true
//                                delay(1200)
//                                isBotTyping = false
//                                messages.add(Triple("This is a reply.", false, System.currentTimeMillis()))
//                                lastSeenIndex = messages.lastIndex
//                            }
//                        }
//                    }) {
//                        Icon(imageVector = Icons.Default.Send, contentDescription = "Send")
//                    }
//                }
//            }
//        }
//    )
//}
//
//// Typing indicator composable (three animated dots)
//@Composable
//fun TypingIndicator() {
//    val dotCount = 3
//    val anim = remember { Animatable(0f) }
//    LaunchedEffect(Unit) {
//        while (true) {
//            anim.animateTo(
//                targetValue = 1f,
//                animationSpec = androidx.compose.animation.core.tween(durationMillis = 900)
//            )
//            anim.snapTo(0f)
//        }
//    }
//    Row {
//        for (i in 0 until dotCount) {
//            val alpha = ((anim.value * dotCount - i).coerceIn(0f, 1f))
//            Box(
//                Modifier
//                    .size(8.dp)
//                    .padding(2.dp)
//                    .background(Color.Gray.copy(alpha = alpha), CircleShape)
//            )
//        }
//    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(viewModel: ChatViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Gemini Chat") })
        },
        bottomBar = {
            MessageInput(
                currentInput = uiState.currentInput,
                onInputChanged = viewModel::updateCurrentInput,
                onSendMessage = viewModel::sendMessage,
                isLoading = uiState.isLoading
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            ChatMessages(messages = uiState.messages)

            if (uiState.isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            uiState.error?.let { errorMsg ->
                Text(
                    text = "Error: $errorMsg",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun ChatMessages(messages: List<Message>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth().weight(1f),
        reverseLayout = true, // Show latest messages at the bottom
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.Bottom) // Arrange from bottom up
    ) {
        items(messages.reversed()) { message -> // Reverse to display chronologically from top to bottom if not using reverseLayout
            ChatMessageBubble(message = message)
        }
    }
}

@Composable
fun ChatMessageBubble(message: Message) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (message.isUser) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer
            ),
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .widthIn(max = 300.dp) // Limit bubble width
        ) {
            Text(
                text = message.text,
                color = if (message.isUser) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageInput(
    currentInput: String,
    onInputChanged: (String) -> Unit,
    onSendMessage: () -> Unit,
    isLoading: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = currentInput,
            onValueChange = onInputChanged,
            label = { Text("Type your message...") },
            modifier = Modifier.weight(1f),
            singleLine = false,
            maxLines = 5,
            keyboardOptions = androidx.compose.ui.text.input.KeyboardOptions(
                imeAction = androidx.compose.ui.text.input.ImeAction.Send
            ),
            keyboardActions = androidx.compose.ui.text.input.KeyboardActions(
                onSend = { if (!isLoading) onSendMessage() }
            ),
            enabled = !isLoading // Disable input when loading
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = onSendMessage,
            enabled = currentInput.isNotBlank() && !isLoading, // Enable button only when there's text and not loading
            modifier = Modifier.height(56.dp)
        ) {
            Icon(Icons.Filled.Send, contentDescription = "Send Message")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChatScreen() {
    YourAppTheme {
        ChatScreen()
    }
}

