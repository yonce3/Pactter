package com.yonce3.pactter.repository

import androidx.room.Room
import androidx.room.RoomDatabase
import com.yonce3.pactter.data.AppDatabase

class AddPacRepository {

    companion object {
        val db = Room.databaseBuilder(
            application, AppDatabase::class.java, "database-name").build()
    }

    fun addPac() {

    }
}