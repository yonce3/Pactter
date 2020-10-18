package (

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper

)com.yonce3.pactter.data

import android.content.Context
import com.yonce3.pactter.data.Dao.PacDao
import com.yonce3.pactter.data.Dao.UserDao
import com.yonce3.pactter.data.entity.Pac
import com.yonce3.pactter.data.entity.User

@Database(entities = arrayOf(User::class, Pac::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun pacDao(): PacDao

    private fun buildDataBase(appContext: Context): AppDatabase {
            return Room.databaseBuilder(appContext, AppDatabase::class.java, "database-name")
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                    }
                } {

                })
    }

    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
        TODO("Not yet implemented")
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("Not yet implemented")
    }

    override fun clearAllTables() {
        TODO("Not yet implemented")
    }
}