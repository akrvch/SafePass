package com.example.safepass.storage

import android.content.Context
import com.example.safepass.model.PasswordEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class PasswordRepository(context: Context) {
    private val dao = AppDatabase.get(context).passwordDao()

    fun getAll(): Flow<List<PasswordEntity>> =
        dao.getAll()
            .flowOn(Dispatchers.IO)

    suspend fun get(id: Long): PasswordEntity? =
        withContext(Dispatchers.IO) {
            dao.getById(id)
        }

    suspend fun add(title: String, username: String?, password: String) =
        withContext(Dispatchers.IO) {
            dao.insert(PasswordEntity(title = title, username = username, password = password))
        }

    suspend fun update(entity: PasswordEntity) =
        withContext(Dispatchers.IO) {
            dao.update(entity)
        }

    suspend fun delete(entity: PasswordEntity) =
        withContext(Dispatchers.IO) {
            dao.delete(entity)
        }
}