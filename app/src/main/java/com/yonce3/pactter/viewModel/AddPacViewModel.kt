package com.yonce3.pactter.viewModel

import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.yonce3.pactter.data.AppDatabase
import com.yonce3.pactter.data.entity.Pac
import com.yonce3.pactter.data.entity.User
import com.yonce3.pactter.repository.AddPacRepository

class AddPacViewModel : ViewModel() {

    companion object {
        val newInstance = AddPacRepository()
    }

    fun addPac(pac: Pac) {
        newInstance.addPac()
    }

    fun getPacCount(user: User) {

    }

}