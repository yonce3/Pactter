package com.yonce3.pactter.data.local

import androidx.room.*

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import com.yonce3.pactter.data.Dao.PacDao
import com.yonce3.pactter.data.Dao.UserDao
import com.yonce3.pactter.data.local.entity.Pac
import com.yonce3.pactter.data.local.entity.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(User::class, Pac::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun pacDao(): PacDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

    private class AppDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.pacDao())
                }
            }
        }

        suspend fun populateDatabase(pacDao: PacDao) {

            // TODO: Add your own words!
        }
    }
}