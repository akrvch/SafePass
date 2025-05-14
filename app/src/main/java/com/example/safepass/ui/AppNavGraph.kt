package com.example.safepass.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.safepass.storage.PasswordRepository
import com.example.safepass.storage.PinManager

@Composable
fun AppNavGraph(repository: PasswordRepository) {
    val navController = rememberNavController()
    val start = if (PinManager.isPinSet()) "pin" else "login"
    NavHost(navController = navController, startDestination = start) {
        composable("login") { LoginScreen(onLogin = { navController.navigate("pin") }, onRegister = { navController.navigate("register") }) }
        composable("register") { RegisterScreen(onRegistered = { navController.navigate("pin") }) }
        composable("pin") { PINScreen(onVerified = { navController.navigate("list") }, onSetup = { navController.navigate("list") }) }
        composable("list") { PasswordListScreen(repo = repository, onAdd = { navController.navigate("edit?entryId=-1") }, onDetail = { id -> navController.navigate("detail/$id") }) }
        composable("detail/{entryId}", arguments = listOf(navArgument("entryId") { type = NavType.LongType })) { back ->
            val id = back.arguments!!.getLong("entryId")
            PasswordDetailScreen(id = id, repo = repository, onEdit = { navController.navigate("edit?entryId=$id") }, onDelete = { navController.popBackStack("list", false) })
        }
        composable("edit?entryId={entryId}", arguments = listOf(navArgument("entryId") { type = NavType.LongType; defaultValue = -1L })) { back ->
            val raw = back.arguments!!.getLong("entryId")
            AddEditPasswordScreen(repo = repository, entryId = if (raw >= 0) raw else null, onDone = { navController.popBackStack("list", false) })
        }
    }
}