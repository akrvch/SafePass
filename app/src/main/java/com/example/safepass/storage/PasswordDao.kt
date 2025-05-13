package com.example.safepass.storage

import androidx.room.*
import com.example.safepass.model.PasswordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordDao {
    @Query("SELECT * FROM passwords")
    fun getAll(): Flow<List<PasswordEntity>>

    @Query("SELECT * FROM passwords WHERE id = :id")
    fun getById(id: Long): PasswordEntity?

    @Insert
    fun insert(entity: PasswordEntity): Long

    @Update
    fun update(entity: PasswordEntity)

    @Delete
    fun delete(entity: PasswordEntity)
}