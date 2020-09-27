package com.yonce3.pactter.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yonce3.pactter.data.Dao.UserDao
import com.yonce3.pactter.data.entity.User

@Database(entities = arrayOf(User::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}