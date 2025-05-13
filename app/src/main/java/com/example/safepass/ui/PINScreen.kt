package com.example.safepass.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.safepass.storage.PinManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PINScreen(
    onVerified: () -> Unit,
    onSetup: () -> Unit
) {
    val ctx = LocalContext.current
    var pin by remember { mutableStateOf("") }
    val isSetup = !PinManager.isPinSet()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (isSetup) "Set your 4-digit PIN" else "Enter your 4-digit PIN",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(Modifier.height(24.dp))
        OutlinedTextField(
            value = pin,
            onValueChange = { pin = it.filter { ch -> ch.isDigit() }.take(4) },
            label = { Text("PIN") },
            singleLine = true,
            visualTransformation = VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.width(200.dp)
        )
        Spacer(Modifier.height(24.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(onClick = {
                if (isSetup) {
                    if (pin.length == 4) {
                        PinManager.savePIN(pin)
                        onSetup()
                    } else {
                        Toast.makeText(ctx, "Enter exactly 4 digits", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    if (PinManager.verifyPIN(pin)) onVerified()
                    else Toast.makeText(ctx, "Invalid PIN", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text(if (isSetup) "Set PIN" else "Verify PIN")
            }
            TextButton(onClick = { onSetup() }) {
                Text("Later")
            }
        }
    }
}
