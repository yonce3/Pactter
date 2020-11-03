package com.yonce3.pactter.data.local.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.yonce3.pactter.data.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): LiveData<User>

    @Query("SELECT * FROM user WHERE name LIKE :name")
    fun findUsersByName(name: String): LiveData<User>

    @Insert
    fun insertAll(vararg users: User)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Delete
    suspend fun delete(user: User)
}