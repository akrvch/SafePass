package com.example.safepass.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.LaunchedEffect
import com.example.safepass.model.PasswordEntity
import com.example.safepass.storage.PasswordRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditPasswordScreen(
    repo: PasswordRepository,
    entryId: Long?,
    onDone: () -> Unit
) {
    val ctx = LocalContext.current
    var title by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var existing by remember { mutableStateOf<PasswordEntity?>(null) }
    var showPassword by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(entryId) {
        entryId?.takeIf { it >= 0 }?.let { e ->
            existing = repo.get(e)
            existing?.let { pe ->
                title = pe.title
                username = pe.username.orEmpty()
                password = pe.password
            }
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = if (existing == null) "Add Password" else "Edit Password",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title*") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username (opt)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password*") },
            singleLine = true,
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (showPassword) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(imageVector = image, contentDescription = if (showPassword) "Hide password" else "Show password")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = {
                password = List(12) { "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".random() }
                    .joinToString("")
                showPassword = true
            }) {
                Icon(Icons.Filled.Refresh, contentDescription = "Generate")
            }
        }

        Button(
            onClick = {
                when {
                    title.isBlank() || password.isBlank() ->
                        Toast.makeText(ctx, "Title and Password required", Toast.LENGTH_SHORT).show()
                    else -> scope.launch {
                        if (existing == null) {
                            repo.add(title.trim(), username.ifBlank { null }, password)
                        } else {
                            repo.update(existing!!.copy(
                                title = title.trim(),
                                username = username.ifBlank { null },
                                password = password
                            ))
                        }
                        onDone()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (existing == null) "Save" else "Update")
        }
    }
}
