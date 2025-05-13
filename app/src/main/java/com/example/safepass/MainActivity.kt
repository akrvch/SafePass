package com.example.safepass

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.safepass.storage.PasswordRepository
import com.example.safepass.storage.PinManager
import com.example.safepass.ui.AppNavGraph
import com.example.safepass.ui.theme.SafePassTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PinManager.init(this)
        setContent {
            SafePassTheme {
                val repo = PasswordRepository(this)
                AppNavGraph(repository = repo)
            }
        }
    }
}