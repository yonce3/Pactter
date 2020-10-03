package com.yonce3.pactter.data.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.yonce3.pactter.data.entity.Pac

@Dao
interface PacDao {
    @Query("SELECT * FROM pac")
    fun getAll(): List<Pac>

    @Insert
    fun insertAll(vararg pacs: Pac)

    @Insert
    fun insert(pac: Pac)

    @Delete
    fun delete(pac: Pac)
}