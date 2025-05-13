package com.example.safepass.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.LaunchedEffect
import com.example.safepass.model.PasswordEntity
import com.example.safepass.storage.PasswordRepository
import kotlinx.coroutines.launch

@Composable
fun PasswordDetailScreen(id: Long, repo: PasswordRepository, onEdit: () -> Unit, onDelete: () -> Unit) {
    val scope = rememberCoroutineScope()
    val ctx = LocalContext.current
    var entry by remember { mutableStateOf<PasswordEntity?>(null) }
    val clipboard = LocalClipboardManager.current
    var show by remember { mutableStateOf(false) }
    LaunchedEffect(id) { entry = repo.get(id) }
    entry?.let {
        Column(Modifier.fillMaxSize().padding(16.dp)) {
            Text(it.title, style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(12.dp))
            it.username?.let { u -> Text("Username: $u") }
            Spacer(Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(if (show) it.password else "*".repeat(it.password.length), modifier = Modifier.weight(1f).clickable {
                    clipboard.setText(AnnotatedString(it.password))
                    Toast.makeText(ctx, "Copied", Toast.LENGTH_SHORT).show()
                })
                IconButton(onClick = { show = !show }) { Icon(if (show) Icons.Default.VisibilityOff else Icons.Default.Visibility, contentDescription = null) }
            }
            Spacer(Modifier.height(24.dp))
            Row {
                Button(onClick = onEdit, modifier = Modifier.weight(1f)) { Icon(Icons.Default.Edit, contentDescription = null); Spacer(Modifier.width(4.dp)); Text("Edit") }
                Spacer(Modifier.width(16.dp))
                Button(onClick = { scope.launch { repo.delete(it); onDelete() } }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error), modifier = Modifier.weight(1f)) { Text("Delete") }
            }
        }
    } ?: Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Not found") }
}