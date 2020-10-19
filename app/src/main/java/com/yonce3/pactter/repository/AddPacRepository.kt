package com.yonce3.pactter.repository

import androidx.lifecycle.LiveData
import com.yonce3.pactter.data.Dao.PacDao
import com.yonce3.pactter.data.entity.Pac

class AddPacRepository(private val pacDao: PacDao) {

    val allPacs: LiveData<List<Pac>> = pacDao.getAll()

    suspend fun insertPac(pac: Pac) {
        pacDao.insert(pac)
    }


}