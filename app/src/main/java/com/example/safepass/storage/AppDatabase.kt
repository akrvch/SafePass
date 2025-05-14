package com.example.safepass.storage

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Database
import com.example.safepass.model.PasswordEntity

@Database(entities = [PasswordEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun passwordDao(): PasswordDao
    companion object {
        @Volatile private var INST: AppDatabase? = null
        fun get(ctx: Context): AppDatabase = INST ?: synchronized(this) {
            Room.databaseBuilder(ctx, AppDatabase::class.java, "safepass_db")
                .build().also { INST = it }
        }
    }
}