package com.example.safepass.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.safepass.storage.AuthManager

@Composable
fun RegisterScreen(onRegistered: () -> Unit) {
    val ctx = LocalContext.current
    var user by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }
    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Register", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(value = user, onValueChange = { user = it }, label = { Text("Username") })
        OutlinedTextField(value = pass, onValueChange = { pass = it }, label = { Text("Password") }, visualTransformation = PasswordVisualTransformation())
        OutlinedTextField(value = confirm, onValueChange = { confirm = it }, label = { Text("Confirm Password") }, visualTransformation = PasswordVisualTransformation())
        Spacer(Modifier.height(12.dp))
        Button(onClick = { when {
            user.isBlank() || pass.isBlank() -> Toast.makeText(ctx, "Fill all fields", Toast.LENGTH_SHORT).show()
            pass != confirm -> Toast.makeText(ctx, "Passwords do not match", Toast.LENGTH_SHORT).show()
            else -> { AuthManager.register(user.trim(), pass); onRegistered() }
        } }) { Text("Register") }
    }
}