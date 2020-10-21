package com.yonce3.pactter.data.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.yonce3.pactter.data.entity.Pac

@Dao
interface PacDao {
    @Query("SELECT * FROM pac")
    fun getAll(): LiveData<List<Pac>>

    @Query("SELECT * FROM pac WHERE pacId = :pacId")
    fun findPacWithId(pacId: Int): LiveData<Pac>

    @Insert
    fun insertAll(vararg pacs: Pac)

    @Insert
    fun insert(pac: Pac)

    @Delete
    fun delete(pac: Pac)
}