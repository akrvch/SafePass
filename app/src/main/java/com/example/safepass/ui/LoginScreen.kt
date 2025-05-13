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
fun LoginScreen(onLogin: () -> Unit, onRegister: () -> Unit) {
    val ctx = LocalContext.current
    var user by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Login", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(value = user, onValueChange = { user = it }, label = { Text("Username") })
        OutlinedTextField(value = pass, onValueChange = { pass = it }, label = { Text("Password") }, visualTransformation = PasswordVisualTransformation())
        Spacer(Modifier.height(12.dp))
        Button(onClick = { if (AuthManager.login(user.trim(), pass)) onLogin() else Toast.makeText(ctx, "Invalid credentials", Toast.LENGTH_SHORT).show() }) { Text("Login") }
        TextButton(onClick = onRegister) { Text("Register") }
    }
}