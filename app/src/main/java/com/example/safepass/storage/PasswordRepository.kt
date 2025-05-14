package com.example.safepass.storage

import android.content.Context
import com.example.safepass.model.PasswordEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class PasswordRepository(ctx: Context) {
    private val dao = AppDatabase.get(ctx).passwordDao()
    fun getAll(): Flow<List<PasswordEntity>> = dao.getAll().flowOn(Dispatchers.IO)
    suspend fun get(id: Long): PasswordEntity? = withContext(Dispatchers.IO) { dao.getById(id) }
    suspend fun add(title: String, user: String?, pwd: String) = withContext(Dispatchers.IO) {
        dao.insert(
            PasswordEntity(
                title = title,
                username = user,
                password = pwd
            )
        )
    }

    suspend fun update(e: PasswordEntity) = withContext(Dispatchers.IO) { dao.update(e) }
    suspend fun delete(e: PasswordEntity) = withContext(Dispatchers.IO) { dao.delete(e) }
}