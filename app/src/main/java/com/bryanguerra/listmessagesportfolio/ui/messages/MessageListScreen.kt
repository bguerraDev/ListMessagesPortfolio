package com.bryanguerra.listmessagesportfolio.ui.messages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bryanguerra.listmessagesportfolio.data.domain.model.MessageDisplay
import com.bryanguerra.listmessagesportfolio.ui.common.ShimmerCardPlaceholder
import com.bryanguerra.listmessagesportfolio.utils.formatTimestampToSpanish

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageListScreen(messagesViewModel: MessagesViewModel, navController: NavController) {
    val messages by messagesViewModel.filteredMessages.collectAsState()
    val error by messagesViewModel.error.collectAsState()
    val query by messagesViewModel.searchQuery.collectAsState()
    val isAscending by messagesViewModel.isAscending.collectAsState()
    val isLoading by messagesViewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        messagesViewModel.fetchMessages()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Mensajes", style = MaterialTheme.typography.titleLarge)
                },
                actions = {
                    IconButton(onClick = {
                        messagesViewModel.logout {
                            navController.navigate("login") {
                                popUpTo("messages") { inclusive = true }
                            }
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Cerrar sesión")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Orden: ${if (isAscending) "Antiguos primero" else "Recientes primero"}")
                Button(onClick = { messagesViewModel.toggleOrder() }) {
                    Icon(Icons.Default.SwapVert, contentDescription = "Cambiar orden")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Ordenar")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = query,
                onValueChange = { messagesViewModel.updateSearchQuery(it) },
                label = { Text("Buscar por mensaje, usuario o fecha") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (error != null) {
                Text(text = error ?: "", color = MaterialTheme.colorScheme.error)
            }

            LazyColumn {
                if (isLoading) {
                    items(5) {
                        ShimmerCardPlaceholder()
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                } else {
                    items(messages) { msg ->
                        MessageCard(message = msg)
                    }
                }
            }
        }
    }
}


@Composable
fun MessageCard(message: MessageDisplay) {
    var isExpanded by remember { mutableStateOf(false) }

    val rotationAngle by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        label = "rotation"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { isExpanded = !isExpanded },
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(message.name, style = MaterialTheme.typography.titleMedium)
                }

                Icon(
                    imageVector = Icons.Default.ExpandMore,
                    contentDescription = "Expandir mensaje",
                    modifier = Modifier.rotate(rotationAngle)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Email, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(message.email, style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Schedule, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(formatTimestampToSpanish(message.timestamp), style = MaterialTheme.typography.bodySmall)
            }

            AnimatedVisibility(visible = isExpanded) {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = message.message,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

